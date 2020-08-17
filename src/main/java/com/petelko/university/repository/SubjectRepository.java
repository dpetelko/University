package com.petelko.university.repository;

import com.petelko.university.model.Subject;
import com.petelko.university.repository.custom.CustomizedSubjectRepository;

public interface SubjectRepository extends GlobalRepository<Subject, Long>, CustomizedSubjectRepository {
    boolean existsSubjectById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}

