package com.example.SSMS.service.impl;

import com.example.SSMS.dtos.AuthenticationResponseDTO;
import com.example.SSMS.dtos.SignInRequestDTO;
import com.example.SSMS.dtos.SignUpRequestDTO;
import com.example.SSMS.model.AppUser;
import com.example.SSMS.model.Employee;
import com.example.SSMS.repository.EmployeeDAO;
import com.example.SSMS.repository.UserDAO;
import com.example.SSMS.service.AuthServiceI;
import com.example.SSMS.utill.JwtUtill;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class AuthService implements AuthServiceI {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtill utill;
    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public AppUser createUser(SignUpRequestDTO signUpRequest) {
        AppUser newUser = new AppUser();
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setRole(signUpRequest.getRole());
        newUser.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        newUser.setId(signUpRequest.getId());
        return userDAO.save(newUser);
    }

    @Override
    public AuthenticationResponseDTO signIn(SignInRequestDTO signInRequest, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Credentials");
        }catch (DisabledException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"This user is not found");
        }
        var user = userDAO.findByEmail(signInRequest.getEmail()).orElseThrow();
        var jwt = utill.generateToken(user);
        var refreshToken = utill.generateRefreshToken(new HashMap<>(),user);
        return new AuthenticationResponseDTO(jwt,refreshToken);
    }

    public Employee getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        return employeeDAO.findByEmail(userEmail);
    }
}
