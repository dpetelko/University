package com.petelko.university.service;

import com.petelko.university.model.Teacher;
import com.petelko.university.model.dto.TeacherDTO;

import java.util.List;

public interface TeacherService extends Service<Teacher> {
    List<TeacherDTO> getAllDTO();
    List<TeacherDTO> getAllActiveDTO();
    TeacherDTO getDTOById(Long id);
    TeacherDTO create(TeacherDTO teacher);
    TeacherDTO update(TeacherDTO teacher);
    boolean existsTeacherById(Long id);
    boolean isUniqueEmail(String email, Long id);
}
