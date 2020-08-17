package com.petelko.university.repository;

import com.petelko.university.model.Department;
import com.petelko.university.repository.custom.CustomizedDepartmentRepository;

public interface DepartmentRepository extends GlobalRepository<Department, Long>, CustomizedDepartmentRepository {
    boolean existsDepartmentByFacultyId(Long facultyId);
    boolean existsDepartmentById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
