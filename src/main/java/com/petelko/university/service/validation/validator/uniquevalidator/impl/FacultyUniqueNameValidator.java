package com.petelko.university.service.validation.validator.uniquevalidator.impl;

import com.petelko.university.service.FacultyService;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("FacultyDTO")
public class FacultyUniqueNameValidator implements UniqueValidator {

    private FacultyService facultyService;

    @Autowired
    public FacultyUniqueNameValidator(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public boolean isUniqueField(String name, Long id) {
        return facultyService.isUniqueName(name, id);
    }
}
