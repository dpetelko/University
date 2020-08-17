package com.petelko.university.repository;

import com.petelko.university.model.Auditory;
import com.petelko.university.repository.custom.CustomizedAuditoryRepository;

public interface AuditoryRepository extends GlobalRepository<Auditory, Long>, CustomizedAuditoryRepository {
    boolean existsAuditoryById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
