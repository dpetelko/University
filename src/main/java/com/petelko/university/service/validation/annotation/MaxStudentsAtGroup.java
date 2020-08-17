package com.petelko.university.service.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.petelko.university.service.validation.validator.MaxStudentsAtGroupValidator;

@Constraint(validatedBy = MaxStudentsAtGroupValidator.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxStudentsAtGroup {
    
    String message() default "Sorry! Student limit exceeded!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
