package com.backendchallenge.infrastructure.repository.impl;

import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.domain.exception.CouldNotCreateAdhesionException;
import com.backendchallenge.domain.exception.CouldNotRetrieveCompaniesException;
import com.backendchallenge.domain.exception.InvalidInputDataException;
import com.backendchallenge.infrastructure.entity.CompanyEntity;
import com.backendchallenge.infrastructure.repository.CompanyH2Repository;
import com.backendchallenge.infrastructure.repository.mapper.CompanyEntityMapper;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyRepositoryImplTest {

    @Mock
    private CompanyH2Repository repository;

    @Mock
    private CompanyEntityMapper mapper;

    @InjectMocks
    private CompanyRepositoryImpl companyRepositoryImpl;

    @Test
    void testCreateAdhesionSuccess() {
        Company company = Company.builder()
                .cuit("30-123456789-0")
                .name("Company Name")
                .build();
        CompanyEntity companyEntity = CompanyEntity.builder()
                .cuit("30-123456789-0")
                .name("Company Name")
                .build();

        when(repository.existsByCuit(company.getCuit())).thenReturn(false);
        when(mapper.toEntity(company)).thenReturn(companyEntity);
        companyRepositoryImpl.createAdhesion(company);

        verify(repository, times(1)).save(companyEntity); // Ensure save is called once
    }

    @Test
    void testCreateAdhesionExistingCuit() {
        Company company = Company.builder()
                .cuit("123456789")
                .name("Name")
                .build();
        when(repository.existsByCuit(company.getCuit())).thenReturn(true);
        assertThrows(InvalidInputDataException.class, () -> companyRepositoryImpl.createAdhesion(company));
    }

    @Test
    void testCreateAdhesionWithPersistenceException() {
        Company company = Company.builder()
                .cuit("123456789")
                .name("Name")
                .build();

        when(repository.existsByCuit(company.getCuit())).thenReturn(false);
        when(mapper.toEntity(company)).thenReturn(CompanyEntity.builder()
                .cuit("123456789")
                .name("Name")
                .build());
        doThrow(new PersistenceException("Database error")).when(repository).save(any());

        assertThrows(CouldNotCreateAdhesionException.class, () -> companyRepositoryImpl.createAdhesion(company));
    }

    @Test
    void testGetLastMonthAdheredCompaniesSuccess() {
        List<CompanyEntity> companyEntities = List.of(CompanyEntity.builder()
                .cuit("123456789")
                .name("Company Name")
                .build());
        when(repository.findCompaniesAdheredLastMonth(any(), any())).thenReturn(Optional.of(companyEntities));
        when(mapper.toDomain(any(CompanyEntity.class))).thenReturn(Company.builder()
                .cuit("123456789")
                .name("Company Name")
                .build());
        List<Company> companies = companyRepositoryImpl.getLastMonthAdheredCompanies();

        assertNotNull(companies);
        assertEquals(1, companies.size());
    }

    @Test
    void testGetLastMonthAdheredCompaniesNoData() {
        when(repository.findCompaniesAdheredLastMonth(any(), any())).thenReturn(Optional.empty());

        assertThrows(CouldNotRetrieveCompaniesException.class, () -> companyRepositoryImpl.getLastMonthAdheredCompanies());
    }

    @Test
    void testGetLastMonthAdheredCompaniesWithPersistenceException() {
        when(repository.findCompaniesAdheredLastMonth(any(), any())).thenThrow(new PersistenceException("DB error"));

        assertThrows(CouldNotRetrieveCompaniesException.class, () -> companyRepositoryImpl.getLastMonthAdheredCompanies());
    }

    @Test
    void testGetCompaniesByLastMonthTransfersSuccess() {
        List<CompanyEntity> companyEntities = List.of(CompanyEntity.builder()
                .cuit("123456789")
                .name("Company Name")
                .build());
        when(repository.findCompaniesWithLastMonthTransactions(any(), any())).thenReturn(Optional.of(companyEntities));
        when(mapper.toDomain(any(CompanyEntity.class))).thenReturn(Company.builder()
                .cuit("123456789")
                .name("Company Name")
                .build());
        List<Company> companies = companyRepositoryImpl.getCompaniesByLastMonthTransfers();

        assertNotNull(companies);
        assertEquals(1, companies.size());
    }

    @Test
    void testGetCompaniesByLastMonthTransfersNoData() {
        when(repository.findCompaniesWithLastMonthTransactions(any(), any())).thenReturn(Optional.empty());

        assertThrows(CouldNotRetrieveCompaniesException.class, () -> companyRepositoryImpl.getCompaniesByLastMonthTransfers());
    }

    @Test
    void testGetCompaniesByLastMonthTransfersWithPersistenceException() {
        when(repository.findCompaniesWithLastMonthTransactions(any(), any())).thenThrow(new PersistenceException("DB error"));

        assertThrows(CouldNotRetrieveCompaniesException.class, () -> companyRepositoryImpl.getCompaniesByLastMonthTransfers());
    }
}