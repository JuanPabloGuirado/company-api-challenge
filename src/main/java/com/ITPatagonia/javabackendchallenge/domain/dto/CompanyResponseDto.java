package com.ITPatagonia.javabackendchallenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponseDto {

    private String cuit;
    private String razonSocial;
    private LocalDate fechaAdhesion;
    private List<TransactionDto> transacciones;
}
