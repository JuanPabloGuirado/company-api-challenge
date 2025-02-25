package com.companyapi.backendchallenge.infrastructure.adapters.output.persistence.repository;

import com.companyapi.backendchallenge.infrastructure.adapters.output.persistence.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    boolean existsByCuit(String cuit);

    @Query("SELECT DISTINCT c FROM CompanyEntity c JOIN TransactionEntity t ON c.id = t.company.id " +
            "WHERE t.transactionDate >= :startDate AND t.transactionDate < :endDate")
    Optional<List<CompanyEntity>> findCompaniesWithLastMonthTransactions(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM CompanyEntity c " +
            "WHERE c.registrationDate >= :startDate AND c.registrationDate < :endDate")
    Optional<List<CompanyEntity>> findCompaniesAdheredLastMonth(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
