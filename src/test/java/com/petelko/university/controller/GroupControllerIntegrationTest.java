package com.petelko.university.controller;

import com.petelko.university.controller.rest.TestUtils;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GroupController.class)
public class GroupControllerIntegrationTest {

    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();
    private TestUtils testUtils = new TestUtils();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;

    @Test
    void should_GetAllGroupList_When_GetAll() throws Exception {
        List<GroupDTO> expectedList = expectedStorage.getExpectedGroupDTOList();
        when(groupService.getAllDTO()).thenReturn(expectedList);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/getAll");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("group/groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", expectedList));
    }

    @Test
    void should_GetGroup_When_GetById() throws Exception {
        GroupDTO expectedGroup = expectedStorage.getExpectedGroupDTO();
        when(groupService.getDTOById(1L)).thenReturn(expectedGroup);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/getById/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("group/group-info"))
                .andExpect(model().attributeExists("group"))
                .andExpect(model().attribute("group", expectedGroup));
    }

    @Test
    void should_GroupMarkDeleted_When_Delete() throws Exception {
        doNothing().when(groupService).delete(1L);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("redirect:/groups/getAll"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void should_GroupMarkUndeleted_When_Undelete() throws Exception {
        doNothing().when(groupService).undelete(1L);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/undelete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("redirect:/groups/getById/?id=1"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void should_ShowCreateGroupDialogForm_When_SendGETRequestEdit() throws Exception {
        GroupDTO emptyGroup = new GroupDTO();
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/groups/edit");
        ResultActions getResult = mockMvc.perform(getRequest);
        getResult.andExpect(view().name("group/group-edit"))
                .andExpect(model().attribute("headerString", "Add new Group"))
                .andExpect(model().attribute("group", emptyGroup));
    }

    @Test
    void should_CreateGroup_When_SendPOSTRequestEditWithGroupWithoutIdInAttribute() throws Exception {
        GroupDTO emptyGroup = new GroupDTO();
        GroupDTO createdGroup = expectedStorage.getCreatedGroupDTO();
        GroupDTO expectedGroup = expectedStorage.getExpectedGroupDTO();
        when(groupService.create(createdGroup)).thenReturn(expectedGroup);
        when(groupService.getDTOById(1L)).thenReturn(expectedGroup);
        when(groupService.getDTOByName(createdGroup.getName())).thenThrow(EntityNotFoundException.class);
        MockHttpServletRequestBuilder postRequest = post("/groups/edit")
                .accept(MediaType.TEXT_HTML)
                .flashAttr("group", createdGroup);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(view().name("redirect:/groups/getAll"))
                .andExpect(flash().attribute("successMessage", "Group created successfully"));
    }

    @Test
    void should_UpdateGroup_When_SendPOSTRequestEditWithGroupWithIdInAttribute() throws Exception {
        GroupDTO expectedGroup = expectedStorage.getExpectedGroupDTO();
        when(groupService.update(expectedGroup)).thenReturn(expectedGroup);
        when(groupService.getDTOById(1L)).thenReturn(expectedGroup);
        when(groupService.getDTOByName(expectedGroup.getName())).thenReturn(expectedGroup);
        MockHttpServletRequestBuilder postRequest = post("/groups/edit")
                .accept(MediaType.TEXT_HTML)
                .flashAttr("group", expectedGroup);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(view().name("redirect:/groups/getById/?id=4"))
                .andExpect(flash().attribute("successMessage", "Group updated successfully"));
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
