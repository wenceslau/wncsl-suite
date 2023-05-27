package com.wncsl.core.adapters.inbound.rest.account.service;

import com.wncsl.core.domain.account.service.UserDomainServiceImpl;
import com.wncsl.core.adapters.outbound.grpc.GrpcClientService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {


    @Mock
    private UserDomainServiceImpl userDomainService;
    @Mock
    private GrpcClientService grpcClientService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks    //The classe exact we will need test, and this annotation will inject the others mock inside it
    private UserService userService = new UserService(null, null);

    @BeforeEach
    void initializer(){
        MockitoAnnotations.openMocks(this); //We need this for initiate objects annotated with Mockito annotations
    }

    @Test
    void create_withListOfPermission() {

        PermissionDTO permissionDTO = PermissionDTO.builder()
                .uuid(UUID.randomUUID())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .name("Administrator")
                .username("admin")
                .password("123456")
                .permissions(List.of(permissionDTO))
                .build();

        Mockito.when(userDomainService.create(Mockito.any())).thenReturn(null);
        Mockito.when(grpcClientService.createUser(Mockito.any())).thenReturn(null);
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("12345678");

        userDTO = userService.create(userDTO);

        assertNotNull(userDTO.getPermissions());
        assertEquals(userDTO.getPermissions().size(), 1);

    }
}