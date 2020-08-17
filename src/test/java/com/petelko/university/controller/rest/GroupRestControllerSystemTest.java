package com.petelko.university.controller.rest;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.controller.ExpectedControllerStorage;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.service.GroupService;
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
public class GroupRestControllerSystemTest {

    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();
    private TestUtils testUtils = new TestUtils();

    @Autowired
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "groups.yml")
    public void should_GetAllGroupList_When_GetAll() throws Exception {
            List<GroupDTO> expectedList = expectedStorage.getExpectedGroupDTOList();
            mockMvc.perform(get("/api/groups")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(result -> is(expectedList));
        }

    @Test
    @DataSet(value = "groups.yml")
    void should_CreateGroup_When_SendPOSTRequestEditWithGroupWithoutIdInAttribute() throws Exception {
        GroupDTO DtoForCreate = expectedStorage.getCreatedGroupDTO();
        GroupDTO expectedDto = expectedStorage.getExpectedGroupDTO();
        mockMvc.perform(post("/api/groups")
                .content(testUtils.convertObjectToJsonString(DtoForCreate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> is(expectedDto));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_GetGroup_When_GetById() throws Exception {
        GroupDTO expected = expectedStorage.getGroupDTO();
        mockMvc.perform(get("/api/groups/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> is(expected));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_UpdateGroup_When_SendPOSTRequestEditWithGroupWithIdInAttribute() throws Exception {
        GroupDTO expectedDto = expectedStorage.getGroupDTO();
        mockMvc.perform(put("/api/groups")
                .content(testUtils.convertObjectToJsonString(expectedDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> is(expectedDto));
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_GroupMarkDeleted_When_Delete() throws Exception {
        mockMvc.perform(get("/api/groups/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "groups.yml")
    void should_GroupMarkUndeleted_When_Undelete() throws Exception {
        mockMvc.perform(get("/api/groups/undelete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnValidErrorListInResponse_When_CreateNonValidGroup() throws Exception {
        GroupDTO DtoForCreate = new GroupDTO();
        GroupDTO expectedDto = expectedStorage.getExpectedGroupDTO();
        mockMvc.perform(post("/api/groups")
                .content(testUtils.convertObjectToJsonString(DtoForCreate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> is(expectedStorage.getValidErrorListForEmptyGroup()));
    }
}
