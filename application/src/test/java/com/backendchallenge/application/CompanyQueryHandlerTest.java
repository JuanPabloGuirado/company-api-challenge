package com.backendchallenge.application;

import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.domain.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyQueryHandlerTest {

    @InjectMocks
    private CompanyQueryHandler companyQueryHandler;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    void getCompaniesByLastMonthTransactionsSuccess() {
        List<Company> mockCompanies = List.of(
                Company.builder()
                        .name("Company A")
                        .cuit("20-12345678-1")
                        .build(),
                Company.builder()
                        .name("Company B")
                        .cuit("27-87654321-5")
                        .build()
        );

        when(companyRepository.getCompaniesByLastMonthTransfers()).thenReturn(mockCompanies);

        List<Company> result = companyQueryHandler.getCompaniesByLastMonthTransactions();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Company::getName)
                .containsExactly("Company A", "Company B");

        verify(companyRepository, times(1)).getCompaniesByLastMonthTransfers();
    }

    @Test
    void shouldReturnLastMonthAdheredCompanies() {
        List<Company> mockCompanies = List.of(
                Company.builder()
                        .name("Company 1")
                        .cuit("30-11223344-8")
                        .build(),
                Company.builder()
                        .name("Company 2")
                        .cuit("27-87888321-5")
                        .build()
        );

        when(companyRepository.getLastMonthAdheredCompanies()).thenReturn(mockCompanies);
        List<Company> result = companyQueryHandler.getLastMonthAdheredCompanies();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Company::getName)
                .containsExactly("Company 1", "Company 2");

        verify(companyRepository, times(1)).getLastMonthAdheredCompanies();
    }
}