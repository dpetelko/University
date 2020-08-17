package com.petelko.university.service.impl;

import com.petelko.university.model.Group;
import com.petelko.university.repository.GroupRepository;
import com.petelko.university.repository.StudentRepository;
import com.petelko.university.expectedstorage.ExpectedGroupStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(Lifecycle.PER_CLASS)
class GroupServiceImplTest {

    private ExpectedGroupStorage expectedStorage = new ExpectedGroupStorage();

    @Mock
    private GroupRepository mockGroupDao;
    @Mock
    private StudentRepository mockStudentDao;
    @InjectMocks
    private GroupServiceImpl groupService;

    @BeforeAll
    public void setUpBeforeClass() {
            MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_ReturnGroup_When_GetById() {
        when(mockGroupDao.findById(1L))
        .thenReturn(expectedStorage.getExpectedGroup());
        assertEquals(
                expectedStorage.getExpectedGroup().get(),
                groupService.getById(1L));
    }

    @Test
    void should_ReturnAllGroups_When_GetAll() {
        when(mockGroupDao.findAll((Sort) any())).thenReturn(expectedStorage.getExpectedGroupsList());
        assertEquals(
                expectedStorage.getExpectedGroupsList(),
                groupService.getAll());
    }

    @Test
    void should_CreateGroup_When_Create() {
        Group createdGroup = expectedStorage.getCreatedGroup();
        Group actualGroup = new Group("DD-44");
        when(mockGroupDao.save(actualGroup))
        .thenReturn(createdGroup);
        assertEquals(
                createdGroup,
                groupService.create(actualGroup));
    }

    @Test
    void should_UpdateGroup_When_Update() {
        Group updatedGroup = expectedStorage.getUpdatedGroup();
        when(mockGroupDao.save(updatedGroup)).thenReturn(expectedStorage.getUpdatedGroup());
        when(mockGroupDao.existsGroupById(updatedGroup.getId())).thenReturn(true);
        assertEquals(
                expectedStorage.getUpdatedGroup(),
                groupService.update(updatedGroup));
    }

    @Test
    void should_DeleteGroup_When_Delete() {
        Long id = expectedStorage.getGroupForDelete().getId();
        doNothing().when(mockGroupDao).undelete(id);
        when(mockGroupDao.existsGroupById(id)).thenReturn(true);
        when(mockStudentDao.existsStudentByGroupId(id)).thenReturn(false);
        groupService.delete(id);
        verify(mockGroupDao, times(1)).delete(id);
    }

    @Test
    void should_UndeleteGroup_When_Undelete() {
        Long id = expectedStorage.getGroupForDelete().getId();
        doNothing().when(mockGroupDao).undelete(id);
        when(mockGroupDao.existsGroupById(id)).thenReturn(true);
        groupService.undelete(id);
        verify(mockGroupDao, times(1)).undelete(id);
    }
}
