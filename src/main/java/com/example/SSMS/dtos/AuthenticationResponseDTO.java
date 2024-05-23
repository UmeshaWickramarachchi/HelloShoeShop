package com.example.SSMS.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String jwt;
    private String refreshToken;
}
