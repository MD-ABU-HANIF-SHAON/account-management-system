package com.example.monolithic.bill;
import com.example.monolithic.cashflow.CashFlowService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service @RequiredArgsConstructor
public class BillService {
    private final BillRepository repo;
    private final CashFlowService cashFlow;

    public Bill createFor(Long orderId, BigDecimal amount) {
        Bill b = new Bill();
        b.setOrderId(orderId);
        b.setAmount(amount);
        b.setPaid(false);
        b.setIssuedAt(LocalDateTime.now());
        return repo.save(b);
    }

    @Transactional
    public Bill pay(Long billId) {
        Bill b = repo.findById(billId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found"));
        if (b.isPaid()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already paid");
        b.setPaid(true);
        cashFlow.record("IN", b.getAmount(), "Payment for bill #" + b.getId());
        return repo.save(b);
    }
}
