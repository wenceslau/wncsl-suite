package com.wncsl.audit.domain.useraaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserActionRepository extends PagingAndSortingRepository<UserAction, UUID> {

    Page<UserAction> findAllByObjectUuidAndServiceAndObjectName(Pageable pageable, String uuid, String service, String objectName );
}
