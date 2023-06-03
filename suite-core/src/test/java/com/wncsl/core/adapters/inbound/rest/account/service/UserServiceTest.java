package com.wncsl.core.adapters.inbound.rest.account.service;

import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.entity.UserFactory;
import com.wncsl.core.domain.account.ports.UserDomainServicePort;
import com.wncsl.core.domain.account.service.UserDomainServicePortImpl;
import com.wncsl.core.adapters.outbound.grpc.GrpcAccountClientService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


//@ExtendWith(MockitoExtension.class)   // JUnit 5
class UserServiceTest {

    AutoCloseable openMocks;

    @Mock
    private UserDomainServicePortImpl userDomainServicePort;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks    //The classe exact we will need test, and this annotation will inject the others mock inside it
    private UserService userService;

    @BeforeEach
    void setUp(){
        openMocks = MockitoAnnotations.openMocks(this); //We need this for initiate objects annotated with Mockito annotations
    }
    @AfterEach
    void tearDown() throws Exception {
        // my tear down code...
        openMocks.close();
    }

   // @Test
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

        User user = UserFactory.create(userDTO.getName(), userDTO.getUsername());
        user.addPermissionUuid(permissionDTO.getUuid());

        UUID uuid = UUID.randomUUID();
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("12345678");
        Mockito.when(userDomainServicePort.create(Mockito.any())).thenReturn(uuid);
        Mockito.when(userDomainServicePort.findById(user.getId())).thenReturn(user);

        userDTO = userService.create(userDTO);

        assertNotNull(userDTO.getPermissions());
        assertEquals(userDTO.getPermissions().size(), 1);

    }

   // @Test
    void changePassword_whenValueIsCorrect() {

        UUID uuid = UUID.randomUUID();
        User user = UserFactory.createWithId(uuid, "Name", "usr");
        user.createPassword("123456");

        String oldPass = "123456", newPass = "12345678";

        String value = oldPass+"#&&#"+newPass;

        value = Base64.getEncoder().encodeToString(value.getBytes());
        System.out.println(value);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

        Mockito.when(userDomainServicePort.findById(uuid)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(oldPass)).thenReturn(oldPass);
        Mockito.when(passwordEncoder.encode(newPass)).thenReturn(newPass);
        Mockito.when(userDomainServicePort.update(user)).thenReturn(null);

        String finalValue = value;

        assertDoesNotThrow(()-> userService.changePassword(uuid, finalValue));

    }

    //@Test
    void changePassword_whenValueIsNotCorrect() {

        UUID uuid = UUID.randomUUID();
        User user = UserFactory.createWithId(uuid, "Name", "usr");
        user.createPassword("123456");

        String oldPass = "123456", newPass = "12345678";

        String value = "ops";

        value = Base64.getEncoder().encodeToString(value.getBytes());
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

        Mockito.when(userDomainServicePort.findById(uuid)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(oldPass)).thenReturn(oldPass);
        Mockito.when(passwordEncoder.encode(newPass)).thenReturn(newPass);
        Mockito.when(userDomainServicePort.update(user)).thenReturn(null);

        String finalValue = value;

        assertThrows(RuntimeException.class, ()->userService.changePassword(uuid, finalValue));

    }
}