package com.ITPatagonia.javabackendchallenge.domain.mapper;

import com.ITPatagonia.javabackendchallenge.domain.dto.CompanyResponseDto;
import com.ITPatagonia.javabackendchallenge.domain.dto.CreateCompanyAdhesionRequestDto;
import com.ITPatagonia.javabackendchallenge.infrastructure.adapters.output.persistence.entity.CompanyEntity;
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
