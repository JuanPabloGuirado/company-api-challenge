package com.backendchallenge.domain.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    private Long id;
    private String cuit;
    private String name;
    private LocalDate registrationDate;
    private List<Transaction> transactions;
}
