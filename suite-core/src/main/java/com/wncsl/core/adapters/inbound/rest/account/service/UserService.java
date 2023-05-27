package com.wncsl.core.adapters.inbound.rest.account.service;

import com.wncsl.core.adapters.mappers.UserMapper;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.domain.account.ports.UserDomainServicePort;
import com.wncsl.core.adapters.outbound.grpc.GrpcClientService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private GrpcClientService grpcClientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDomainServicePort userDomainService;

    private PermissionDomainServicePort permissionDomainService;

    public UserService(UserDomainServicePort userDomainService, PermissionDomainServicePort permissionDomainService) {
        this.userDomainService =userDomainService;
        this.permissionDomainService = permissionDomainService;
    }

    public UserDTO create(UserDTO userDTO){

        User user = new User(null ,userDTO.getName(), userDTO.getUsername());
        user.validatePassword(userDTO.getPassword());
        user.createPassword(passwordEncoder.encode(userDTO.getPassword()));

        for (PermissionDTO permissionDTO : userDTO.getPermissions()) {
            user.addPermissionUuid(permissionDTO.getUuid());
        }

        userDomainService.create(user);
        grpcClientService.createUser(user);

        userDTO = UserMapper.toDto(user);

        return userDTO;
    }

    public UserDTO update(UUID id, UserDTO userDTO){

        User user = userDomainService.findById(id);
        user.changeName(userDTO.getName());
        userDomainService.update(user);
        userDTO = UserMapper.toDto(user);

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
                .map(entity -> UserMapper.toDto(entity))
                .collect(Collectors.toList());
    }
}
