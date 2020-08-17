package com.petelko.university.service;

import com.petelko.university.model.Department;
import com.petelko.university.model.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService extends  Service<Department> {
    List<DepartmentDTO> getAllDTO();
    List<DepartmentDTO> getAllActiveDTO();
    DepartmentDTO getDTOById(Long id);
    DepartmentDTO create(DepartmentDTO departmentDto);
    DepartmentDTO update(DepartmentDTO departmentDto);
    boolean existsDepartmentById(Long id);
    DepartmentDTO getDTOByName(String name);
    boolean isUniqueName(String name, Long id);
}
