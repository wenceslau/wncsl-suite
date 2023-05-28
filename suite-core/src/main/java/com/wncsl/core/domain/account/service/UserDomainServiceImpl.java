package com.wncsl.core.domain.account.service;

import com.wncsl.core.domain.BusinessException;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.domain.account.ports.UserDomainServicePort;
import com.wncsl.core.domain.account.ports.UserPersistencePort;

import java.util.Set;
import java.util.UUID;

public class UserDomainServiceImpl implements UserDomainServicePort {

    private UserPersistencePort userPersistencePort;
    private PermissionDomainServicePort permissionDomainServicePort;

    public UserDomainServiceImpl(UserPersistencePort userPersistencePort, PermissionDomainServicePort permissionDomainServicePort) {
        this.userPersistencePort = userPersistencePort;
        this.permissionDomainServicePort = permissionDomainServicePort;
    }

    public UUID create(User user){
        usernameAlreadyExist(user.getUsername());

        for (Permission permission : user.getPermissions()) {
            if (permissionDomainServicePort.existByUuid(permission.getUuid())== false){
                throw new BusinessException("The permission UUID "+permission.getUuid()+" does not exist");
            }
        }

        UUID uuid = userPersistencePort.create(user);
        return uuid;
    }

    public UUID update(User user){
        userPersistencePort.update(user);
        return user.getUuid();
    }

    public Set<User> fildAll() {
        return userPersistencePort.findAll();
    }

    public User findById(UUID id){
        return userPersistencePort.findById(id);
    }

    private void usernameAlreadyExist(String username){

        boolean exist = userPersistencePort.existByUsername(username);

        if (exist){
            throw new BusinessException("This username had already exist");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userPersistencePort.findByUsername(username);
    }
}
