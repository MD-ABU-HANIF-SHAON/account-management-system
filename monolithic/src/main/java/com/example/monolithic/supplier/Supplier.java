package com.example.monolithic.supplier;
import com.example.monolithic.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
public class Supplier extends BaseEntity {
    private String name;
    private String phone;
    private String email;
}
