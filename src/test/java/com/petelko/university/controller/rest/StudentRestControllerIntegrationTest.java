package com.petelko.university.controller.rest;

import com.petelko.university.controller.ExpectedControllerStorage;
import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentRestController.class)
public class StudentRestControllerIntegrationTest {

    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();
    private TestUtils testUtils = new TestUtils();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @Test
    void should_GetAllStudentList_When_GetAll() throws Exception {
        List<StudentDTO> expectedList = expectedStorage.getExpectedStudentDTOList();
        when(studentService.getAllDTO()).thenReturn(expectedList);
        mockMvc.perform(get("/api/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(result -> is(expectedList));
    }

    @Test
    void should_CreateStudent_When_SendPOSTRequestEditWithStudentWithoutIdInBody() throws Exception {
        StudentDTO DtoForCreate = expectedStorage.getCreatedStudentDTO();
        StudentDTO expectedDto = expectedStorage.getExpectedStudentDTO();
        when(studentService.create(DtoForCreate)).thenReturn(expectedDto);
        when(studentService.getDTOByEmail(DtoForCreate.getEmail())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(post("/api/students")
                .content(testUtils.convertObjectToJsonString(DtoForCreate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> is(expectedDto));
    }

    @Test
    void should_GetStudent_When_GetById() throws Exception {
        StudentDTO expected = expectedStorage.getExpectedStudentDTO();
        when(studentService.getDTOById(1L)).thenReturn(expected);
        mockMvc.perform(get("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> is(expected));
    }


    @Test
    void should_UpdateStudent_When_SendPOSTRequestEditWithStudentWithIdInBody() throws Exception {
        StudentDTO expectedDto = expectedStorage.getExpectedStudentDTO();
        when(studentService.update(expectedDto)).thenReturn(expectedDto);
        when(studentService.getDTOByEmail(expectedDto.getEmail())).thenReturn(expectedDto);
        mockMvc.perform(put("/api/students")
                .content(testUtils.convertObjectToJsonString(expectedDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> is(expectedDto));
    }

    @Test
    void should_StudentMarkDeleted_When_Delete() throws Exception {
        doNothing().when(studentService).delete(1L);
        mockMvc.perform(get("/api/students/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void should_StudentMarkUndeleted_When_Undelete() throws Exception {
        doNothing().when(studentService).undelete(1L);
        mockMvc.perform(get("/api/students/undelete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnValidErrorListInResponse_When_CreateNonValidStudent() throws Exception {
        StudentDTO DtoForCreate = new StudentDTO();
        StudentDTO expectedDto = expectedStorage.getExpectedStudentDTO();
        when(studentService.create(DtoForCreate)).thenReturn(expectedDto);
        when(studentService.getDTOByEmail(DtoForCreate.getEmail())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(post("/api/students")
                .content(testUtils.convertObjectToJsonString(DtoForCreate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> is(expectedStorage.getValidErrorListForEmptyStudent()));
    }
}
