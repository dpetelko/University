package com.petelko.university.service.validation.validator.uniquevalidator.impl;

import com.petelko.university.service.StudentService;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("StudentDTO")
public class StudentUniqueEmailValidator implements UniqueValidator {

    private StudentService studentService;

    @Autowired
    public StudentUniqueEmailValidator(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public boolean isUniqueField(String email, Long id) {
        return studentService.isUniqueEmail(email, id);
    }
}
