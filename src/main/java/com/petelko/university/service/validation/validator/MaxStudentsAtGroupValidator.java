package com.petelko.university.service.validation.validator;

import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.service.StudentService;
import com.petelko.university.service.validation.annotation.MaxStudentsAtGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxStudentsAtGroupValidator implements ConstraintValidator<MaxStudentsAtGroup, StudentDTO> {

    private static final String GROUP_NAME = "groupName";

    @Autowired
    private StudentService studentService;

    @Value("${university.validation.maxStudentCountInGroup:10}")
    private int limit;
    
    private String message;
    
    @Override
    public void initialize(MaxStudentsAtGroup constraint) {
        message = constraint.message();
    }

    @Override
    public boolean isValid(StudentDTO student, ConstraintValidatorContext context) {
        boolean result = true;
        if (student.getGroupId() != null) {
            result =  validate(student);
        }
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(GROUP_NAME)
                    .addConstraintViolation();
        }
        return result;
    }
    
    private boolean validate(StudentDTO student) {
        Long limitedGroupId = student.getGroupId();
        Long studentId = student.getId();
        int countStudentsInGroup = studentService.countByGroupId(limitedGroupId);
        boolean result = true;
        if (countStudentsInGroup >= limit) {
            boolean isStudentAtGroup = studentService.existsStudentByIdAndGroupId(studentId, limitedGroupId);
            if (!isStudentAtGroup) {
                result = false;
            }
        }
        return result;
    }
}
