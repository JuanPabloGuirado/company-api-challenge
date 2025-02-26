package com.backendchallenge.domain.repository;

import com.backendchallenge.domain.entity.Company;

import java.util.List;

public interface CompanyRepository {

    void createAdhesion(Company company);

    List<Company> getLastMonthAdheredCompanies();

    List<Company> getCompaniesByLastMonthTransfers();
}
