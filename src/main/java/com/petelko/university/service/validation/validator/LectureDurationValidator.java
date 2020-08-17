package com.petelko.university.service.validation.validator;

import com.petelko.university.model.dto.LectureDTO;
import com.petelko.university.service.validation.annotation.LectureDurationConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.LocalTime;

public class LectureDurationValidator implements ConstraintValidator<LectureDurationConstraint, LectureDTO> {

    private static final String END_TIME = "endTime";
    private static final Logger LOGGER = LoggerFactory.getLogger(LectureDurationValidator.class);

    private String message;

    @Value("${university.validation.lectureDurationInMinutes:90}")
    private long lectureDuration;

    @Override
    public void initialize(LectureDurationConstraint constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(LectureDTO lecture, ConstraintValidatorContext context) {
        boolean result = validateDuration(lecture);
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(END_TIME)
                    .addConstraintViolation();
        }
        return result;
    }

    private boolean validateDuration(LectureDTO lecture) {
        LocalTime startTime = lecture.getStartTime();
        LocalTime endTime = lecture.getEndTime();
        Duration duration = Duration.between(startTime, endTime);
        Duration setDuration = Duration.ofMinutes(lectureDuration);
        return setDuration.equals(duration);
    }

}
