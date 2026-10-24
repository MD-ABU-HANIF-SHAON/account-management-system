package com.example.monolithic.cashflow;
import com.example.monolithic.common.CrudController;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/cashflow")
public class CashFlowController extends CrudController<CashFlowEntry> {
    private final CashFlowService service;
    public CashFlowController(CashFlowRepository repo, CashFlowService service) {
        super(repo);
        this.service = service;
    }
    @GetMapping("/balance")
    public Map<String, BigDecimal> balance() { return Map.of("balance", service.balance()); }
}
