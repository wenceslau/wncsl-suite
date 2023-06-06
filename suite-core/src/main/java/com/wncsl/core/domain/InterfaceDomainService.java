package com.wncsl.core.domain;

import java.util.Set;
import java.util.UUID;

public interface InterfaceDomainService<T> {

    UUID create(T entity);

    UUID update(T entity);

    T findById(UUID id);
}
