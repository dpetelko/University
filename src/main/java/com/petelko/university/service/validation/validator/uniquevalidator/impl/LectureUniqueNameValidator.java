package com.petelko.university.service.validation.validator.uniquevalidator.impl;

import com.petelko.university.service.LectureService;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("LectureDTO")
public class LectureUniqueNameValidator implements UniqueValidator {

    private LectureService lectureService;

    @Autowired
    public LectureUniqueNameValidator(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @Override
    public boolean isUniqueField(String name, Long id) {
        return lectureService.isUniqueName(name, id);
    }
}
