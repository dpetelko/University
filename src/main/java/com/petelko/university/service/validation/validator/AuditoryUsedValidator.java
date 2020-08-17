package com.petelko.university.service.validation.validator;

import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.service.LessonService;
import com.petelko.university.service.validation.annotation.AuditoryUsedCheck;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AuditoryUsedValidator implements ConstraintValidator<AuditoryUsedCheck, LessonDTO> {

    public static final String AUDITORY_ID = "auditoryId";
    @Autowired
    private LessonService lessonService;
    private String message;

    @Override
    public void initialize(AuditoryUsedCheck constraint) {
        message = constraint.message();
    }

    @Override
    public boolean isValid(LessonDTO lesson, ConstraintValidatorContext context) {
        boolean result = lectureValidate(lesson);
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(AUDITORY_ID)
                    .addConstraintViolation();
        }
        return result;
    }

    private boolean lectureValidate(LessonDTO lesson) {
        LocalDate date = lesson.getDate();
        Long lectureId = lesson.getLectureId();
        Long lessonId = lesson.getId();
        Long auditoryId = lesson.getAuditoryId();
        return lessonService.isAuditoryFree(auditoryId, lectureId, lessonId, date);
    }
}
