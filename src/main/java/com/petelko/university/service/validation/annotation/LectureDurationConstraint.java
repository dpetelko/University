package com.petelko.university.service.validation.annotation;

import com.petelko.university.service.validation.validator.LectureDurationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = LectureDurationValidator.class)
public @interface LectureDurationConstraint {
    String message() default "{lecture.invalidduration}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
