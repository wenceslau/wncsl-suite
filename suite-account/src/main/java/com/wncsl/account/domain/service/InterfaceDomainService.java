package com.wncsl.account.domain.service;

import com.wncsl.account.domain.entity.Account;

import java.util.Set;
import java.util.UUID;

public interface InterfaceDomainService<T> {

    UUID create(T entity);

    void update(T entity);

    Set<T> fildAll();

    T findById(UUID id);
}
