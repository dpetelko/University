package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedFacultyStorage;
import com.petelko.university.model.Faculty;
import com.petelko.university.repository.FacultyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
class FacultyRepositoryIntegrationTest {

    private ExpectedFacultyStorage expectedStorage = new ExpectedFacultyStorage();

    @Autowired
    private FacultyRepository facultyDao;

    @Test
    @DataSet(value = "faculty/faculties.yml")
    void should_ReturnAllFaculty_When_FindAllDTO() {
        assertThat(expectedStorage.getExpectedFacultyDTOList(), is(facultyDao.findAllDTO()));
    }

    @Test
    @DataSet(value = "faculty/faculties.yml")
    void should_ReturnAllActiveFacultyDTO_When_FindAllActiveDTO() {
        facultyDao.delete(2L);
        assertThat(expectedStorage.getExpectedActiveFacultyDTOList(), is(facultyDao.findAllActiveDTO()));
    }

    @Test
    @DataSet(value = "faculty/faculties.yml")
    void should_ReturnFaculty_When_FindDTOById() {
        assertThat(expectedStorage.getExpectedFacultyDTO(), is(facultyDao.findDTOById(1L)));
    }

    @Test
    @DataSet(value = "faculty/faculties.yml")
    @Transactional
    void should_DeleteFaculty_When_Delete() {
        facultyDao.delete(2L);
        assertTrue(facultyDao.findById(2L).get().isDeleted());
    }

    @Test
    @DataSet(value = "faculty/faculties.yml")
    @Transactional
    void should_UndeleteFaculty_When_Undelete() {
        facultyDao.delete(2L);
        facultyDao.undelete(2L);
        assertFalse(facultyDao.findById(2L).get().isDeleted());
    }

    @Test
    @Transactional
    @DataSet(cleanBefore = true,
            skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "faculty/expected_faculties.yml", ignoreCols = "id")
    void should_CreateFaculty_When_SaveForCreate() {
        facultyDao.save(expectedStorage.getFacultyForCreate());
    }

    @Test
    @DataSet(value = "faculty/faculties.yml")
    @Transactional
    void should_UpdateFaculty_When_SaveForUpdate() {
        facultyDao.save(expectedStorage.getUpdatedFaculty());
        Faculty actual = facultyDao.findById(1L).get();
        Faculty expected = expectedStorage.getUpdatedFaculty();
        assertEquals(expected, actual);
    }
}
