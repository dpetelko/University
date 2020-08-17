package com.petelko.university.service.validation.annotation;

import com.petelko.university.service.validation.validator.TeacherUsedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Constraint(validatedBy = TeacherUsedValidator.class)
@Target({ TYPE, ANNOTATION_TYPE,  })
@Retention(RetentionPolicy.RUNTIME)
public @interface TeacherUsedCheck {

    String message() default "Sorry! This Teacher is used for this time!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
