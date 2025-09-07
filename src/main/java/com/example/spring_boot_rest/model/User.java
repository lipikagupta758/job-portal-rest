package com.example.spring_boot_rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "users")
@Entity
public class User {
    private int id;
    @Id
    private String username;
    private String password;
}
