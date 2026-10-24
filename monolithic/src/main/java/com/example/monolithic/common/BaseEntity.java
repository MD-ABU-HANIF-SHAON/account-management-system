package com.example.monolithic.common;
import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass @Getter @Setter
public abstract class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
