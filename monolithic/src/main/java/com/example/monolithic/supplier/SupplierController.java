package com.example.monolithic.supplier;
import com.example.monolithic.common.CrudController;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/suppliers")
public class SupplierController extends CrudController<Supplier> {
    public SupplierController(SupplierRepository repo) { super(repo); }
}
