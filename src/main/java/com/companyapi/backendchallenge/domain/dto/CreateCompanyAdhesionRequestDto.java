package com.companyapi.backendchallenge.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateCompanyAdhesionRequestDto {

    private String companyName;
    private String cuit;
    private LocalDate registrationDate;
}
