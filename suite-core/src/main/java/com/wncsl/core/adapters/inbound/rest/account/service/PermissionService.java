package com.wncsl.core.adapters.inbound.rest.account.service;

import com.wncsl.core.adapters.mappers.PermissionMapper;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.entity.PermissionFactory;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.adapters.outbound.grpc.GrpcAccountClientService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionDomainServicePort permissionDomainServicePort;

    public PermissionService(PermissionDomainServicePort permissionDomainServiceImpl) {

        this.permissionDomainServicePort = permissionDomainServiceImpl;
    }

    public PermissionDTO create(PermissionDTO permissionDTO){

        Permission permission = PermissionFactory.create(permissionDTO.getRole(), permissionDTO.getDescription());

       permissionDomainServicePort.create(permission);
       permission = permissionDomainServicePort.findById(permission.getUuid());
       //grpcAccountClientService.createPermission(permission);

       permissionDTO = PermissionMapper.toDto(permission);

        return permissionDTO;
    }

    public PermissionDTO update(UUID uuid, PermissionDTO permissionDTO){

        Permission permission  = permissionDomainServicePort.findById(uuid);
        permission.changeDescription(permissionDTO.getDescription());

        permissionDomainServicePort.update(permission);
        permission = permissionDomainServicePort.findById(uuid);

        permissionDTO = PermissionMapper.toDto(permission);

        return permissionDTO;
    }

    public List<PermissionDTO> listAll() {

        return permissionDomainServicePort.fildAll()
                .stream()
                .map(p -> PermissionMapper.toDto(p))
                .collect(Collectors.toList());
    }

    public PermissionDTO findById(UUID uuid) {

        Permission permission = permissionDomainServicePort.findById(uuid);
        return PermissionMapper.toDto(permission);
    }
}
