package com.petelko.university.service.impl;

import com.petelko.university.controller.dto.LessonFilterDTO;
import com.petelko.university.model.Auditory;
import com.petelko.university.model.Group;
import com.petelko.university.model.Lecture;
import com.petelko.university.model.Lesson;
import com.petelko.university.model.Lesson_;
import com.petelko.university.model.Subject;
import com.petelko.university.model.Teacher;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.repository.LessonRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.AuditoryService;
import com.petelko.university.service.LectureService;
import com.petelko.university.service.LessonService;
import com.petelko.university.service.SubjectService;
import com.petelko.university.service.TeacherService;
import com.petelko.university.service.dto.FilterDTO;
import com.petelko.university.service.exception.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class LessonServiceImpl implements LessonService {


    private LessonRepository lessonRepository;
    private GroupServiceImpl groupService;
    private SubjectService subjectService;
    private AuditoryService auditoryService;
    private TeacherService teacherService;
    private LectureService lectureService;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository,
                             GroupServiceImpl groupService,
                             SubjectService subjectService,
                             AuditoryService auditoryService,
                             TeacherService teacherService,
                             LectureService lectureService) {
        this.lessonRepository = lessonRepository;
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.auditoryService = auditoryService;
        this.teacherService = teacherService;
        this.lectureService = lectureService;
    }

    @Override
    public List<LessonDTO> getAllDTO() {
        return lessonRepository.findAllDTO();
    }

    @Override
    public List<LessonDTO> getAllActiveDTO() {
        return lessonRepository.findAllActiveDTO();
    }

    @Override
    public LessonDTO getDTOById(Long id) {
        return lessonRepository.findDTOById(id);
    }

    @Transactional
    @Override
    public LessonDTO create(LessonDTO lessonDto) {
        Lesson lesson = convertToEntity(lessonDto);
        lesson = create(lesson);
        return convertToDto(lesson);
    }

    @Transactional
    @Override
    public LessonDTO update(LessonDTO lessonDto) {
        Lesson lesson = convertToEntity(lessonDto);
        lesson = update(lesson);
        return convertToDto(lesson);
    }

    @Override
    public boolean existsLessonById(Long id) {
        return lessonRepository.existsLessonById(id);
    }

    @Override
    public boolean isAuditoryFree(Long auditoryId, Long lectureId, Long lessonId, LocalDate date) {
        return lessonRepository.isAuditoryNotUsedByDateAndLecture(auditoryId, lectureId, lessonId, date);
    }

    @Override
    public boolean isTeacherFree(Long teacherId, Long lectureId, Long lessonId, LocalDate date) {
        return lessonRepository.isTeacherNotUsedByDateAndLecture(teacherId, lectureId, lessonId, date);
    }

    @Override
    public Set<Long> getBusyGroups(LocalDate date, Long lectureId, Long auditoryId) {
        return lessonRepository.getGroupIdListFromLessonByDataAndLecture(date, lectureId, auditoryId);
    }

    @Override
    public List<LessonDTO> getLessons(LessonFilterDTO lessonFilter) {
        return lessonRepository.getLessons(convertToFilterDTO(lessonFilter));
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Lesson with id '%s' not found", id)));

    }

    @Override
    public List<Lesson> getAll() {
        Sort nameSort = Sort.by(Lesson_.DATE);
        Sort sortOrder = nameSort.ascending();
        return lessonRepository.findAll(sortOrder);
    }

    @Transactional
    @Override
    public Lesson create(Lesson lesson) {
        if (Objects.nonNull(lesson.getId())) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public Lesson update(Lesson lesson) {
        Long id = lesson.getId();
        if (isNull(id)) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        if (!existsLessonById(id)) {
            String msg = format("Lesson with id '%s' not found for update!", id);
            throw new EntityNotFoundException(msg);
        }
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!existsLessonById(id)) {
            String msg = format("Lesson with id '%s' not found for delete!", id);
            throw new EntityNotFoundException(msg);
        }
        lessonRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        if (!existsLessonById(id)) {
            String msg = format("Lesson with id '%s' not found for undelete!", id);
            throw new EntityNotFoundException(msg);
        }
        lessonRepository.undelete(id);
    }

    private LessonDTO convertToDto(Lesson lesson) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(lesson.getId());
        lessonDTO.setDate(lesson.getDate());
        lessonDTO.setDeleted(lesson.isDeleted());
        if (nonNull(lesson.getSubject())) {
            lessonDTO.setSubjectId(lesson.getSubject().getId());
            lessonDTO.setSubjectName(lesson.getSubject().getName());
            lessonDTO.setSubjectDescription(lesson.getSubject().getDescription());
        }
        if (nonNull(lesson.getTeacher())) {
            lessonDTO.setTeacherId(lesson.getTeacher().getId());
            lessonDTO.setTeacherFirstName(lesson.getTeacher().getFirstName());
            lessonDTO.setTeacherLastName(lesson.getTeacher().getLastName());
        }
        if (nonNull(lesson.getGroups())) {
            Set<GroupDTO> groupDTOSet = lesson.getGroups().stream()
                    .map(groupService::convertToDto)
                    .collect(toSet());
            lessonDTO.setGroups(groupDTOSet);
        }
        if (nonNull(lesson.getAuditory())) {
            lessonDTO.setAuditoryId(lesson.getAuditory().getId());
            lessonDTO.setAuditoryName(lesson.getAuditory().getName());
            lessonDTO.setAuditoryDescription(lesson.getAuditory().getDescription());
            lessonDTO.setAuditoryCapacity(lesson.getAuditory().getCapacity());
        }
        if (nonNull(lesson.getLecture())) {
            lessonDTO.setLectureId(lesson.getLecture().getId());
            lessonDTO.setLectureName(lesson.getLecture().getName());
            lessonDTO.setLectureStartTime(lesson.getLecture().getStartTime());
            lessonDTO.setLectureEndTime(lesson.getLecture().getEndTime());
        }
        return lessonDTO;
    }

    private Lesson convertToEntity(LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        if (nonNull(lessonDTO.getId())) {
            lesson = getById(lessonDTO.getId());
        }
        lesson.setDate(lessonDTO.getDate());
        if (nonNull(lessonDTO.getSubjectId())) {
            Subject subject = subjectService.getById(lessonDTO.getSubjectId());
            lesson.setSubject(subject);
        } else {
            lesson.setSubject(null);
        }
        if (nonNull(lessonDTO.getTeacherId())) {
            Teacher teacher = teacherService.getById(lessonDTO.getTeacherId());
            lesson.setTeacher(teacher);
        } else {
            lesson.setTeacher(null);
        }
        if (nonNull(lessonDTO.getAuditoryId())) {
            Auditory auditory = auditoryService.getById(lessonDTO.getAuditoryId());
            lesson.setAuditory(auditory);
        } else {
            lesson.setAuditory(null);
        }
        if (nonNull(lessonDTO.getLectureId())) {
            Lecture lecture = lectureService.getById(lessonDTO.getLectureId());
            lesson.setLecture(lecture);
        } else {
            lesson.setLecture(null);
        }
        if (nonNull(lessonDTO.getGroups())) {
            Set<GroupDTO> groupDTOSet = new HashSet<>();
            for (GroupDTO group : lessonDTO.getGroups()) {
                groupDTOSet.add(groupService.getDTOById(group.getId()));
            }
            Set<Group> groupSet = groupDTOSet.stream()
                    .map(groupService::convertToEntity)
                    .collect(toSet());
            lesson.setGroups(groupSet);
        } else {
            lesson.setGroups(null);
        }
        return lesson;
    }

    private FilterDTO convertToFilterDTO(LessonFilterDTO lessonFilter) {
        return isNull(lessonFilter)
                ? new FilterDTO()
                : new FilterDTO(lessonFilter.getStartDate(),
                lessonFilter.getEndDate(),
                lessonFilter.getGroupId(),
                lessonFilter.getTeacherId());
    }
}
