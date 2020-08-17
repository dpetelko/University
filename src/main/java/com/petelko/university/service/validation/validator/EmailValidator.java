package com.petelko.university.service.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.StudentService;
import com.petelko.university.service.validation.annotation.EmailUniqueConstraint;

public class EmailValidator implements ConstraintValidator<EmailUniqueConstraint, StudentDTO> {

    private static final String EMAIL = "email";
    
    private String message;
    
    @Autowired
    private StudentService studentService;
    
    @Override
    public void initialize(EmailUniqueConstraint constraint) {
        message = constraint.message();
    }

    @Override
    public boolean isValid(StudentDTO validatedStudent, ConstraintValidatorContext context) {
        boolean result = validateByEmail(validatedStudent);
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(EMAIL)
                    .addConstraintViolation();
        }
        return result;
    }
    
    private boolean validateByEmail(StudentDTO validatedStudent) {
        String email = validatedStudent.getEmail();
        boolean result;
        try {
            StudentDTO existedStudent = studentService.getDTOByEmail(email);
            result = Objects.equals(validatedStudent.getId(), existedStudent.getId());
        } catch (EntityNotFoundException e) {
            result = true;
        }
        return result;
    }
}
