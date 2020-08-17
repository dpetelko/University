package com.petelko.university.service.validation.validator.uniquevalidator.impl;

import com.petelko.university.service.SubjectService;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("SubjectDTO")
public class SubjectUniqueNameValidator implements UniqueValidator {

    private SubjectService subjectService;

    @Autowired
    public SubjectUniqueNameValidator(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public boolean isUniqueField(String name, Long id) {
        return subjectService.isUniqueName(name, id);
    }
}
