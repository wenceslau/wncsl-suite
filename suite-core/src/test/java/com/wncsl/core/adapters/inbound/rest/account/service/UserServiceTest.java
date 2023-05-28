package com.wncsl.core.adapters.inbound.rest.account.service;

import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.service.UserDomainServiceImpl;
import com.wncsl.core.adapters.outbound.grpc.GrpcAccountClientService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {


    @Mock
    private UserDomainServiceImpl userDomainService;
    @Mock
    private GrpcAccountClientService grpcAccountClientService;
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

        User user = new User(null ,userDTO.getName(), userDTO.getUsername());
        user.addPermissionUuid(permissionDTO.getUuid());

        Mockito.when(userDomainService.create(Mockito.any())).thenReturn(null);
        Mockito.when(grpcAccountClientService.createUser(Mockito.any())).thenReturn(null);
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("12345678");
        Mockito.when(userDomainService.findById(Mockito.any())).thenReturn(user);

        userDTO = userService.create(userDTO);

        assertNotNull(userDTO.getPermissions());
        assertEquals(userDTO.getPermissions().size(), 1);

    }

    @Test
    void changePassword_whenValueIsCorrect() {

        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "Name", "usr");
        user.createPassword("123456");

        String oldPass = "123456", newPass = "12345678";

        String value = oldPass+"#&&#"+newPass;

        value = Base64.getEncoder().encodeToString(value.getBytes());
        System.out.println(value);

        Mockito.when(userDomainService.findById(uuid)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(oldPass)).thenReturn(oldPass);
        Mockito.when(passwordEncoder.encode(newPass)).thenReturn(newPass);
        Mockito.when(userDomainService.update(user)).thenReturn(null);

        String finalValue = value;

        assertDoesNotThrow(()-> userService.changePassword(uuid, finalValue));

    }

    @Test
    void changePassword_whenValueIsNotCorrect() {

        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "Name", "usr");
        user.createPassword("123456");

        String oldPass = "123456", newPass = "12345678";

        String value = "ops";

        value = Base64.getEncoder().encodeToString(value.getBytes());

        Mockito.when(userDomainService.findById(uuid)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(oldPass)).thenReturn(oldPass);
        Mockito.when(passwordEncoder.encode(newPass)).thenReturn(newPass);
        Mockito.when(userDomainService.update(user)).thenReturn(null);

        String finalValue = value;

        assertThrows(RuntimeException.class, ()->userService.changePassword(uuid, finalValue));

    }
}