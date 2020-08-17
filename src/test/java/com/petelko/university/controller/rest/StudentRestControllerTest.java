package com.petelko.university.controller.rest;

import com.petelko.university.controller.ExpectedControllerStorage;
import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

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

@TestInstance(Lifecycle.PER_CLASS)
class StudentRestControllerTest {

    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();
    private TestUtils testUtils = new TestUtils();
    private MockMvc mockMvc;
    @Mock
    private StudentService studentService;
    @Mock
    private Validator mockValidator;
    @InjectMocks
    private StudentRestController studentController;

    @BeforeAll
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).setValidator(mockValidator).build();
    }

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
}
