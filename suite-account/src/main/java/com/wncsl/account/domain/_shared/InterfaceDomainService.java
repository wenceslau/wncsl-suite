package com.wncsl.account.domain._shared;

import java.util.Set;
import java.util.UUID;

public interface InterfaceDomainService<T> {

    UUID create(T entity);

    void update(T entity);

    Set<T> fildAll();

    T findById(UUID id);
}
