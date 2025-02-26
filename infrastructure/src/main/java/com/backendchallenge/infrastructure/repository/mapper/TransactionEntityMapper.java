package com.backendchallenge.infrastructure.repository.mapper;

import com.backendchallenge.domain.entity.Transaction;
import com.backendchallenge.infrastructure.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityMapper {

    public Transaction toDomain(TransactionEntity entity) {
        if (entity == null) {
            return null;
        }

        return Transaction.builder()
                .empresaId(String.valueOf(entity.getCompany().getId()))
                .importe(entity.getAmount())
                .cuentaDebito(entity.getDebitAccount())
                .cuentaCredito(entity.getCreditAccount())
                .build();
    }
}
