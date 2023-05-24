package com.wncsl.account.presentation.account.service;

import com.wncsl.account.domain.account.entity.Account;
import com.wncsl.account.domain.account.entity.Permission;
import com.wncsl.account.domain.account.repository.AccountRepository;
import com.wncsl.account.domain.account.repository.PermissionRepository;
import com.wncsl.account.domain.account.service.AccountDomainService;
import com.wncsl.account.domain.account.service.PermissionDomainService;
import com.wncsl.account.infra.grpc.GrpcClientService;
import com.wncsl.account.presentation.account.dto.AccountDTO;
import com.wncsl.account.presentation.account.dto.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private GrpcClientService grpcClientService;

    private PermissionDomainService permissionDomainService;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionDomainService = new PermissionDomainService(permissionRepository);
    }

    public PermissionDTO create(PermissionDTO permissionDTO){

        Permission permission = permissionDTO.toEntity();

        permissionDomainService.create(permission);

        permissionDTO = permission.toDTO();

        grpcClientService.createPermission(permissionDTO);

        return permissionDTO;
    }

    public PermissionDTO update(UUID id, PermissionDTO permissionDTO){

        Permission permission  = permissionDomainService.findById(id);
        permission.changeRole(permissionDTO.getRole());
        permission.changeDescription(permissionDTO.getDescription());
        permissionDomainService.update(permission);

        return permissionDTO;
    }

    public List<PermissionDTO> listAll() {

        return permissionDomainService.fildAll()
                .stream()
                .map(a -> a.toDTO())
                .collect(Collectors.toList());
    }
}
