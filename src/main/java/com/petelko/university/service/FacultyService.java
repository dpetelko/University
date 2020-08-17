package com.petelko.university.service;

import com.petelko.university.model.Faculty;
import com.petelko.university.model.dto.FacultyDTO;

import java.util.List;

public interface FacultyService extends Service<Faculty> {
    List<FacultyDTO> getAllDTO();
    List<FacultyDTO> getAllActiveDTO();
    FacultyDTO getDTOById(Long id);
    FacultyDTO create(FacultyDTO facultyDto);
    FacultyDTO update(FacultyDTO facultyDto);
    boolean existsFacultyById(Long id);
    FacultyDTO getDTOByName(String name);
    boolean isUniqueName(String name, Long id);
}
