package com.example.monolithic.order;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/orders") @RequiredArgsConstructor
public class OrderController {
    private final OrderRepository repo;
    private final OrderService service;

    @GetMapping public List<CustomerOrder> all() { return repo.findAll(); }
    @PostMapping public CustomerOrder place(@RequestBody OrderRequest req) { return service.place(req); }
}
