package com.wncsl.core.adapters.inbound.account.service;

import com.wncsl.core.adapters.mappers.PermissionMapper;
import com.wncsl.core.adapters.outbound.persistence.account.repository.PermissionJpaRepository;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.entity.PermissionFactory;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PermissionService {

    private final PermissionDomainServicePort permissionDomainServicePort;
    //Does not make sense queries through the domain layer, so for List pageable, the inbound access directly the outbound
    private final PermissionJpaRepository permissionJpaRepository;

    public PermissionService(PermissionDomainServicePort permissionDomainServiceImpl,
                             PermissionJpaRepository permissionJpaRepository) {

        this.permissionDomainServicePort = permissionDomainServiceImpl;
        this.permissionJpaRepository = permissionJpaRepository;
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

    public Page<PermissionDTO> listAll(Pageable pageable) {

        return permissionJpaRepository.findAll(pageable)
                .map(p -> PermissionMapper.toDto(p));

    }

    public PermissionDTO findById(UUID uuid) {

        Permission permission = permissionDomainServicePort.findById(uuid);
        return PermissionMapper.toDto(permission);
    }
}
