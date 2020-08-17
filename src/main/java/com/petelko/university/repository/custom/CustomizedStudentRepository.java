package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.StudentDTO;

public interface CustomizedStudentRepository extends CustomizedRepository<StudentDTO> {
    StudentDTO findDTOByEmail(String email);
}
