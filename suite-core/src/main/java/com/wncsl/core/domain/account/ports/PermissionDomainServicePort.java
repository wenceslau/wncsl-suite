package com.wncsl.core.domain.account.ports;

import com.wncsl.core.domain.InterfaceDomainService;
import com.wncsl.core.domain.account.entity.Permission;

import java.util.UUID;

public interface PermissionDomainServicePort extends InterfaceDomainService<Permission> {

    boolean existByUuid(UUID uuid);

}
