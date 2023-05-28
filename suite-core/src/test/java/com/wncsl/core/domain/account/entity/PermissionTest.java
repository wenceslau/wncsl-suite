package com.wncsl.core.domain.account.entity;

import com.wncsl.core.domain.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

    @Test
    void changeDescription_whenDescriptionIsValid() {

        Permission permission = new Permission(null, "role", "description");

        permission.changeDescription("changed");

        Assertions.assertEquals(permission.getDescription(), "changed");

    }

    @Test
    void changeDescription_whenDescriptionIsNull() {

        Permission permission = new Permission(null, "role", "description");

        Assertions.assertThrows( BusinessException.class, () -> permission.changeDescription(null) );

    }

    @Test
    void changeDescription_whenDescriptionIsBlank() {

        Permission permission = new Permission(null, "role", "description");

        Assertions.assertThrows( BusinessException.class, () -> permission.changeDescription("") );

    }

    @Test
    void changeRole_whenRoleIsValid() {

        Permission permission = new Permission(null, "role", "description");

        Assertions.assertEquals(permission.getRole(), "role");

    }

    @Test
    void changeRole_whenRoleIsNull() {

        Assertions.assertThrows( BusinessException.class, () -> new Permission(null, null, "description") );

    }

    @Test
    void changeRole_whenRoleIsIsBlank() {

        Assertions.assertThrows( BusinessException.class, () -> new Permission(null, "", "description") );

    }
}