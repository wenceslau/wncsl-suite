package com.wncsl.core.adapters.inbound.rest.account.service;

import com.wncsl.core.adapters.mappers.UserMapper;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.domain.account.ports.UserDomainServicePort;
import com.wncsl.core.adapters.outbound.grpc.GrpcAccountClientService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private GrpcAccountClientService grpcAccountClientService;

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
        grpcAccountClientService.createUser(user);

        userDTO = UserMapper.toDto(user);

        return userDTO;
    }

    public UserDTO update(UUID uuid, UserDTO userDTO){

        User user = userDomainService.findById(uuid);
        user.changeName(userDTO.getName());
        userDomainService.update(user);
        grpcAccountClientService.updateUser(user);

        userDTO = UserMapper.toDto(user);

        return userDTO;
    }

    public UserDTO addPermissions(UUID userUuid, List<PermissionDTO> permissionDTOList){

        User user = userDomainService.findById(userUuid);
        for (PermissionDTO dto : permissionDTOList) {
            user.addPermissionUuid(dto.getUuid());
        }
        userDomainService.update(user);

        user = userDomainService.findById(userUuid);

        return UserMapper.toDto(user);
    }

    public void changePassword(UUID id, String value){

        String oldPassword = null;
        String newPassword = null;

        try {
            value = new String(Base64.getDecoder().decode(value));
            System.out.println(value);
            oldPassword = value.split("#&&#")[0];
            newPassword = value.split("#&&#")[1];
        } catch (Exception e) {
            throw new RuntimeException("Failure to decode password",e);
        }

        User user = userDomainService.findById(id);
        user.changePassword(passwordEncoder.encode(oldPassword), passwordEncoder.encode(newPassword));

        userDomainService.update(user);
    }

    public List<UserDTO> listAll(Pageable pageable) {

        return userDomainService.fildAll()
                .stream()
                .map(entity -> UserMapper.toDto(entity))
                .collect(Collectors.toList());
    }

    public UserDTO findById(UUID uuid) {
        User user =  userDomainService.findById(uuid);
        return UserMapper.toDto(user);
    }
}
