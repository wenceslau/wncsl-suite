package com.wncsl.core.adapters.outbound.persistence.account.repository;

import com.wncsl.core.adapters.mappers.UserMapper;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.core.domain.account.entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryImplTest {

    @Mock
    UserJpaRepository userJpaRepository;

    @InjectMocks
    UserPersistencePortImpl userRepository = new UserPersistencePortImpl(userJpaRepository);

    @BeforeEach
    void initializer(){
        MockitoAnnotations.openMocks(this); //We need this for initiate objects annotated with Mockito annotations
    }

    @Test
    void create_successfully() {

        User user = UserFactory.create("User", "usr");
        user.createPassword("12345678");

        UserModel userModel = UserMapper.toModel(user);
        UUID uuid = userModel.getUuid();

        Mockito.when(userJpaRepository.save(userModel)).thenReturn(userModel);

        UUID uuid1 =  userRepository.create(user);

        assertEquals(uuid1, uuid);

    }
}