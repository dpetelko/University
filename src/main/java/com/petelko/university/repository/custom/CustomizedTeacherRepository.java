package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.TeacherDTO;

public interface CustomizedTeacherRepository extends CustomizedRepository<TeacherDTO> {
    TeacherDTO findDTOByEmail(String email);
}
