package com.backendchallenge.infrastructure.repository.impl;

import com.backendchallenge.contract.validation.annotation.ValidateInputParams;
import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.domain.exception.CouldNotCreateAdhesionException;
import com.backendchallenge.domain.exception.CouldNotRetrieveCompaniesException;
import com.backendchallenge.domain.exception.InvalidInputDataException;
import com.backendchallenge.domain.repository.CompanyRepository;
import com.backendchallenge.infrastructure.entity.CompanyEntity;
import com.backendchallenge.infrastructure.repository.CompanyH2Repository;
import com.backendchallenge.infrastructure.repository.mapper.CompanyEntityMapper;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyH2Repository repository;

    private final CompanyEntityMapper mapper;

    @Override
    @ValidateInputParams
    public void createAdhesion(Company company) {
        try {
            Optional.of(company)
                    .ifPresentOrElse(req -> {
                        checkExistingCuit(req);
                        repository.save(mapper.toEntity(req));
                    }, () -> {
                        throw new CouldNotCreateAdhesionException("Unable to create new adhesion. Please check the "
                                + "request body.");
                    });
        } catch (PersistenceException exception) {
            log.error("Error creating company adhesion - message: {}", exception.getMessage());
            throw new CouldNotCreateAdhesionException("Internal error while creating new adhesion. Please try again "
                    + "in a few moments.");
        }
    }

    @Override
    public List<Company> getLastMonthAdheredCompanies() {
        return getCompaniesForLastMonth(repository::findCompaniesAdheredLastMonth,
                "Error retrieving last month adhered companies",
                "Internal error while fetching last adhered companies. Please try again in a few moments.");
    }

    @Override
    public List<Company> getCompaniesByLastMonthTransfers() {
        return getCompaniesForLastMonth(repository::findCompaniesWithLastMonthTransactions,
                "Error retrieving last month active companies",
                "Internal error while fetching last month's active companies."
                        + " Please try again in a few moments.");
    }

    private List<Company> getCompaniesForLastMonth(
            BiFunction<LocalDate, LocalDate, Optional<List<CompanyEntity>>> repositoryQuery,
            String logMessage,
            String errorMessage) {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();

        try {
            return repositoryQuery.apply(startDate, endDate)
                    .map(companies -> companies.stream()
                            .map(mapper::toDomain)
                            .collect(Collectors.toList()))
                          .orElseThrow(() -> new CouldNotRetrieveCompaniesException("Internal error while fetching "
                                  + "company list"));
        } catch (PersistenceException exception) {
            log.info(logMessage);
            throw new CouldNotRetrieveCompaniesException(errorMessage);
        }
    }

    private void checkExistingCuit(Company request) {
        if (repository.existsByCuit(request.getCuit())) {
            log.error("Could not create adhesion, already existing cuit [{}]", request.getCuit());
            throw new InvalidInputDataException("Cuit already exists");
        }
    }
}
