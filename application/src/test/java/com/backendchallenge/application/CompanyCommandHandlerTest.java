package com.backendchallenge.application;

import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.domain.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompanyCommandHandlerTest {

    @InjectMocks
    private CompanyCommandHandler companyCommandHandler;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    void registerCompanySuccess() {
        Company company = Company.builder()
                .name("Company A")
                .cuit("23-44556677-3")
                .build();

        companyCommandHandler.registerCompany(company);
        verify(companyRepository, times(1)).createAdhesion(company);
    }
}