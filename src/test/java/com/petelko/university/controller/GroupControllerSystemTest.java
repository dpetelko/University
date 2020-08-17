package com.petelko.university.controller;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.controller.rest.TestUtils;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.service.GroupService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
public class GroupControllerSystemTest {


    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();
    private TestUtils testUtils = new TestUtils();

    @Autowired
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "groups.yml")
    void should_GetAllGroupList_When_GetAll() throws Exception {
        List<GroupDTO> expectedList = expectedStorage.getExpectedGroupDTOList();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/getAll");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("group/groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", expectedList));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_GetGroup_When_GetById() throws Exception {
        GroupDTO expectedGroup = expectedStorage.getGroupDTO();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/getById/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("group/group-info"))
                .andExpect(model().attributeExists("group"))
                .andExpect(model().attribute("group", expectedGroup));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_GroupMarkDeleted_When_Delete() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("redirect:/groups/getAll"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_GroupMarkUndeleted_When_Undelete() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/undelete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("redirect:/groups/getById/?id=1"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_ShowCreateGroupDialogForm_When_SendGETRequestEdit() throws Exception {
        GroupDTO emptyGroup = new GroupDTO();
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/groups/edit");
        ResultActions getResult = mockMvc.perform(getRequest);
        getResult.andExpect(view().name("group/group-edit"))
                .andExpect(model().attribute("headerString", "Add new Group"))
                .andExpect(model().attribute("group", emptyGroup));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_CreateGroup_When_SendPOSTRequestEditWithGroupWithoutIdInAttribute() throws Exception {
        GroupDTO createdGroup = expectedStorage.getCreatedGroupDTO();
        MockHttpServletRequestBuilder postRequest = post("/groups/edit")
                .accept(MediaType.TEXT_HTML)
                .flashAttr("group", createdGroup);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(view().name("redirect:/groups/getAll"))
                .andExpect(flash().attribute("successMessage", "Group created successfully"));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_UpdateGroup_When_SendPOSTRequestEditWithGroupWithIdInAttribute() throws Exception {
        GroupDTO expectedGroup = expectedStorage.getGroupDTO();
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/groups/edit/?id=1");
        MockHttpServletRequestBuilder postRequest = post("/groups/edit")
                .accept(MediaType.TEXT_HTML)
                .flashAttr("group", expectedGroup);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(view().name("redirect:/groups/getById/?id=1"))
                .andExpect(flash().attribute("successMessage", "Group updated successfully"));
    }


    @Test
    @DataSet(value = "groups.yml")
    void should_AlarmInInfoForm_When_GroupNotFound() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/getById/?id=8");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(view().name("group/group-info"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Sorry, Group doesn't exist or has been deleted"));
    }
}
