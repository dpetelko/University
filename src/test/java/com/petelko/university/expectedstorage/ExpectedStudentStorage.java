package com.petelko.university.expectedstorage;

import com.petelko.university.model.Group;
import com.petelko.university.model.Student;
import com.petelko.university.model.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpectedStudentStorage {
    public Optional<Student> getExpectedStudent() {
        return Optional.of(new Student(1L, "Ivan", "Ivanov", "11@11.ru", "+78989632121", new Group(1L, "AA-11")));
    }

    public StudentDTO getExpectedStudentDTO() {
        return new StudentDTO(1L, "Ivan", "Ivanov", "11@11.ru", "+78989632121", 1L, "AA-11");
    }

    public List<Student> getExpectedStudentsList() {
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(new Student(1L, "Ivan", "Ivanov", "11@11.ru", "+78989632121", new Group(1L, "AA-11")));
        studentsList.add(new Student(2L, "Petr", "Petrov", "22@22.ru", "+78989632121", new Group(1L, "AA-11")));
        return studentsList;
    }

    public List<StudentDTO> getExpectedStudentDTOList() {
        List<StudentDTO> studentsList = new ArrayList<>();
        studentsList.add(new StudentDTO(1L, "Ivan", "Ivanov", "11@11.ru", "+78989632121", 1L, "AA-11"));
        studentsList.add(new StudentDTO(2L, "Petr", "Petrov", "22@22.ru", "+78989632121", 1L, "AA-11"));
        return studentsList;
    }

    public List<Student> getExpectedActiveStudentsList() {
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(new Student(1L, "Ivan", "Ivanov", "11@11.ru", "+78989632121", new Group(1L, "AA-11")));
        return studentsList;
    }

    public List<StudentDTO> getExpectedActiveStudentDTOList() {
        List<StudentDTO> studentsList = new ArrayList<>();
        studentsList.add(new StudentDTO(1L, "Ivan", "Ivanov", "11@11.ru", "+78989632121", 1L, "AA-11"));
        return studentsList;
    }

    public Student getCreatedStudent() {
        return new Student(1L, "Dmitry", "Petelko", "77@77.ru", "+78989632121", new Group(1L, "AA-11"));
    }

    public Student getCreatedStudent2() {
        return new Student("Dmitry", "Petelko", "77@77.ru", "+78989632121", new Group(1L, "AA-11"));
    }

    public Student getUpdatedStudent() {
        return new Student(1L, "Ivan", "Ivanov", "11@11.ru", "+78989632121", new Group(2L, "BB-22"));
    }

    public Student getStudentForDelete() {
        return new Student(3L, "Sidor", "Sidorov", "33@33.ru", "+78989632121", new Group(2L, "BB-22"));
    }
}
