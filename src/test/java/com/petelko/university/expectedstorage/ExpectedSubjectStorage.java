package com.petelko.university.expectedstorage;

import com.petelko.university.model.Department;
import com.petelko.university.model.Subject;
import com.petelko.university.model.dto.SubjectDTO;

import java.util.ArrayList;
import java.util.List;

public class ExpectedSubjectStorage {
    public List<SubjectDTO> getExpectedSubjectDTOList() {
        List<SubjectDTO> subjectList = new ArrayList<>();
        subjectList.add(new SubjectDTO(2L,
                "Biology",
                "Studies life and living organisms",
                1L,
                "Humanitarian Department"));
        subjectList.add(new SubjectDTO(3L,
                "Chemistry",
                "Studies elements and compounds",
                1L,
                "Humanitarian Department"));
        subjectList.add(new SubjectDTO(1L,
                "Geography",
                "Seeks an understanding of Earth and its human and natural complexities",
                1L,
                "Humanitarian Department"));
        return subjectList;
    }

    public List<SubjectDTO> getExpectedActiveSubjectDTOList() {
        List<SubjectDTO> subjectList = new ArrayList<>();
        subjectList.add(new SubjectDTO(2L,
                "Biology",
                "Studies life and living organisms",
                1L,
                "Humanitarian Department"));
        subjectList.add(new SubjectDTO(1L,
                "Geography",
                "Seeks an understanding of Earth and its human and natural complexities",
                1L,
                "Humanitarian Department"));
        return subjectList;
    }

    public SubjectDTO getExpectedSubjectDTO() {
        return new SubjectDTO(2L,
                "Biology",
                "Studies life and living organisms",
                1L,
                "Humanitarian Department");
    }

    public Subject getSubjectForCreate() {
        return new Subject("Geography",
                "Seeks an understanding of Earth and its human and natural complexities",
                new Department(1L, "Humanitarian Department"));
    }

    public Subject getCreatedSubject() {
        return new Subject(1L,"Geography",
                "Seeks an understanding of Earth and its human and natural complexities",
                new Department(1L, "Humanitarian Department"));
    }

    public Subject getUpdatedSubject() {
        return new Subject(1L,
                "Law",
                "Studies the set of rules and principles by which a society is governed",
                new Department(1L, "Humanitarian Department"));
    }
}
