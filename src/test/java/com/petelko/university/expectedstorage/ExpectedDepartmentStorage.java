package com.petelko.university.expectedstorage;

import com.petelko.university.model.Department;
import com.petelko.university.model.Faculty;
import com.petelko.university.model.dto.DepartmentDTO;

import java.util.ArrayList;
import java.util.List;

public class ExpectedDepartmentStorage {
    public List<DepartmentDTO> getExpectedDepartmentDTOList() {
        List<DepartmentDTO> departmentList = new ArrayList<>();
        departmentList.add(new DepartmentDTO(1L,
                "Humanitarian Department",
                "FU",
                1L,
                "Psy",
                "FU"));
        departmentList.add(new DepartmentDTO(2L,
                "Technical Department",
                "FU",
                1L,
                "Psy",
                "FU"));
        return departmentList;
    }

    public List<DepartmentDTO> getExpectedActiveDepartmentDTOList() {
        List<DepartmentDTO> departmentList = new ArrayList<>();
        departmentList.add(new DepartmentDTO(1L,
                "Humanitarian Department",
                "FU",
                1L,
                "Psy",
                "FU"));
        return departmentList;
    }

    public DepartmentDTO getExpectedDepartmentDTO() {
        return new DepartmentDTO(1L,
                "Humanitarian Department",
                "FU",
                1L,
                "Psy",
                "FU");
    }

    public Department getDepartmentForCreate() {
        return new Department("Military Department", "FU",
                new Faculty(1L, "Psy", "FU"));
    }

    public Department getCreatedDepartment() {
        return new Department(1L, "Military Department", "FU",
                new Faculty(1L, "Psy", "FU"));
    }

    public Department getUpdatedDepartment() {
        return new Department(2L, "Military Department", "FU",
                new Faculty(1L, "Psy", "FU"));
    }
}
