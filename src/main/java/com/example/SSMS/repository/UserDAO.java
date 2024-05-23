package com.example.SSMS.repository;

import com.example.SSMS.model.AppUser;
import com.example.SSMS.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<AppUser,Integer> {
    Optional<AppUser> findByEmail(String email);

    AppUser findByRole(Roles role);

}
