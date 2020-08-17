package com.petelko.university.service;

import com.petelko.university.model.Subject;
import com.petelko.university.model.dto.SubjectDTO;

import java.util.List;

public interface SubjectService extends Service<Subject> {
    List<SubjectDTO> getAllDTO();
    List<SubjectDTO> getAllActiveDTO();
    SubjectDTO getDTOById(Long id);
    SubjectDTO create(SubjectDTO subjectDto);
    SubjectDTO update(SubjectDTO subjectDto);
    boolean existsSubjectById(Long id);
    SubjectDTO getDTOByName(String name);
    boolean isUniqueName(String name, Long id);
}
