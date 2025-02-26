package com.backendchallenge.contract.rest;

import com.backendchallenge.application.CompanyCommandHandler;
import com.backendchallenge.application.CompanyQueryHandler;
import com.backendchallenge.contract.dto.CompanyDto;
import com.backendchallenge.contract.mapper.CompanyDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyCommandHandler companyCommandHandler;

    private final CompanyQueryHandler companyQueryHandler;

    private final CompanyDtoMapper mapper;

    @PostMapping
    public ResponseEntity<Void> registerCompany(@RequestBody CompanyDto companyDto) {
        companyCommandHandler.registerCompany(mapper.toEntity(companyDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/last-adhered")
    public ResponseEntity<List<CompanyDto>> getLastAdheredCompanies() {
        return ResponseEntity.ok(companyQueryHandler.getLastMonthAdheredCompanies()
                .stream()
                .map(mapper::toDto)
                .toList());
    }

    @GetMapping("/last-month-active")
    public ResponseEntity<List<CompanyDto>> getLastMonthActiveCompanies() {
        return ResponseEntity.ok(companyQueryHandler.getCompaniesByLastMonthTransactions()
                .stream()
                .map(mapper::toDto)
                .toList());
    }
}
