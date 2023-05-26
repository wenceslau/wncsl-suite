package com.wncsl.core.domain.account.entity;

import com.wncsl.core.domain.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class UserTest {

    @Test
    void validatePassword_shouldThrowErrorWhenPasswordIsInvalid() {

        User user =  new User("User2", "user2", Collections.emptyList());

        Assertions.assertThrows(BusinessException.class, () -> user.createPassword("12"));

    }

    @Test
    void validatePassword_whenTheValueIsGreater3Character() {

        User user =  new User("User2", "user2", Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> user.createPassword("123"));
    }

    /**
     * Error when the new and old password is the same
     */
    @Test
    void changePassword_whenNewAndOldPasswordIsEquals(){

        String oldPass = "12345678";
        String newPass = "12345678";

        User user = new User("User1", "usr1", Collections.emptyList());
        user.createPassword(oldPass);

        Assertions.assertThrows(BusinessException.class, () -> user.changePassword(oldPass, newPass));

    }

    /**
     * Success when the new and old password is diferent
     */
    @Test
    void changePassword_whenNewAndOldPasswordIsNotEquals(){

        String oldPass = "12345678";
        String newPass = "12345679";

        User user = new User("User1", "usr1", Collections.emptyList());
        user.createPassword(oldPass);

        Assertions.assertDoesNotThrow(() -> user.changePassword(oldPass, newPass));

    }


    /**
     * Error when the old password is different for the current
     */
    @Test
    void changePassword_whenOldPasswordIsNoEqualsCurrent(){

        User user = new User("User1", "usr1", Collections.emptyList());
        user.createPassword("1234567890");

        String oldPass = "12345678";
        String newPass = "12345679";

        Assertions.assertThrows(BusinessException.class, () -> user.changePassword(oldPass, newPass));

    }

    /**
     * Error when the old password is different for the current
     */
    @Test
    void changePassword_whenOldPasswordIsEqualsCurrent(){

        User user = new User("User1", "usr1", Collections.emptyList());
        user.createPassword("12345678");

        String oldPass = "12345678";
        String newPass = "12345679";

        Assertions.assertDoesNotThrow(() -> user.changePassword(oldPass, newPass));

    }


}