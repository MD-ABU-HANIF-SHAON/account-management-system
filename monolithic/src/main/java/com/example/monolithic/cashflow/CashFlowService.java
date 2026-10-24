package com.example.monolithic.cashflow;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CashFlowService {
    private final CashFlowRepository repo;

    public CashFlowEntry record(String type, BigDecimal amount, String description) {
        CashFlowEntry e = new CashFlowEntry();
        e.setType(type);
        e.setAmount(amount);
        e.setDescription(description);
        e.setCreatedAt(LocalDateTime.now());
        return repo.save(e);
    }

    public BigDecimal balance() {
        return repo.findAll().stream()
            .map(e -> "IN".equals(e.getType()) ? e.getAmount() : e.getAmount().negate())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
