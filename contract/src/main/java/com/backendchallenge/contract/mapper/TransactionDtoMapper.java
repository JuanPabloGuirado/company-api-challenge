package com.backendchallenge.contract.mapper;

import com.backendchallenge.contract.dto.TransactionDto;
import com.backendchallenge.domain.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoMapper {

    public Transaction toEntity(TransactionDto dto) {
        if (dto == null) {
            return null;
        }

        return Transaction.builder()
                .importe(dto.getImporte())
                .empresaId(dto.getEmpresaId())
                .cuentaDebito(dto.getCuentaDebito())
                .cuentaCredito(dto.getCuentaCredito())
                .build();
    }

    public TransactionDto toDto(Transaction entity) {
        if (entity == null) {
            return null;
        }

        return TransactionDto.builder()
                .importe(entity.getImporte())
                .empresaId(entity.getEmpresaId())
                .cuentaDebito(entity.getCuentaDebito())
                .cuentaCredito(entity.getCuentaCredito())
                .build();
    }
}
