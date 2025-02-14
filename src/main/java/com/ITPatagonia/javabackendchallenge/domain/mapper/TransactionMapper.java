package com.ITPatagonia.javabackendchallenge.domain.mapper;

import com.ITPatagonia.javabackendchallenge.domain.dto.TransactionDto;
import com.ITPatagonia.javabackendchallenge.infrastructure.adapters.output.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "importe", source = "entity.amount")
    @Mapping(target = "empresaId", source = "entity.company.id")
    @Mapping(target = "cuentaDebito", source = "entity.debitAccount")
    @Mapping(target = "cuentaCredito", source = "entity.creditAccount")
    TransactionDto toDto(TransactionEntity entity);
}
