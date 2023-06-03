package com.wncsl.core.domain.account.entity;

import java.util.UUID;

public class UserFactory {

    public static User create(String name,String username){
        return createWithId(UUID.randomUUID(), name, username);
    }

    public static User createWithId(UUID uuid,  String name, String username){
        return new User(uuid, name, username);
    }

}
