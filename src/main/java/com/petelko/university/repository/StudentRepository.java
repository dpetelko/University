package com.petelko.university.repository;

import org.springframework.stereotype.Repository;

import com.petelko.university.model.Student;
import com.petelko.university.repository.custom.CustomizedStudentRepository;

@Repository
public interface StudentRepository extends GlobalRepository<Student, Long>, CustomizedStudentRepository {
    boolean existsStudentByGroupId(Long groupId);
    boolean existsStudentByIdAndGroupId(Long id, Long groupId);
    boolean existsStudentById(Long id);
    int countByGroupId(Long id);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
}
