package com.wncsl.account.domain.account.entity;

import com.wncsl.account.domain.BusinessException;
import com.wncsl.account.domain.account.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void validatePassword_shouldThrowErrorWhenPasswordIsInvalid() {

        Account account =  new Account("User2", "user2");

        Assertions.assertThrows(BusinessException.class, () -> account.createPassword("12"));

    }

    @Test
    void validatePassword_whenTheValueIsGreater3Character() {

        Account account =  new Account("User2", "user2");

        Assertions.assertDoesNotThrow(() ->account.createPassword("123"));
    }


}