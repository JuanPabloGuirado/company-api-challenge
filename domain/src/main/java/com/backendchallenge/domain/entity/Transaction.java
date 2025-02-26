package com.backendchallenge.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    BigDecimal importe;
    String empresaId;
    String cuentaDebito;
    String cuentaCredito;
}
