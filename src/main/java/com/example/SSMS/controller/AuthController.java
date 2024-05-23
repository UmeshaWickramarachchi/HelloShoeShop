package com.example.SSMS.controller;

import com.example.SSMS.dtos.SignInRequestDTO;
import com.example.SSMS.dtos.AuthenticationResponseDTO;
import com.example.SSMS.dtos.SignUpRequestDTO;
import com.example.SSMS.model.AppUser;
import com.example.SSMS.service.AuthServiceI;
import com.example.SSMS.service.UserServiceI;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthServiceI authServiceI;
    @Autowired
    private UserServiceI userServiceI;

    @PostMapping("/register")
    public ResponseEntity<AppUser> signUpUser(@RequestBody SignUpRequestDTO signUpRequest){
        return ResponseEntity.ok(authServiceI.createUser(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> createAuthenticationToken(@RequestBody SignInRequestDTO authRequest, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        return ResponseEntity.ok(authServiceI.signIn(authRequest,response));
    }

    @GetMapping("/profile")
    public ResponseEntity<AppUser> getLoggedUser(){
        return ResponseEntity.ok(authServiceI.getCurrentUser());
    }

}
