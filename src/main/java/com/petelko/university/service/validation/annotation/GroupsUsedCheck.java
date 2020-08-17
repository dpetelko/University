package com.petelko.university.service.validation.annotation;

import com.petelko.university.service.validation.validator.GroupsUsedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Constraint(validatedBy = GroupsUsedValidator.class)
@Target({ TYPE, ANNOTATION_TYPE,  })
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupsUsedCheck {

    String message() default "Sorry! Some of these Groups is used for this time!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
