package com.ITPatagonia.javabackendchallenge.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionDto {

    private BigDecimal importe;
    private String empresaId;
    private String cuentaDebito;
    private String cuentaCredito;
}
