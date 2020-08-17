package com.petelko.university.repository;

import com.petelko.university.model.Faculty;
import com.petelko.university.repository.custom.CustomizedFacultyRepository;

public interface FacultyRepository extends GlobalRepository<Faculty, Long>, CustomizedFacultyRepository {
    boolean existsFacultyById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
