package com.wncsl.core.adapters.inbound.account.service;

import com.wncsl.core.adapters.mappers.UserMapper;
import com.wncsl.core.adapters.outbound.persistence.account.repository.UserJpaRepository;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.entity.UserFactory;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.domain.account.ports.UserDomainServicePort;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import java.util.Base64;

@Service
public class UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserDomainServicePort userDomainServicePort;
    //Does not make sense queries through the domain layer, so for List pageable, the inbound access directly the outbound
    private final UserJpaRepository userJpaRepository;
    private final PermissionDomainServicePort permissionDomainService;


    public UserService(PasswordEncoder passwordEncoder,
                       UserDomainServicePort userDomainServicePort,
                       PermissionDomainServicePort permissionDomainService, UserJpaRepository userJpaRepository) {

        this.passwordEncoder = passwordEncoder;
        this.userDomainServicePort = userDomainServicePort;
        this.permissionDomainService = permissionDomainService;
        this.userJpaRepository = userJpaRepository;
    }

    public UserDTO create(UserDTO userDTO){

        User user = UserFactory.create(userDTO.getName(), userDTO.getUsername());
        user.validatePassword(userDTO.getPassword());
        user.createPassword(passwordEncoder.encode(userDTO.getPassword()));

        for (PermissionDTO permissionDTO : userDTO.getPermissions()) {
            user.addPermissionUuid(permissionDTO.getUuid());
        }

        userDomainServicePort.create(user);
        user = userDomainServicePort.findById(user.getId());

        userDTO = UserMapper.toDto(user);

        return userDTO;
    }

    public UserDTO update(UUID uuid, UserDTO userDTO){

        User user = userDomainServicePort.findById(uuid);
        user.changeName(userDTO.getName());

        userDomainServicePort.update(user);

        userDTO = UserMapper.toDto(user);

        return userDTO;
    }

    public UserDTO addPermissions(UUID userUuid, List<PermissionDTO> permissionDTOList){

        User user = userDomainServicePort.findById(userUuid);
        for (PermissionDTO dto : permissionDTOList) {
            if (permissionDomainService.existByUuid(dto.getUuid())) {
                user.addPermissionUuid(dto.getUuid());
            }
        }

        userDomainServicePort.update(user);

        return UserMapper.toDto(user);
    }

    public void changePassword(UUID uuid, String value){

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

        User user = userDomainServicePort.findById(uuid);
        user.changePassword(passwordEncoder.encode(oldPassword), passwordEncoder.encode(newPassword));

        userDomainServicePort.update(user);
    }

    public Page<UserDTO> listAll(Pageable pageable) {
        return  userJpaRepository.findAll(pageable)
                .map(entity -> UserMapper.toDto(entity));
    }

    public UserDTO findById(UUID uuid) {
        User user =  userDomainServicePort.findById(uuid);
        return UserMapper.toDto(user);
    }

    public UserDTO findByUsername(String username){
        User user =  userDomainServicePort.findByUsername(username);
        return UserMapper.toDto(user);
    }
}
