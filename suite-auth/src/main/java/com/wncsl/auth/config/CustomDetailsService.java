package com.wncsl.auth.config;

import com.wncsl.auth.entity.Account;
import com.wncsl.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
        System.out.println(this.getClass().getSimpleName()+".loadUserByUsername: "+ LocalTime.now());
        Account account;
        try {
            account = new Account();
            account.setUsername("user");
            account.setId(UUID.randomUUID());
            String pass = (passwordEncoder.encode(username + "Password"));
            SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_CREATE_ACCOUNT");
            CustomUser customUser = new CustomUser("user", pass, List.of(role) );
            return customUser;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}