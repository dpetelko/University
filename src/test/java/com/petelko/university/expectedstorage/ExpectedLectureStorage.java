package com.petelko.university.expectedstorage;

import com.petelko.university.model.Lecture;
import com.petelko.university.model.dto.LectureDTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExpectedLectureStorage {

    public List<LectureDTO> getExpectedLectureDTOList() {
        List<LectureDTO> lectureList = new ArrayList<>();
        lectureList.add(new LectureDTO(1L, "I", LocalTime.of(8, 0), LocalTime.of(9, 30)));
        lectureList.add(new LectureDTO(2L, "II", LocalTime.of(10, 0), LocalTime.of(11, 30)));
        return lectureList;
    }

    public List<LectureDTO> getExpectedActiveLectureDTOList() {
        List<LectureDTO> lectureList = new ArrayList<>();
        lectureList.add(new LectureDTO(1L, "I", LocalTime.of(8, 0), LocalTime.of(9, 30)));
        return lectureList;
    }

    public LectureDTO getExpectedLectureDTO() {
        return new LectureDTO(1L, "I", LocalTime.of(8, 0), LocalTime.of(9, 30));
    }

    public Lecture getLectureForCreate() {
        return new Lecture("I", LocalTime.of(8, 0), LocalTime.of(9, 30));
    }

    public Lecture getCreatedLecture() {
        return new Lecture(1L, "I", LocalTime.of(8, 0), LocalTime.of(9, 30));

    }

    public Lecture getUpdatedLecture() {
        return new Lecture(1L, "I", LocalTime.of(8, 0), LocalTime.of(9, 20));
    }
}
