package com.wncsl.core.presentation.account.service;

import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.repository.UserRepository;
import com.wncsl.core.domain.account.service.UserDomainService;
import com.wncsl.core.infra.grpc.GrpcClientService;
import com.wncsl.core.presentation.account.dto.PermissionDTO;
import com.wncsl.core.presentation.account.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private GrpcClientService grpcClientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDomainService userDomainService;

    public UserService(UserRepository userRepository) {
        this.userDomainService = new UserDomainService(userRepository);
    }

    public UserDTO create(UserDTO userDTO){

        List<Permission> permissions = new ArrayList<>();
        for (PermissionDTO permissionDTO : userDTO.getPermissions()) {
            permissions.add(new Permission(permissionDTO.getRole(), permissionDTO.getDescription()));
        }
        User user = new User(userDTO.getName(), userDTO.getUsername(), permissions);
        user.validatePassword(userDTO.getPassword());
        user.createPassword(passwordEncoder.encode(userDTO.getPassword()));

        userDomainService.create(user);
        userDTO = user.toDTO();

        grpcClientService.createUser(userDTO);
        //AccountClientGrpc accountClientGrpc = new AccountClientGrpc("localhost", 9090);
        //accountClientGrpc.createAccount(account);

        return userDTO;
    }

    public UserDTO update(UUID id, UserDTO userDTO){

        User user = userDomainService.findById(id);
        user.changeName(userDTO.getName());
        userDomainService.update(user);

        return userDTO;
    }

    public void changePassword(UUID id, String oldPassword, String newPassword){

        User user = userDomainService.findById(id);
        user.changePassword(passwordEncoder.encode(oldPassword), passwordEncoder.encode(newPassword));

        userDomainService.update(user);
    }


    public List<UserDTO> listAll() {

        return userDomainService.fildAll()
                .stream()
                .map(a -> a.toDTO())
                .collect(Collectors.toList());
    }
}
