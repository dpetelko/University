package com.petelko.university.controller;

import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.GroupService;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestInstance(Lifecycle.PER_CLASS)
class StudentControllerTest {

    public static final String LINE_MASK = "Oops ... Something went wrong%s!";

    private MockMvc mockMvc;
    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();

    @Mock
    private StudentService studentService;
    @Mock
    private GroupService groupService;
    @Mock
    private Validator mockValidator;
    @InjectMocks
    private StudentController studentController;

    @BeforeAll
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).setValidator(mockValidator).build();
    }

    @Test
    void should_GetAllStudentList_When_GetAll() throws Exception {
        List<StudentDTO> expectedList = expectedStorage.getExpectedStudentDTOList();
        when(studentService.getAllDTO()).thenReturn(expectedList);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/getAll");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("student/students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("students", expectedList));
    }

    @Test
    void should_GetStudent_When_GetById() throws Exception {
        StudentDTO expectedStudent = expectedStorage.getExpectedStudentDTO();
        when(studentService.getDTOById(1L)).thenReturn(expectedStudent);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/getById/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("student/student-info"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", expectedStudent));
    }

    @Test
    void should_StudentMarkDeleted_When_Delete() throws Exception {
        doNothing().when(studentService).delete(1L);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("redirect:/students/getAll"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void should_StudentMarkUndeleted_When_Undelete() throws Exception {
        doNothing().when(studentService).undelete(1L);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/undelete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("redirect:/students/getById/?id=1"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void should_ShowCreateStudentDialogForm_When_SendGETRequestEdit() throws Exception {
        StudentDTO emptyStudent = new StudentDTO();
        List<GroupDTO> groupList = expectedStorage.getExpectedGroupDTOList();
        when(groupService.getAllActiveDTO()).thenReturn(groupList);
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/students/edit");
        ResultActions getResult = mockMvc.perform(getRequest);
        getResult.andExpect(view().name("student/student-edit"))
                .andExpect(model().attribute("headerString", "Add new Student"))
                .andExpect(model().attribute("student", emptyStudent))
                .andExpect(model().attribute("groupSelect", groupList));
    }

    @Test
    void should_CreateStudent_When_SendPOSTRequestEditWithStudentWithoutIdInAttribute() throws Exception {
        StudentDTO createdStudent = expectedStorage.getCreatedStudentDTO();
        StudentDTO expectedStudent = expectedStorage.getExpectedStudentDTO();
        when(studentService.create(createdStudent)).thenReturn(expectedStudent);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/students/edit")
                .accept(MediaType.TEXT_HTML)
                .flashAttr("group", createdStudent);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(view().name("redirect:/students/getAll"))
                .andExpect(flash().attribute("successMessage", "Student created successfully"));
    }

    @Test
    void should_UpdateStudent_When_SendPOSTRequestEditWithStudentWithIdInAttribute() throws Exception {
        StudentDTO expectedStudent = expectedStorage.getExpectedStudentDTO();
        when(studentService.update(expectedStudent)).thenReturn(expectedStudent);
        when(studentService.getDTOById(1L)).thenReturn(expectedStudent);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/students/edit")
                .accept(MediaType.TEXT_HTML)
                .flashAttr("group", expectedStudent);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(view().name("redirect:/students/getAll"))
                .andExpect(flash().attribute("successMessage", "Student created successfully"));
    }

    @Test
    void should_AlarmInInfoForm_When_StudentNotFound() throws Exception {
        when(studentService.getDTOById(2L)).thenThrow(EntityNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/getById/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("student/student-info"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Sorry, Student doesn't exist or has been deleted"));
    }
}
