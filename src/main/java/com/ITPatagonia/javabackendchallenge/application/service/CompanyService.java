package com.ITPatagonia.javabackendchallenge.application.service;

import com.ITPatagonia.javabackendchallenge.domain.dto.CompanyResponseDto;
import com.ITPatagonia.javabackendchallenge.domain.dto.CreateCompanyAdhesionRequestDto;

import java.util.List;

public interface CompanyService {

    List<CompanyResponseDto> getLastMonthAdhered();

    List<CompanyResponseDto> getLastMonthActive();

    void createCompanyAdhesion(CreateCompanyAdhesionRequestDto request);
}
