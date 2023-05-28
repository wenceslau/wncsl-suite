package com.wncsl.core.domain.account.entity;

import com.wncsl.core.domain.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    /**
     * Error when the new and old password is the same
     */
    @Test
    void changePassword_whenNewAndOldPasswordIsEquals(){

        String oldPass = "12345678";
        String newPass = "12345678";

        User user = new User(null,"User1", "usr1");
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

        User user = new User(null,"User1", "usr1");
        user.createPassword(oldPass);

        Assertions.assertDoesNotThrow(() -> user.changePassword(oldPass, newPass));

    }


    /**
     * Error when the old password is different for the current
     */
    @Test
    void changePassword_whenOldPasswordIsNoEqualsCurrent(){

        User user = new User(null,"User1", "usr1");
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

        User user = new User(null,"User1", "usr1");
        user.createPassword("12345678");

        String oldPass = "12345678";
        String newPass = "12345679";

        Assertions.assertDoesNotThrow(() -> user.changePassword(oldPass, newPass));

    }

    @Test
    void changeName_whenNameIsValid() {

        User user = new User(null,"User1", "usr1");

        user.changeName("User2");

        Assertions.assertEquals("User2", user.getName());

    }

    @Test
    void changeName_whenNameIsNull() {

        User user = new User(null,"User1", "usr1");

        Assertions.assertThrows(BusinessException.class, () -> user.changeName(null));

    }

    @Test
    void changeName_whenNameIsBlank() {

        User user = new User(null,"User1", "usr1");

        Assertions.assertThrows(BusinessException.class, () -> user.changeName(""));

    }

    @Test
    void validatePassword_shouldThrowErrorWhenPasswordIsInvalid() {

        User user =  new User(null,"User2", "user2");

        Assertions.assertThrows(BusinessException.class, () -> user.createPassword("12"));

    }

    @Test
    void validatePassword_whenTheValueIsGreater3Character() {

        User user =  new User(null,"User2", "user2");

        Assertions.assertDoesNotThrow(() -> user.createPassword("123"));
    }
}