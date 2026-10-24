package com.example.monolithic.bill;
import com.example.monolithic.common.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Entity @Getter @Setter
public class Bill extends BaseEntity {
    private Long orderId;
    private BigDecimal amount;
    private boolean paid;
    private LocalDateTime issuedAt;
}
