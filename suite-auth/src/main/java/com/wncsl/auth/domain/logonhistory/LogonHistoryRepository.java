package com.wncsl.auth.domain.logonhistory;

import com.wncsl.auth.domain.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogonHistoryRepository extends JpaRepository<LogonHistory, Long> {

}
