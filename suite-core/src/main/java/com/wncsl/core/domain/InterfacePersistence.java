package com.wncsl.core.domain;

import java.util.Set;
import java.util.UUID;

public interface InterfacePersistence<T>
{
    UUID create(T entity);
    UUID update(T entity);
    T findById(UUID id);
//    Set<T> findAll();
}
