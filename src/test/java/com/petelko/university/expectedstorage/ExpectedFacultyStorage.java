package com.petelko.university.expectedstorage;

import com.petelko.university.model.Faculty;
import com.petelko.university.model.dto.FacultyDTO;

import java.util.ArrayList;
import java.util.List;

public class ExpectedFacultyStorage {


    public List<FacultyDTO> getExpectedFacultyDTOList() {
        List<FacultyDTO> facultyList = new ArrayList<>();
        facultyList.add(new FacultyDTO(2L, "Gamma", "FU"));
        facultyList.add(new FacultyDTO(1L, "Psy", "FU"));
        return facultyList;
    }

    public List<FacultyDTO> getExpectedActiveFacultyDTOList() {
        List<FacultyDTO> facultyList = new ArrayList<>();
        facultyList.add(new FacultyDTO(1L, "Psy", "FU"));
        return facultyList;
    }

    public FacultyDTO getExpectedFacultyDTO() {
        return new FacultyDTO(1L, "Psy", "FU");
    }

    public Faculty getFacultyForCreate() {
        return new Faculty("Psy", "FU");
    }

    public Faculty getCreatedFaculty() {
        return new Faculty(3L, "Psy", "FU");
    }

    public Faculty getUpdatedFaculty() {
        return new Faculty(1L, "Eta", "FU");
    }
}
