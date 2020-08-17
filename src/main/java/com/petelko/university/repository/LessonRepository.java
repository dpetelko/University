package com.petelko.university.repository;

import com.petelko.university.model.Lesson;
import com.petelko.university.repository.custom.CustomizedLessonRepository;

public interface LessonRepository extends GlobalRepository<Lesson, Long>, CustomizedLessonRepository {
    boolean existsLessonById(Long id);
}
