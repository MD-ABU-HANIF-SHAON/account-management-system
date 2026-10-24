package com.example.monolithic.inventory;
import com.example.monolithic.common.CrudController;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/inventory")
public class InventoryController extends CrudController<InventoryItem> {
    public record RestockRequest(int quantity, BigDecimal unitCost) {}

    private final InventoryService service;
    public InventoryController(InventoryRepository repo, InventoryService service) {
        super(repo);
        this.service = service;
    }
    @PostMapping("/{id}/restock")
    public InventoryItem restock(@PathVariable Long id, @RequestBody RestockRequest r) {
        return service.restock(id, r.quantity(), r.unitCost());
    }
}
