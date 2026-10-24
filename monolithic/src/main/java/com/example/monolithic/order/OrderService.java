package com.example.monolithic.order;
import com.example.monolithic.bill.BillService;
import com.example.monolithic.inventory.InventoryItem;
import com.example.monolithic.inventory.InventoryService;
import com.example.monolithic.user.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service @RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo;
    private final UserRepository userRepo;
    private final InventoryService inventory;
    private final BillService billService;

    @Transactional
    public CustomerOrder place(OrderRequest req) {
        if (req == null || req.userId() == null || req.itemId() == null || req.quantity() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId, itemId and positive quantity required");
        if (!userRepo.existsById(req.userId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        InventoryItem item = inventory.reduceStock(req.itemId(), req.quantity());
        BigDecimal total = item.getUnitPrice().multiply(BigDecimal.valueOf(req.quantity()));

        CustomerOrder o = new CustomerOrder();
        o.setUserId(req.userId());
        o.setItemId(req.itemId());
        o.setQuantity(req.quantity());
        o.setTotalAmount(total);
        o.setStatus("PLACED");
        o.setCreatedAt(LocalDateTime.now());
        CustomerOrder saved = repo.save(o);

        billService.createFor(saved.getId(), total);
        return saved;
    }
}
