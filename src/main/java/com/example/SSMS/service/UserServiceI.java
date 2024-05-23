package com.example.SSMS.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceI {

    UserDetailsService userDetailsService();

    void createSuperAdminIfNotExists();
}
