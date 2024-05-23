package com.example.SSMS.dtos;

import lombok.Data;

@Data
public class SignInRequestDTO {
    private String email;
    private String password;
}
