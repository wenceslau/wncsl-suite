package com.wncsl.core.domain.account.service;

import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.domain.account.ports.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDomainServiceImplTest {

    @Mock
    UserPersistencePort userPersistencePort;

    @Mock
    PermissionDomainServicePort permissionDomainServicePort;

    @InjectMocks
    UserDomainServiceImpl userDomainService = new UserDomainServiceImpl(userPersistencePort, permissionDomainServicePort);

    @BeforeEach
    void initializer(){
        MockitoAnnotations.openMocks(this); //We need this for initiate objects annotated with Mockito annotations
    }

    @Test
    void create_successfully() {

        User user = new User(null,"User", "usr");
        user.createPassword("123456");

        UUID uuid = user.getId();

        Mockito.when(userPersistencePort.create(Mockito.any())).thenReturn(uuid);

        userDomainService.create(user);

        assertEquals(user.getId(), uuid);

    }

    @Test
    void create_notSuccessfully() {

        User user = new User(null,"User", "usr");
        user.createPassword("123456");

        UUID uuid = user.getId();

        Mockito.when(userPersistencePort.create(Mockito.any())).thenReturn(null);

        userDomainService.create(user);

        assertEquals(user.getId(), uuid);

    }
}