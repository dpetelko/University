package com.petelko.university.repository;

import com.petelko.university.model.Lecture;
import com.petelko.university.repository.custom.CustomizedLectureRepository;

public interface LectureRepository extends GlobalRepository<Lecture, Long>, CustomizedLectureRepository {
    boolean existsLectureById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
