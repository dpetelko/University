package com.petelko.university.service.validation.validator;

import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.service.AuditoryService;
import com.petelko.university.service.validation.annotation.AuditoryOverflowCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AuditoryOverflowValidator implements ConstraintValidator<AuditoryOverflowCheck, LessonDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditoryOverflowValidator.class);

    public static final String AUDITORY_ID = "auditoryId";
    private String message;
    @Autowired
    private AuditoryService auditoryService;

    @Value("${university.validation.maxStudentCountInGroup:10}")
    private int limit;

    @Override
    public void initialize(AuditoryOverflowCheck constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(LessonDTO value, ConstraintValidatorContext context) {
        int maxStudentCount = value.getGroups().size() * limit;
        int auditoryCapacity = auditoryService.getDTOById(value.getAuditoryId()).getCapacity();
        boolean result = maxStudentCount <= auditoryCapacity;
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(AUDITORY_ID)
                    .addConstraintViolation();
        }
        return result;
    }
}
