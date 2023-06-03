package com.wncsl.security;//package com.wncsl.account.infra.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CustomUser extends User {
    private static final long serialVersionUID = 1L;

    private UUID userUuid;

    private String type;

    public CustomUser(UUID userUuid, String user, String pass, String type, List<SimpleGrantedAuthority> authorityList) {
        super(user, pass, authorityList);
        this.userUuid = userUuid;
        this.type = type;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public String getType() {
        return type;
    }
}