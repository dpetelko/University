package com.petelko.university.repository;

import com.petelko.university.model.Teacher;
import com.petelko.university.repository.custom.CustomizedTeacherRepository;

public interface TeacherRepository extends GlobalRepository<Teacher, Long>, CustomizedTeacherRepository {
    boolean existsTeacherById(Long id);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

}
