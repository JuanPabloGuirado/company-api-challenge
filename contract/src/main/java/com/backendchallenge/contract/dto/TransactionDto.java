package com.backendchallenge.contract.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private BigDecimal importe;
    private String empresaId;
    private String cuentaDebito;
    private String cuentaCredito;
}
