package com.example.monolithic.order;
import com.example.monolithic.common.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Entity @Table(name = "orders") @Getter @Setter
public class CustomerOrder extends BaseEntity {
    private Long userId;
    private Long itemId;
    private int quantity;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
}
