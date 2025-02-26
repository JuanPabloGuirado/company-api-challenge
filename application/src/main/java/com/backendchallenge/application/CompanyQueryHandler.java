package com.backendchallenge.application;

import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyQueryHandler {

    private final CompanyRepository companyRepository;

    public List<Company> getCompaniesByLastMonthTransactions() {
        return companyRepository.getCompaniesByLastMonthTransfers();
    }

    public List<Company> getLastMonthAdheredCompanies() {
        return companyRepository.getLastMonthAdheredCompanies();
    }
}
