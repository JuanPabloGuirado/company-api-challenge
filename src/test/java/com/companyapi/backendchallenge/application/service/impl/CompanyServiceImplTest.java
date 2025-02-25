package com.companyapi.backendchallenge.application.service.impl;

import com.companyapi.backendchallenge.config.exception.CouldNotCreateAdhesionException;
import com.companyapi.backendchallenge.config.exception.CouldNotRetrieveCompaniesException;
import com.companyapi.backendchallenge.config.exception.InvalidInputDataException;
import com.companyapi.backendchallenge.domain.dto.CompanyResponseDto;
import com.companyapi.backendchallenge.domain.dto.CreateCompanyAdhesionRequestDto;
import com.companyapi.backendchallenge.domain.mapper.CompanyMapper;
import com.companyapi.backendchallenge.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import com.companyapi.backendchallenge.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper mapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private CompanyEntity companyEntity;
    private CompanyResponseDto companyDto;

    @BeforeEach
    void setUp() {
        companyEntity = CompanyEntity.builder()
                .id(1L)
                .corporateName("My Company S.A.")
                .cuit("30-12345678-9")
                .registrationDate(LocalDate.of(2025, 1, 15))
                .build();

        companyDto = CompanyResponseDto.builder()
                .razonSocial("My Company S.A.")
                .fechaAdhesion(LocalDate.of(2025, 1, 15))
                .build();
    }

    @Test
    void getLastMonthAdheredSuccess() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();

        when(companyRepository.findCompaniesAdheredLastMonth(startDate, endDate))
                .thenReturn(Optional.of(List.of(companyEntity)));
        when(mapper.toDto(companyEntity)).thenReturn(companyDto);

        List<CompanyResponseDto> result = companyService.getLastMonthAdhered();

        assertEquals(1, result.size());
        assertEquals("My Company S.A.", result.get(0).getRazonSocial());
    }

    @Test
    void getLastMonthAdheredWithExceptionWhenNoCompaniesFound() {
        when(companyRepository.findCompaniesAdheredLastMonth(any(), any()))
                .thenReturn(Optional.empty());

        assertThrows(CouldNotRetrieveCompaniesException.class,
                () -> companyService.getLastMonthAdhered());
    }

    @Test
    void getLastMonthAdheredWithExceptionWhenDatabaseErrorOccurs() {
        when(companyRepository.findCompaniesAdheredLastMonth(any(), any()))
                .thenThrow(new PersistenceException());

        assertThrows(CouldNotRetrieveCompaniesException.class,
                () -> companyService.getLastMonthAdhered());
    }

    @Test
    void getLastMonthActiveSuccess() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();

        when(companyRepository.findCompaniesWithLastMonthTransactions(startDate, endDate))
                .thenReturn(Optional.of(List.of(companyEntity)));
        when(mapper.toDto(companyEntity)).thenReturn(companyDto);

        List<CompanyResponseDto> result = companyService.getLastMonthActive();

        assertEquals(1, result.size());
        assertEquals("My Company S.A.", result.get(0).getRazonSocial());
    }

    @Test
    void createCompanyAdhesionSuccessWhenValidRequest() {
        CreateCompanyAdhesionRequestDto request = getRequestDto();
        CompanyEntity newEntity = CompanyEntity.builder().build();

        when(companyRepository.existsByCuit(request.getCuit())).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(newEntity);

        assertDoesNotThrow(() -> companyService.createCompanyAdhesion(request));
        verify(companyRepository).save(newEntity);
    }

    @Test
    void createCompanyAdhesionWithExceptionWhenCuitAlreadyExists() {
        CreateCompanyAdhesionRequestDto request = getRequestDto();

        when(companyRepository.existsByCuit(request.getCuit())).thenReturn(true);

        assertThrows(InvalidInputDataException.class, () -> companyService.createCompanyAdhesion(request));
    }

    @Test
    void createCompanyAdhesionWithExceptionWhenDatabaseErrorOccurs() {
        CreateCompanyAdhesionRequestDto request = getRequestDto();

        when(companyRepository.existsByCuit(request.getCuit())).thenReturn(false);
        when(mapper.toEntity(request)).thenThrow(new PersistenceException());

        assertThrows(CouldNotCreateAdhesionException.class, () -> companyService.createCompanyAdhesion(request));
    }

    private CreateCompanyAdhesionRequestDto getRequestDto() {
        return CreateCompanyAdhesionRequestDto.builder()
                .companyName("New Company")
                .cuit("30-87654321-0")
                .registrationDate(LocalDate.now())
                .build();
    }
}