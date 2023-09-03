package com.example.jobsearch.dao;

import java.util.Optional;

public interface CrudDao<T, E> {
    Long save(T obj);
    Optional<T> find(E id);
    void update(T obj);
    void delete(E id);
}
