package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.FacultyDTO;

public interface CustomizedFacultyRepository extends CustomizedRepository<FacultyDTO> {
    FacultyDTO findDTOByName(String name);
}
