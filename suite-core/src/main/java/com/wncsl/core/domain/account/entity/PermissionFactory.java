package com.wncsl.core.domain.account.entity;

import java.util.UUID;

public class PermissionFactory {

    public static Permission create(String role ,String description){
        return createWithId(UUID.randomUUID(), role, description);
    }

    public static Permission createWithId(UUID uuid,  String role, String description){
        return new Permission(uuid, role, description);
    }

}
