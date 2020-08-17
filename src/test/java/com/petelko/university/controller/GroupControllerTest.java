package com.petelko.university.controller;

import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.GroupService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestInstance(Lifecycle.PER_CLASS)
class GroupControllerTest {

    private MockMvc mockMvc;
    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();

    @Mock
    private GroupService groupService;
    @Mock
    private Validator mockValidator;
    @InjectMocks
    private GroupController groupController;

    @BeforeAll
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).setValidator(mockValidator).build();
    }

    @Test
    void should_GetAllGroupList_When_GetAll() throws Exception {
        List<GroupDTO> expectedList = expectedStorage.getExpectedGroupDTOList();
        when(groupService.getAllDTO()).thenReturn(expectedList);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/getAll");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("group/groups"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
        .andExpect(MockMvcResultMatchers.model().attribute("groups", expectedList));
    }

    @Test
    void should_GetGroup_When_GetById() throws Exception {
        GroupDTO expectedGroup = expectedStorage.getExpectedGroupDTO();
        when(groupService.getDTOById(1L)).thenReturn(expectedGroup);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/getById/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("group/group-info"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
        .andExpect(MockMvcResultMatchers.model().attribute("group", expectedGroup));
    }

    @Test
    void should_GroupMarkDeleted_When_Delete() throws Exception {
        doNothing().when(groupService).delete(1L);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/groups/getAll"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("successMessage"));
    }

    @Test
    void should_GroupMarkUndeleted_When_Undelete() throws Exception {
        doNothing().when(groupService).undelete(1L);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/undelete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/groups/getById/?id=1"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("successMessage"));
    }

    @Test
    void should_ShowCreateGroupDialogForm_When_SendGETRequestEdit() throws Exception {
        GroupDTO emptyGroup = new GroupDTO();
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/groups/edit");
        ResultActions getResult = mockMvc.perform(getRequest);
        getResult.andExpect(MockMvcResultMatchers.view().name("group/group-edit"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add new Group"))
                .andExpect(MockMvcResultMatchers.model().attribute("group", emptyGroup));
        }

    @Test
    void should_CreateGroup_When_SendPOSTRequestEditWithGroupWithoutIdInAttribute() throws Exception {
        GroupDTO createdGroup = expectedStorage.getCreatedGroupDTO();
        GroupDTO expectedGroup = expectedStorage.getExpectedGroupDTO();
        when(groupService.create(createdGroup)).thenReturn(expectedGroup);
        when(groupService.getDTOById(1L)).thenReturn(expectedGroup);
         MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/groups/edit");
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(MockMvcResultMatchers.view().name("redirect:/groups/getAll"))
                .andExpect(MockMvcResultMatchers.flash().attribute("successMessage", "Group created successfully"));
    }

    @Test
    void should_UpdateGroup_When_SendPOSTRequestEditWithGroupWithIdInAttribute() throws Exception {
        GroupDTO expectedGroup = expectedStorage.getExpectedGroupDTO();
        when(groupService.update(expectedGroup)).thenReturn(expectedGroup);
        when(groupService.getDTOById(1L)).thenReturn(expectedGroup);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/groups/edit");
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(MockMvcResultMatchers.view().name("redirect:/groups/getAll"))
                .andExpect(MockMvcResultMatchers.flash().attribute("successMessage", "Group created successfully"));
    }


    @Test
    void should_AlarmInInfoForm_When_GroupNotFound() throws Exception {
        when(groupService.getDTOById(2L)).thenThrow(EntityNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/getById/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("group/group-info"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Sorry, Group doesn't exist or has been deleted"));
    }
}
