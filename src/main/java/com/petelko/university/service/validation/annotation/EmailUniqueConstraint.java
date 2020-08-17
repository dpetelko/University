package com.petelko.university.service.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.petelko.university.service.validation.validator.EmailValidator;

@Constraint(validatedBy = EmailValidator.class)
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUniqueConstraint {
    String message() default "Sorry! Email is already used!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
