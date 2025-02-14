package com.ITPatagonia.javabackendchallenge.infrastructure.adapters.input.controller;

import com.ITPatagonia.javabackendchallenge.application.service.CompanyService;
import com.ITPatagonia.javabackendchallenge.domain.dto.CompanyResponseDto;
import com.ITPatagonia.javabackendchallenge.domain.dto.CreateCompanyAdhesionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/last-adhered")
    public ResponseEntity<List<CompanyResponseDto>> getLastAdheredCompanies() {
        return ResponseEntity.ok(companyService.getLastMonthAdhered());
    }

    @GetMapping("/last-month-active")
    public ResponseEntity<List<CompanyResponseDto>> getLastMonthActiveCompanies() {
        return ResponseEntity.ok(companyService.getLastMonthActive());
    }

    @PostMapping
    public ResponseEntity<Void> createCompanyAdhesion(@RequestBody CreateCompanyAdhesionRequestDto request) {
        companyService.createCompanyAdhesion(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
