package com.backendchallenge.application;

import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.domain.repository.CompanyRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyCommandHandler {

    private final CompanyRepository companyRepository;

    public void registerCompany(Company company) {
        companyRepository.createAdhesion(company);
    }
}
