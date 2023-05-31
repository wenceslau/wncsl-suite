package com.wncsl.auth.domain.permission;

import com.wncsl.auth.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    boolean existsByRole(String role);

    boolean existsByRoleAndUuidIsNot(String role, UUID uuid);
}
