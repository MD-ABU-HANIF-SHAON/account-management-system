package com.example.monolithic.cashflow;
import com.example.monolithic.common.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Entity @Getter @Setter
public class CashFlowEntry extends BaseEntity {
    private String type; // IN / OUT
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
}
