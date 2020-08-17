package com.petelko.university.controller.rest;

import com.petelko.university.controller.ExpectedControllerStorage;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.service.GroupService;
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
class GroupRestControllerTest {

    private ExpectedControllerStorage expectedStorage = new ExpectedControllerStorage();
    private TestUtils testUtils = new TestUtils();
    private MockMvc mockMvc;
    @Mock
    private GroupService groupService;
    @Mock
    private Validator mockValidator;
    @InjectMocks
    private GroupRestController groupController;

    @BeforeAll
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).setValidator(mockValidator).build();
    }

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
}
