package com.example.SSMS.service;

import com.example.SSMS.dtos.AuthenticationResponseDTO;
import com.example.SSMS.dtos.SignInRequestDTO;
import com.example.SSMS.dtos.SignUpRequestDTO;
import com.example.SSMS.model.AppUser;
import com.example.SSMS.model.Employee;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthServiceI {
    AppUser createUser(SignUpRequestDTO signUpRequest);

    AuthenticationResponseDTO signIn(SignInRequestDTO signInRequest, HttpServletResponse response) throws IOException;

    Employee getCurrentUser();
}
