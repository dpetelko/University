package com.petelko.university.controller;

import com.petelko.university.controller.rest.dto.ValidationError;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.model.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;

public class ExpectedControllerStorage {

    public List<GroupDTO> getExpectedGroupDTOList() {
        List<GroupDTO> expectedList = new ArrayList<>();
        expectedList.add(new GroupDTO(1L, "AA-11"));
        expectedList.add(new GroupDTO(2L, "BB-22"));
        expectedList.add(new GroupDTO(3L, "CC-33"));
        return expectedList;
    }

    public GroupDTO getExpectedGroupDTO() {
        return new GroupDTO(4L, "AA-22");
    }

    public GroupDTO getGroupDTO() {
        return new GroupDTO(1L, "AA-11");
    }

    public GroupDTO getCreatedGroupDTO() {
        return new GroupDTO("AA-22");
    }

    public List<StudentDTO> getExpectedStudentDTOList() {
        List<StudentDTO> expectedList = new ArrayList<>();
        expectedList.add(new StudentDTO(1L, "Ivan", "Ivanov", "er-12@gmail.com", "+78587452114", 1L, "AA-11"));
        expectedList.add(new StudentDTO(2L, "Petr", "Petrov", "qw@.gamail.com", "+78784122114", 1L, "AA-11"));
        return expectedList;
    }

    public StudentDTO getExpectedStudentDTO() {
        return new StudentDTO(1L, "Ivan", "Ivanov", "er-12@gmail.com", "+78587452114", 1L, "AA-11");
    }

    public StudentDTO getCreatedStudentDTO() {
        return new StudentDTO("Ivan", "Ivanov", "er-12@gmail.com", "+78587452114", 1L, "AA-11");
    }

    public List<ValidationError> getValidErrorListForEmptyStudent() {
        List<ValidationError> result = new ArrayList<>();
        result.add(new ValidationError("phoneNumber", "Sorry, phone number can not be empty!"));
        result.add(new ValidationError("lastName", "Sorry, last name can not be empty!"));
        result.add(new ValidationError("firstName", "Sorry, first name can not be empty!"));
        result.add(new ValidationError("email", "Sorry, email can not be empty!"));
    return result;
    }

    public List<ValidationError> getValidErrorListForEmptyGroup() {
        List<ValidationError> result = new ArrayList<>();
        result.add(new ValidationError("name", "Sorry, Group name can't be empty!"));
        return result;
    }
}
