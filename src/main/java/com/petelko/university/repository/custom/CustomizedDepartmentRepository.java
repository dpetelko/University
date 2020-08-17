package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.DepartmentDTO;

public interface CustomizedDepartmentRepository extends CustomizedRepository<DepartmentDTO> {
    DepartmentDTO findDTOByName(String name);
}
