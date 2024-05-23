package com.example.SSMS.dtos;

import com.example.SSMS.model.enums.Roles;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class SignUpRequestDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private Roles role;
}
