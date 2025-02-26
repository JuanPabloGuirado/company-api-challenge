package com.backendchallenge.application;

import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyCommandHandler {

    private final CompanyRepository companyRepository;

    public void registerCompany(Company company) {
        companyRepository.createAdhesion(company);
    }
}
