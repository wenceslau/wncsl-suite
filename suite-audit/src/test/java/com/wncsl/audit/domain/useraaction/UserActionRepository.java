package com.wncsl.audit.domain.useraaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, UUID> {
}
