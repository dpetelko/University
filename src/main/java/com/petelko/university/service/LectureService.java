package com.petelko.university.service;

import com.petelko.university.model.Lecture;
import com.petelko.university.model.dto.LectureDTO;

import java.util.List;

public interface LectureService extends Service<Lecture> {
    List<LectureDTO> getAllDTO();
    List<LectureDTO> getAllActiveDTO();
    LectureDTO getDTOById(Long id);
    LectureDTO create(LectureDTO lectureDto);
    LectureDTO update(LectureDTO lectureDto);
    boolean existsLectureById(Long id);
    LectureDTO getDTOByName(String name);
    boolean isUniqueName(String name, Long id);
}
