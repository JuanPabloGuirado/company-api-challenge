package com.companyapi.backendchallenge.infrastructure.adapters.output.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "\"company\"")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuit")
    private String cuit;

    @Column(name = "corporate_name")
    private String corporateName;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @EqualsExclude
    @ToStringExclude
    @HashCodeExclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions;
}
