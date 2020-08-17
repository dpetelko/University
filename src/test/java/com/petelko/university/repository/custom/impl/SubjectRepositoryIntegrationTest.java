package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedSubjectStorage;
import com.petelko.university.model.Subject;
import com.petelko.university.repository.SubjectRepository;
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
class SubjectRepositoryIntegrationTest {

    private ExpectedSubjectStorage expectedStorage = new ExpectedSubjectStorage();

    @Autowired
    private SubjectRepository subjectDao;

    @Test
    @DataSet(value = "subject/subjects.yml")
    void should_ReturnAllSubject_When_FindAllDTO() {
        assertThat(expectedStorage.getExpectedSubjectDTOList(), is(subjectDao.findAllDTO()));
    }

    @Test
    @DataSet(value = "subject/subjects.yml")
    void should_ReturnAllActiveSubjectDTO_When_FindAllActiveDTO() {
        subjectDao.delete(3L);
        assertThat(expectedStorage.getExpectedActiveSubjectDTOList(), is(subjectDao.findAllActiveDTO()));
    }

    @Test
    @DataSet(value = "subject/subjects.yml")
    void should_ReturnSubject_When_FindDTOById() {
        assertThat(expectedStorage.getExpectedSubjectDTO(), is(subjectDao.findDTOById(2L)));
    }

    @Test
    @DataSet(value = "subject/subjects.yml")
    @Transactional
    void should_DeleteSubject_When_Delete() {
        subjectDao.delete(3L);
        assertTrue(subjectDao.findById(3L).get().isDeleted());
    }

    @Test
    @DataSet(value = "subject/subjects.yml")
    @Transactional
    void should_UndeleteSubject_When_Undelete() {
        subjectDao.delete(3L);
        subjectDao.undelete(3L);
        assertFalse(subjectDao.findById(3L).get().isDeleted());
    }

    @Test
    @Transactional
    @DataSet(value = "subject/departments.yml",
            cleanBefore = true,
            skipCleaningFor = {"flyway_schema_history"})
    @ExpectedDataSet(value = "subject/expected_subjects.yml", ignoreCols = "id")
    void should_CreateSubject_When_SaveForCreate() {
        subjectDao.save(expectedStorage.getSubjectForCreate());
    }

    @Test
    @DataSet(value = "subject/subjects.yml")
    @Transactional
    void should_UpdateSubject_When_SaveForUpdate() {
        subjectDao.save(expectedStorage.getUpdatedSubject());
        Subject actual = subjectDao.findById(1L).get();
        Subject expected = expectedStorage.getUpdatedSubject();
        assertEquals(expected, actual);
    }
}
