package com.example.SSMS.utill;

import com.example.SSMS.service.impl.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        userService.createSuperAdminIfNotExists();
    }
}
