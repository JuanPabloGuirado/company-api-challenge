package com.backendchallenge.infrastructure.repository.mapper;

import com.backendchallenge.domain.entity.Company;
import com.backendchallenge.infrastructure.entity.CompanyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyEntityMapper {

    private final TransactionEntityMapper transactionMapper;

    public Company toDomain(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }

        return Company.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cuit(entity.getCuit())
                .registrationDate(entity.getRegistrationDate())
                .transactions(entity.getTransactions()
                        .stream()
                        .map(transactionMapper::toDomain)
                        .toList())
                .build();
    }

    public CompanyEntity toEntity(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyEntity.builder()
                .cuit(company.getCuit())
                .name(company.getName())
                .registrationDate(company.getRegistrationDate())
                .build();
    }
}
