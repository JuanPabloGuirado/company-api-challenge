package com.companyapi.backendchallenge.application.service;

import com.companyapi.backendchallenge.domain.dto.CompanyResponseDto;
import com.companyapi.backendchallenge.domain.dto.CreateCompanyAdhesionRequestDto;

import java.util.List;

public interface CompanyService {

    List<CompanyResponseDto> getLastMonthAdhered();

    List<CompanyResponseDto> getLastMonthActive();

    void createCompanyAdhesion(CreateCompanyAdhesionRequestDto request);
}
