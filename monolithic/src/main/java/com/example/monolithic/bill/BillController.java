package com.example.monolithic.bill;
import com.example.monolithic.common.CrudController;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/bills")
public class BillController extends CrudController<Bill> {
    private final BillService service;
    public BillController(BillRepository repo, BillService service) {
        super(repo);
        this.service = service;
    }
    @PostMapping("/{id}/pay")
    public Bill pay(@PathVariable Long id) { return service.pay(id); }
}
