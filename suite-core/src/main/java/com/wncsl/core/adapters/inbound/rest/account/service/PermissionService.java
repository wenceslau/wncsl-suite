package com.wncsl.core.adapters.inbound.rest.account.service;

import com.wncsl.core.adapters.mappers.PermissionMapper;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.adapters.outbound.grpc.GrpcAccountClientService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private GrpcAccountClientService grpcAccountClientService;

    private PermissionDomainServicePort permissionDomainServiceImpl;

    public PermissionService(PermissionDomainServicePort permissionDomainServiceImpl) {
        this.permissionDomainServiceImpl = permissionDomainServiceImpl;
    }

    public PermissionDTO create(PermissionDTO permissionDTO){

        Permission permission = new Permission(null, permissionDTO.getRole(), permissionDTO.getDescription());

       permissionDomainServiceImpl.create(permission);
       grpcAccountClientService.createPermission(permissionDTO);

       permissionDTO = PermissionMapper.toDto(permission);


        return permissionDTO;
    }

    public PermissionDTO update(UUID id, PermissionDTO permissionDTO){

        Permission permission  = permissionDomainServiceImpl.findById(id);
        permission.changeDescription(permissionDTO.getDescription());
        permissionDomainServiceImpl.update(permission);

        return permissionDTO;
    }

    public List<PermissionDTO> listAll() {

        return permissionDomainServiceImpl.fildAll()
                .stream()
                .map(p -> PermissionMapper.toDto(p))
                .collect(Collectors.toList());
    }

    public PermissionDTO findById(UUID uuid) {
        Permission permission = permissionDomainServiceImpl.findById(uuid);
        return PermissionMapper.toDto(permission);
    }
}
