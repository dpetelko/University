package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.SubjectDTO;

public interface CustomizedSubjectRepository extends CustomizedRepository<SubjectDTO> {
    SubjectDTO findDTOByName(String name);
}
