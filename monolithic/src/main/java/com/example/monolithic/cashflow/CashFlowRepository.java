package com.example.monolithic.cashflow;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CashFlowRepository extends JpaRepository<CashFlowEntry, Long> {}
