package com.petelko.university.service.validation.annotation;


import com.petelko.university.service.validation.validator.AuditoryOverflowValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AuditoryOverflowValidator.class)
public @interface AuditoryOverflowCheck {
    String message() default "{auditory.capacity.overflow}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
