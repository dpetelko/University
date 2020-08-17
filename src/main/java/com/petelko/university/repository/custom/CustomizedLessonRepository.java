package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.service.dto.FilterDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CustomizedLessonRepository extends CustomizedRepository<LessonDTO> {
    boolean isAuditoryNotUsedByDateAndLecture(Long auditoryId, Long lectureId, Long lessonId, LocalDate date);
    boolean isTeacherNotUsedByDateAndLecture(Long teacherId, Long lectureId, Long lessonId, LocalDate date);
    Set<Long> getGroupIdListFromLessonByDataAndLecture(LocalDate date, Long lectureId, Long auditoryId);
    List<LessonDTO> getLessons(FilterDTO lessonFilter);
}
