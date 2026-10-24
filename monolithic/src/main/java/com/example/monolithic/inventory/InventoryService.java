package com.example.monolithic.inventory;
import com.example.monolithic.cashflow.CashFlowService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service @RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repo;
    private final CashFlowService cashFlow;

    public InventoryItem get(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    public InventoryItem reduceStock(Long itemId, int qty) {
        InventoryItem item = get(itemId);
        if (item.getQuantity() < qty) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock");
        item.setQuantity(item.getQuantity() - qty);
        return repo.save(item);
    }

    public InventoryItem restock(Long itemId, int qty, BigDecimal unitCost) {
        InventoryItem item = get(itemId);
        item.setQuantity(item.getQuantity() + qty);
        cashFlow.record("OUT", unitCost.multiply(BigDecimal.valueOf(qty)), "Restock: " + item.getName());
        return repo.save(item);
    }
}
