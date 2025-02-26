package com.backendchallenge.contract.mapper;

import com.backendchallenge.contract.dto.CompanyDto;
import com.backendchallenge.domain.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyDtoMapper {

    private final TransactionDtoMapper transactionMapper;

    public Company toEntity(CompanyDto dto) {
        if (dto == null) {
            return null;
        }
        return Company.builder()
                .name(dto.getRazonSocial())
                .cuit(dto.getCuit())
                .registrationDate(dto.getFechaAdhesion())
                .build();
    }

    public CompanyDto toDto(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyDto.builder()
                .cuit(company.getCuit())
                .razonSocial(company.getName())
                .fechaAdhesion(company.getRegistrationDate())
                .transacciones(company.getTransactions().stream()
                        .map(transactionMapper::toDto)
                        .toList())
                .build();
    }
}
