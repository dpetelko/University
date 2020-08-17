package com.petelko.university.service.validation.validator;

import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.service.LessonService;
import com.petelko.university.service.validation.annotation.TeacherUsedCheck;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class TeacherUsedValidator implements ConstraintValidator<TeacherUsedCheck, LessonDTO> {

    public static final String TEACHER_ID = "teacherId";
    private String message;

    @Autowired
    private LessonService lessonService;

    @Override
    public void initialize(TeacherUsedCheck constraint) {
        message = constraint.message();
    }

    @Override
    public boolean isValid(LessonDTO lesson, ConstraintValidatorContext context) {
        boolean result = lectureValidate(lesson);
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(TEACHER_ID)
                    .addConstraintViolation();
        }
        return result;
    }

    private boolean lectureValidate(LessonDTO lesson) {
        LocalDate date = lesson.getDate();
        Long teacherId = lesson.getTeacherId();
        Long lessonId = lesson.getId();
        Long lectureId = lesson.getLectureId();
        return lessonService.isTeacherFree(teacherId, lectureId, lessonId, date);
    }
}
