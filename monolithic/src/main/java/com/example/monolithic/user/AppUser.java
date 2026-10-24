package com.example.monolithic.user;
import com.example.monolithic.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "app_users") @Getter @Setter
public class AppUser extends BaseEntity {
    private String name;
    private String email;
    private String role; // ADMIN / STAFF / CUSTOMER
}
