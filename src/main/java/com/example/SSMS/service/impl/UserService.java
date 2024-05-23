package com.example.SSMS.service.impl;

import com.example.SSMS.exception.RecordNotFoundException;
import com.example.SSMS.model.AppUser;
import com.example.SSMS.model.enums.Roles;
import com.example.SSMS.repository.UserDAO;
import com.example.SSMS.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Set;

@Service
public class UserService implements UserServiceI {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser user = userDAO.findByEmail(username).orElseThrow();
                if(user != null){
                    Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                            new SimpleGrantedAuthority(user.getRole().name()));
                    System.out.println(new User(user.getUsername(), user.getPassword(), authorities));
                    return new User(user.getUsername(), user.getPassword(), authorities);
                }
                else{
                    throw new RecordNotFoundException("User Not Found");
                }
            }
        };
    }

    @Override
    public void createSuperAdminIfNotExists() {
        AppUser super_admin = userDAO.findByRole(Roles.SUPER_ADMIN);
        AppUser user = new AppUser();
        if(super_admin == null){
            user.setRole(Roles.SUPER_ADMIN);
            user.setEmail("admin@mail.com");
            user.setPassword(new BCryptPasswordEncoder().encode("admin1234"));
            user.setEmployee(null);
            userDAO.save(user);
        }
    }
}
