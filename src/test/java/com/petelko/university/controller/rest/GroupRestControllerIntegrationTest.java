package com.petelko.university.controller.rest;

import com.petelko.university.controller.ExpectedControllerStorage;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.GroupService;
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

@WebMvcTest(GroupRestController.class)
public class GroupRestControllerIntegrationTest {
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
        mockMvc.perform(get("/api/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(result -> is(expectedList));
    }

    @Test
    void should_CreateGroup_When_SendPOSTRequestEditWithGroupWithoutIdInAttribute() throws Exception {
        GroupDTO DtoForCreate = expectedStorage.getCreatedGroupDTO();
        GroupDTO expectedDto = expectedStorage.getExpectedGroupDTO();
        when(groupService.create(DtoForCreate)).thenReturn(expectedDto);
        when(groupService.getDTOByName(DtoForCreate.getName())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(post("/api/groups")
                .content(testUtils.convertObjectToJsonString(DtoForCreate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> is(expectedDto));
    }

    @Test
    void should_GetGroup_When_GetById() throws Exception {
        GroupDTO expected = expectedStorage.getExpectedGroupDTO();
        when(groupService.getDTOById(1L)).thenReturn(expected);
        mockMvc.perform(get("/api/groups/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> is(expected));
    }

    @Test
    void should_UpdateGroup_When_SendPOSTRequestEditWithGroupWithIdInAttribute() throws Exception {
        GroupDTO expectedDto = expectedStorage.getExpectedGroupDTO();
        when(groupService.update(expectedDto)).thenReturn(expectedDto);
        when(groupService.getDTOByName(expectedDto.getName())).thenReturn(expectedDto);
        mockMvc.perform(put("/api/groups")
                .content(testUtils.convertObjectToJsonString(expectedDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> is(expectedDto));
    }

    @Test
    void should_GroupMarkDeleted_When_Delete() throws Exception {
        doNothing().when(groupService).delete(1L);
        mockMvc.perform(get("/api/groups/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void should_GroupMarkUndeleted_When_Undelete() throws Exception {
        doNothing().when(groupService).undelete(1L);
        mockMvc.perform(get("/api/groups/undelete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnValidErrorListInResponse_When_CreateNonValidGroup() throws Exception {
        GroupDTO DtoForCreate = new GroupDTO();
        GroupDTO expectedDto = expectedStorage.getExpectedGroupDTO();
        when(groupService.create(DtoForCreate)).thenReturn(expectedDto);
        when(groupService.getDTOByName(DtoForCreate.getName())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(post("/api/groups")
                .content(testUtils.convertObjectToJsonString(DtoForCreate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> is(expectedStorage.getValidErrorListForEmptyGroup()));
    }
}
