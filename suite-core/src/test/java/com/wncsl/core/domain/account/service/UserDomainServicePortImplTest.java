package com.wncsl.core.domain.account.service;

import com.wncsl.core.domain.BusinessException;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.entity.UserFactory;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.domain.account.ports.UserPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDomainServicePortImplTest {

    @Mock
    UserPersistencePort userPersistencePort;
    @Mock
    PermissionDomainServicePort permissionDomainServicePort;
    @InjectMocks
    UserDomainServicePortImpl userDomainService = new UserDomainServicePortImpl(userPersistencePort, permissionDomainServicePort);

    @BeforeEach
    void initializer(){
        MockitoAnnotations.openMocks(this); //We need this for initiate objects annotated with Mockito annotations
    }

    @Test
    void create_whenIsAllValid() {

        User user = UserFactory.create("User", "usr");
        user.createPassword("123456");

        UUID uuid = user.getId();

        Mockito.when(userPersistencePort.create(Mockito.any())).thenReturn(uuid);

        userDomainService.create(user);

        assertEquals(user.getId(), uuid);

    }

    @Test
    void create_whenTheUsernameAlreadyExist() {

        String username = "usr";

        Mockito.when(userPersistencePort.existByUsername(username)).thenReturn(true);

        User user = UserFactory.create("User", username);

        Mockito.when(userPersistencePort.create(Mockito.any())).thenReturn(null);

        Assertions.assertThrows(BusinessException.class, () ->  userDomainService.create(user));

    }

    @Test
    void create_whenOnePermissionDoesNotExist() {

        String username = "usr";

        Mockito.when(permissionDomainServicePort.existByUuid(Mockito.any())).thenReturn(false);

        User user = UserFactory.create("User", username);
        user.addPermissionUuid(UUID.randomUUID());

        Mockito.when(userPersistencePort.create(Mockito.any())).thenReturn(null);

        Assertions.assertThrows(BusinessException.class, () ->  userDomainService.create(user));

    }

    @Test
    void update() {
    }

    @Test
    void fildAll() {
    }

    @Test
    void findById() {
    }
}