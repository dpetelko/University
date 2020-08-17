package com.petelko.university.service.validation.validator.uniquevalidator.impl;

import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DepartmentDTO")
public class DepartmentUniqueNameValidator implements UniqueValidator {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentUniqueNameValidator(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public boolean isUniqueField(String name, Long id) {
        return departmentService.isUniqueName(name, id);
    }
}
