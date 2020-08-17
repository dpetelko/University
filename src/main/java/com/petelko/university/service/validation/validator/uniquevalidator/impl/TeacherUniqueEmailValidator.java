package com.petelko.university.service.validation.validator.uniquevalidator.impl;

import com.petelko.university.service.TeacherService;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("TeacherDTO")
public class TeacherUniqueEmailValidator implements UniqueValidator {

    private TeacherService teacherService;

    @Autowired
    public TeacherUniqueEmailValidator(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public boolean isUniqueField(String email, Long id) {
        return teacherService.isUniqueEmail(email, id);
    }
}
