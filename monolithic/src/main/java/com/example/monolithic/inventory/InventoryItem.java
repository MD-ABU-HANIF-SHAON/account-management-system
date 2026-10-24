package com.example.monolithic.inventory;
import com.example.monolithic.common.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Entity @Getter @Setter
public class InventoryItem extends BaseEntity {
    private String name;
    private int quantity;
    private BigDecimal unitPrice;
    private Long supplierId;
}
