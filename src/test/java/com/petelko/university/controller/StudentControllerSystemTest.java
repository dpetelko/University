package com.petelko.university.controller;


import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.controller.rest.TestUtils;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.service.GroupService;
import com.petelko.university.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
public class StudentControllerSystemTest {

    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();
    private TestUtils testUtils = new TestUtils();

    @Autowired
    private StudentService studentService;
    @Autowired
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "students.yml")
    void should_GetAllStudentList_When_GetAll() throws Exception {
        List<StudentDTO> expectedList = expectedStorage.getExpectedStudentDTOList();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/getAll");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("student/students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("students", expectedList));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_GetStudent_When_GetById() throws Exception {
        StudentDTO expectedStudent = expectedStorage.getExpectedStudentDTO();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/getById/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("student/student-info"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", expectedStudent));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_StudentMarkDeleted_When_Delete() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("redirect:/students/getAll"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_StudentMarkUndeleted_When_Undelete() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/undelete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("redirect:/students/getById/?id=1"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_ShowCreateStudentDialogForm_When_SendGETRequestEdit() throws Exception {
        StudentDTO emptyStudent = new StudentDTO();
        StudentDTO createdStudent = expectedStorage.getCreatedStudentDTO();
        List<GroupDTO> groupList = expectedStorage.getExpectedGroupDTOList();
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/students/edit");
        ResultActions getResult = mockMvc.perform(getRequest);
        getResult.andExpect(view().name("student/student-edit"))
                .andExpect(model().attribute("headerString", "Add new Student"))
                .andExpect(model().attribute("student", emptyStudent))
                .andExpect(model().attribute("groupSelect", groupList));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_CreateStudent_When_SendPOSTRequestEditWithStudentWithoutIdInAttribute() throws Exception {
        StudentDTO createdStudent = expectedStorage.getCreatedStudentDTO();
         MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/students/edit")
                .accept(MediaType.TEXT_HTML)
                .flashAttr("student", createdStudent);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(view().name("redirect:/students/getAll"))
                .andExpect(flash().attribute("successMessage", "Student created successfully"));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_UpdateStudent_When_SendPOSTRequestEditWithStudentWithIdInAttribute() throws Exception {
        StudentDTO expectedStudent = expectedStorage.getExpectedStudentDTO();
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/students/edit")
                .accept(MediaType.TEXT_HTML)
                .flashAttr("student", expectedStudent);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(view().name("redirect:/students/getById/?id=1"))
                .andExpect(flash().attribute("successMessage", "Student updated successfully"));
    }

    @Test
    @DataSet(value = "students.yml")
    void should_AlarmInInfoForm_When_StudentNotFound() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/getById/?id=8");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("student/student-info"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Sorry, Student doesn't exist or has been deleted"));
    }

}
