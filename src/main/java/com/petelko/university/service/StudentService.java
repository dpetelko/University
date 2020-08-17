package com.petelko.university.service;

import com.petelko.university.model.Student;
import com.petelko.university.model.dto.StudentDTO;

import java.util.List;

public interface StudentService extends Service<Student> {
    List<StudentDTO> getAllDTO();
    List<StudentDTO> getAllActiveDTO();
    StudentDTO getDTOById(Long id);
    StudentDTO getDTOByEmail(String email);
    StudentDTO create(StudentDTO student);
    StudentDTO update(StudentDTO student);
    int countByGroupId(Long id);
    boolean existsStudentByIdAndGroupId(Long id, Long groupId);
    boolean existsStudentById(Long id);
    boolean isUniqueEmail(String email, Long id);
}
