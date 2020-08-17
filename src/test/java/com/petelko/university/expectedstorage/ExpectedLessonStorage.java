package com.petelko.university.expectedstorage;

import com.petelko.university.model.Auditory;
import com.petelko.university.model.Department;
import com.petelko.university.model.Group;
import com.petelko.university.model.Lecture;
import com.petelko.university.model.Lesson;
import com.petelko.university.model.Subject;
import com.petelko.university.model.Teacher;
import com.petelko.university.model.dto.AuditoryDTO;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.model.dto.LectureDTO;
import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.model.dto.SubjectDTO;
import com.petelko.university.model.dto.TeacherDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class ExpectedLessonStorage {
    public LessonDTO getExpectedLessonDTOid1() {
        Set<GroupDTO> groups = new HashSet<>();
        groups.add(new GroupDTO(1L, "AA-11"));
        groups.add(new GroupDTO(2L, "BB-22"));
        AuditoryDTO auditory = new AuditoryDTO(1L, "AAA", "Large auditory", 200);
        LectureDTO lecture = new LectureDTO(1L, "I", LocalTime.of(8, 0), LocalTime.of(9, 30));
        SubjectDTO subject = new SubjectDTO(1L,
                "Geography",
                "Seeks an understanding of Earth and its human and natural complexities",
                1L,
                "Humanitarian Department");
        TeacherDTO teacher = new TeacherDTO(1L,
                "Ivan",
                "Ivanov",
                "teacher1@11.ru",
                "+78989632121",
                1L,
                "Geography",
                "Seeks an understanding of Earth and its human and natural complexities",
                1L,
                "Humanitarian Department",
                "FU");
        return new LessonDTO(1L,
                LocalDate.of(2020, 12, 14),
                groups,
                subject.getId(),
                subject.getName(),
                subject.getDescription(),
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                auditory.getId(),
                auditory.getName(),
                auditory.getDescription(),
                auditory.getCapacity(),
                lecture.getId(),
                lecture.getName(),
                lecture.getStartTime(),
                lecture.getEndTime(),
                false);
    }

    public LessonDTO getExpectedLessonDTOid2() {
        Set<GroupDTO> groups = new HashSet<>();
        groups.add(new GroupDTO(3L, "CC-33"));
        AuditoryDTO auditory = new AuditoryDTO(2L, "BBB", "Small auditory", 50);
        LectureDTO lecture = new LectureDTO(2L, "II", LocalTime.of(10, 0), LocalTime.of(11, 30));
        SubjectDTO subject = new SubjectDTO(2L,
                "Biology",
                "Studies life and living organisms",
                1L,
                "Humanitarian Department");
        TeacherDTO teacher = new TeacherDTO(2L,
                "Petr",
                "Petrov",
                "teacher2@22.ru",
                "+78989632121",
                2L,
                "Biology",
                "Studies life and living organisms",
                1L,
                "Humanitarian Department",
                "FU");
        return new LessonDTO(2L,
                LocalDate.of(2020, 12, 14),
                groups,
                subject.getId(),
                subject.getName(),
                subject.getDescription(),
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                auditory.getId(),
                auditory.getName(),
                auditory.getDescription(),
                auditory.getCapacity(),
                lecture.getId(),
                lecture.getName(),
                lecture.getStartTime(),
                lecture.getEndTime(),
                false);
    }

    public Lesson getLessonForCreate() {
        Set<Group> groups = new HashSet<>();
        groups.add(new Group(2L, "BB-22"));
        Auditory auditory = new Auditory(2L, "BBB", "Small auditory", 50);
        Lecture lecture = new Lecture(2L, "II", LocalTime.of(10, 00), LocalTime.of(11, 30));
        Subject subject = new Subject(2L,
                "Biology",
                "Studies life and living organisms",
                new Department(1L,
                "Humanitarian Department"));
        Teacher teacher = new Teacher(2L,
                "Petr",
                "Petrov",
                "teacher2@22.ru",
                "+78989632121",
                new Subject(
                2L,
                "Biology",
                "Studies life and living organisms",
                new Department(
                1L,
                "Humanitarian Department",
                "FU")));
        return new Lesson(subject,
                teacher,
                groups,
                LocalDate.of(2020, 12, 14),
                auditory,
                lecture,
                false);
    }

    public Lesson getCreatedLesson() {
        Lesson lesson = getLessonForCreate();
        lesson.setId(1L);
        return lesson;
    }

    public Lesson getUpdatedLesson() {
        Lesson lesson = getLessonForCreate();
        lesson.setId(1L);
        lesson.setDate(LocalDate.of(2024, 10, 10));
        return lesson;
    }

}
