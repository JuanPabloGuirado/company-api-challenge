package com.companyapi.backendchallenge.domain.mapper;

import com.companyapi.backendchallenge.domain.dto.CompanyResponseDto;
import com.companyapi.backendchallenge.domain.dto.CreateCompanyAdhesionRequestDto;
import com.companyapi.backendchallenge.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface CompanyMapper {

    @Mapping(target = "razonSocial", source = "entity.corporateName")
    @Mapping(target = "fechaAdhesion", source = "entity.registrationDate")
    @Mapping(target = "transacciones", source = "entity.transactions")
    CompanyResponseDto toDto(CompanyEntity entity);

    @Mapping(target = "corporateName", source = "companyName")
    CompanyEntity toEntity(CreateCompanyAdhesionRequestDto request);
}
