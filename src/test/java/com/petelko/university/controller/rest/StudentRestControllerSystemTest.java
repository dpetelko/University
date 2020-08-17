package com.petelko.university.controller.rest;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.controller.ExpectedControllerStorage;
import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
public class StudentRestControllerSystemTest {

    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();
    private TestUtils testUtils = new TestUtils();

    @Autowired
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "students.yml")
    void should_GetAllStudentList_When_GetAll() throws Exception {
        List<StudentDTO> expectedList = expectedStorage.getExpectedStudentDTOList();
        mockMvc.perform(get("/api/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(result -> is(expectedList));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_CreateStudent_When_SendPOSTRequestEditWithStudentWithoutIdInBody() throws Exception {
        StudentDTO DtoForCreate = expectedStorage.getCreatedStudentDTO();
        StudentDTO expectedDto = expectedStorage.getExpectedStudentDTO();
                mockMvc.perform(post("/api/students")
                .content(testUtils.convertObjectToJsonString(DtoForCreate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> is(expectedDto));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_GetStudent_When_GetById() throws Exception {
        StudentDTO expected = expectedStorage.getExpectedStudentDTO();
        mockMvc.perform(get("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> is(expected));
    }


    @Test
    @DataSet(value = "students.yml")
    void should_UpdateStudent_When_SendPOSTRequestEditWithStudentWithIdInBody() throws Exception {
        StudentDTO expectedDto = expectedStorage.getExpectedStudentDTO();
        mockMvc.perform(put("/api/students")
                .content(testUtils.convertObjectToJsonString(expectedDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> is(expectedDto));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_StudentMarkDeleted_When_Delete() throws Exception {
        mockMvc.perform(get("/api/students/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "students.yml")
    void should_StudentMarkUndeleted_When_Undelete() throws Exception {
        mockMvc.perform(get("/api/students/undelete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnValidErrorListInResponse_When_CreateNonValidStudent() throws Exception {
        StudentDTO DtoForCreate = new StudentDTO();
        StudentDTO expectedDto = expectedStorage.getExpectedStudentDTO();
        mockMvc.perform(post("/api/students")
                .content(testUtils.convertObjectToJsonString(DtoForCreate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> is(expectedStorage.getValidErrorListForEmptyStudent()));
    }

}
