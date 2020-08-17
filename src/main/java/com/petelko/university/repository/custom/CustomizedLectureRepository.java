package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.LectureDTO;

public interface CustomizedLectureRepository extends CustomizedRepository<LectureDTO> {
    LectureDTO findDTOByName(String name);
}
