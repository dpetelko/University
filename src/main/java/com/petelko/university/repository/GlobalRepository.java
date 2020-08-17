package com.petelko.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GlobalRepository<T, ID> extends JpaRepository<T, ID> {
    
    List<T> findByDeletedFalse();
}
