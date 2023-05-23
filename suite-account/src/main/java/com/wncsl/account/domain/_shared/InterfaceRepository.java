package com.wncsl.account.domain._shared;

import java.util.Set;
import java.util.UUID;

public interface InterfaceRepository<T>
{
    UUID create(T entity);
    UUID update(T entity);
    T find(UUID id);
    Set<T> findAll();

}
