package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedGroupStorage;
import com.petelko.university.model.Group;
import com.petelko.university.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
class GroupRepositoryIntegrationTest {

    private ExpectedGroupStorage expectedStorage = new ExpectedGroupStorage();

    @Autowired
    private GroupRepository groupDao;

    @Test
    @DataSet(value = "group/groups.yml")
    void should_ReturnGroup_When_FindById() {
        assertEquals(expectedStorage.getExpectedGroup(), groupDao.findById(1L));
    }

    @Test
    @DataSet(value = "group/groups.yml")
    void should_ReturnGroupDTO_When_FindDTOById() {
        assertEquals(expectedStorage.getExpectedGroupDTO(), groupDao.findDTOById(1L));
    }

    @Test
    @DataSet(value = "group/groups.yml")
    void should_ReturnAllGroups_When_FindAll() {
        assertEquals(expectedStorage.getExpectedGroupsList(), groupDao.findAll());
    }

    @Test
    @DataSet(value = "group/groups.yml")
    void should_ReturnAllGroupDTO_When_FindAllDTO() {
        assertEquals(expectedStorage.getExpectedGroupDTOList(), groupDao.findAllDTO());
    }

    @Test
    @Transactional
    @DataSet(cleanBefore = true,
            skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "group/expected_groups.yml", ignoreCols = "id")
    void should_CreateGroup_When_SaveForCreate() {
        groupDao.save(expectedStorage.getGroupForCreate());
    }

    @Test
    @DataSet(value = "group/groups.yml")
    @Transactional
    void should_UpdateGroup_When_SaveForUpdate() {
        groupDao.save(expectedStorage.getUpdatedGroup());
        Group actual = groupDao.findById(2L).get();
        Group expected = expectedStorage.getUpdatedGroup();
        assertEquals(expected, actual);
    }

    @Test
    @DataSet(value = "group/groups.yml")
    @Transactional
    void should_DeleteGroup_When_Delete() {
        groupDao.delete(3L);
        assertTrue(groupDao.findById(3L).get().isDeleted());
    }

    @Test
    @DataSet(value = "group/groups.yml")
    @Transactional
    void should_UndeleteGroup_When_Undelete() {
        groupDao.undelete(3L);
        assertFalse(groupDao.findById(3L).get().isDeleted());
    }
}
