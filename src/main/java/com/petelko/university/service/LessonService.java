package com.petelko.university.service;

import com.petelko.university.controller.dto.LessonFilterDTO;
import com.petelko.university.model.Lesson;
import com.petelko.university.model.dto.LessonDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface LessonService extends Service<Lesson> {

    List<LessonDTO> getAllDTO();
    List<LessonDTO> getAllActiveDTO();
    LessonDTO getDTOById(Long id);
    LessonDTO create(LessonDTO lessonDto);
    LessonDTO update(LessonDTO lessonDto);
    boolean existsLessonById(Long id);
    boolean isAuditoryFree(Long auditoryId, Long lectureId, Long lessonId, LocalDate date);
    boolean isTeacherFree(Long teacherId, Long lectureId, Long lessonId, LocalDate date);
    Set<Long> getBusyGroups(LocalDate date, Long lectureId, Long auditoryId);
    List <LessonDTO> getLessons(LessonFilterDTO lessonFilter);
}
