package com.petelko.university.repository.custom;

import java.util.List;

public interface CustomizedRepository<T> {

    List<T> findAllDTO();

    List<T> findAllActiveDTO();

    T findDTOById(Long id);

    void delete(Long id);

    void undelete(Long id);
}
