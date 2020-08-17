package com.petelko.university.service;

import java.util.List;

public interface Service<T> {
    T getById(Long id);
    List<T> getAll();
    T create(T t);
    T update(T t);
    void delete(Long id);
    void undelete(Long id);
}
