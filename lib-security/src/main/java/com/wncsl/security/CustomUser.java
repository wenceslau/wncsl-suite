package com.wncsl.security;//package com.wncsl.account.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class CustomUser extends User {
    private static final long serialVersionUID = 1L;
    public CustomUser(String user, String pass, List<SimpleGrantedAuthority> authorityList) {
        super(user, pass, authorityList);
    }
}