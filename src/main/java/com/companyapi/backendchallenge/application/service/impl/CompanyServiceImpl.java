package com.companyapi.backendchallenge.application.service.impl;

import com.companyapi.backendchallenge.application.service.CompanyService;
import com.companyapi.backendchallenge.config.exception.CouldNotCreateAdhesionException;
import com.companyapi.backendchallenge.config.exception.CouldNotRetrieveCompaniesException;
import com.companyapi.backendchallenge.config.exception.InvalidInputDataException;
import com.companyapi.backendchallenge.domain.dto.CompanyResponseDto;
import com.companyapi.backendchallenge.domain.dto.CreateCompanyAdhesionRequestDto;
import com.companyapi.backendchallenge.domain.mapper.CompanyMapper;
import com.companyapi.backendchallenge.domain.validation.ValidateInputParams;
import com.companyapi.backendchallenge.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import com.companyapi.backendchallenge.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper mapper;

    @Override
    public List<CompanyResponseDto> getLastMonthAdhered() {
        return getCompaniesForLastMonth(
                companyRepository::findCompaniesAdheredLastMonth,
                "Error retrieving last month adhered companies",
                "Internal error while fetching last adhered companies. Please try again in a few moments."
        );
    }

    @Override
    public List<CompanyResponseDto> getLastMonthActive() {
        return getCompaniesForLastMonth(
                companyRepository::findCompaniesWithLastMonthTransactions,
                "Error retrieving last month active companies",
                "Internal error while fetching last month's active companies. Please try again in a few moments."
        );
    }

    private List<CompanyResponseDto> getCompaniesForLastMonth(
            BiFunction<LocalDate, LocalDate, Optional<List<CompanyEntity>>> repositoryQuery,
            String logMessage,
            String errorMessage) {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();

        try {
            return repositoryQuery.apply(startDate, endDate)
                    .map(companies -> companies.stream()
                            .map(mapper::toDto)
                            .collect(Collectors.toList()))
                    .orElseThrow(() -> new CouldNotRetrieveCompaniesException("Internal error while fetching company list"));
        } catch (PersistenceException exception) {
            log.info(logMessage);
            throw new CouldNotRetrieveCompaniesException(errorMessage);
        }
    }

    @Override
    @ValidateInputParams
    public void createCompanyAdhesion(CreateCompanyAdhesionRequestDto request) {
        try {
            Optional.of(request)
                    .ifPresentOrElse(req -> {
                        checkExistingCuit(req);
                        companyRepository.save(mapper.toEntity(req));
                    }, () -> {
                        throw new CouldNotCreateAdhesionException("Unable to create new adhesion. Please check the "
                                + "request body.");
                    });
        } catch (PersistenceException exception) {
            log.info(String.format("Error creating company adhesion - message: %s", exception.getMessage()));
            throw new CouldNotCreateAdhesionException("Internal error while creating new adhesion. Please try again "
                    + "in a few moments.");
        }
    }

    private void checkExistingCuit(CreateCompanyAdhesionRequestDto request) {
        if (companyRepository.existsByCuit(request.getCuit())) {
            log.error(String.format("Could not create adhesion, already existing cuit [%s]", request.getCuit()));
            throw new InvalidInputDataException("Cuit already exists");
        }
    }
}
