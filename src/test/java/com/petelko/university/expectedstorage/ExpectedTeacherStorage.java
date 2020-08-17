package com.petelko.university.expectedstorage;

import com.petelko.university.model.Department;
import com.petelko.university.model.Subject;
import com.petelko.university.model.Teacher;
import com.petelko.university.model.dto.TeacherDTO;

import java.util.ArrayList;
import java.util.List;

public class ExpectedTeacherStorage {
    public List<TeacherDTO> getExpectedTeacherDTOList() {
        List<TeacherDTO> teacherList = new ArrayList<>();
        teacherList.add(new TeacherDTO(1L,
                "Ivan",
                "Ivanov",
                "teacher1@11.ru",
                "+78989632121",
                1L,
                "Geography",
                "Seeks an understanding of Earth and its human and natural complexities",
                1L,
                "Humanitarian Department",
                "FU"));
        teacherList.add(new TeacherDTO(2L,
                "Petr",
                "Petrov",
                "teacher2@22.ru",
                "+78989632121",
                2L,
                "Biology",
                "Studies life and living organisms",
                1L,
                "Humanitarian Department",
                "FU"));
        teacherList.add(new TeacherDTO(3L,
                "Sidor",
                "Sidorov",
                "teacher3@33.ru",
                "+78989632121",
                3L,
                "Chemistry",
                "Studies elements and compounds",
                1L,
                "Humanitarian Department",
                "FU"));
        return teacherList;
    }

    public List<TeacherDTO> getExpectedActiveTeacherDTOList() {
        List<TeacherDTO> teacherList = new ArrayList<>();
        teacherList.add(new TeacherDTO(1L,
                "Ivan",
                "Ivanov",
                "teacher1@11.ru",
                "+78989632121",
                1L,
                "Geography",
                "Seeks an understanding of Earth and its human and natural complexities",
                1L,
                "Humanitarian Department",
                "FU"));
        teacherList.add(new TeacherDTO(2L,
                "Petr",
                "Petrov",
                "teacher2@22.ru",
                "+78989632121",
                2L,
                "Biology",
                "Studies life and living organisms",
                1L,
                "Humanitarian Department",
                "FU"));


        return teacherList;
    }

    public TeacherDTO getExpectedTeacherDTO() {
        return new TeacherDTO(1L,
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
    }

    public Teacher getTeacherForCreate() {
        return new Teacher("Dmitry",
                "Petelko",
                "teacher1@11.ru",
                "+78989632121",
                new Subject(1L,
                        "Geography",
                        "Seeks an understanding of Earth and its human and natural complexities",
                        new Department(1L, "Humanitarian Department")),
                new Department(1L, "Humanitarian Department"));
    }

    public Teacher getCreatedTeacher() {
        return new Teacher(1L,
                "Dmitry",
                "Petelko",
                "teacher1@11.ru",
                "+78989632121",
                new Subject(1L, "Geography",
                        "Seeks an understanding of Earth and its human and natural complexities",
                        new Department(1L, "Humanitarian Department")),
                new Department(1L, "Humanitarian Department"));
    }

    public Teacher getUpdatedTeacher() {
        return new Teacher(1L,
                "Dmitry",
                "Petelko",
                "77@77.ru",
                "+78989632121",
                new Subject());
    }
}
