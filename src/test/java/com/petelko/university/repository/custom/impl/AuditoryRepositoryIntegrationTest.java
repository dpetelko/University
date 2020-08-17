package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedAuditoryStorage;
import com.petelko.university.model.Auditory;
import com.petelko.university.repository.AuditoryRepository;
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
class AuditoryRepositoryIntegrationTest {

    private ExpectedAuditoryStorage expectedStorage = new ExpectedAuditoryStorage();

    @Autowired
    private AuditoryRepository auditoryDao;

    @Test
    @DataSet(value = "auditory/auditories.yml")
    void should_ReturnAllAuditoryDTO_When_FindAllDTO() {
        assertThat(expectedStorage.getExpectedAuditoryDTOList(), is(auditoryDao.findAllDTO()));
    }


    @Test
    @DataSet(value = "auditory/auditories.yml")
    void should_ReturnAllActiveAuditoryDTO_When_FindAllActiveDTO() {
        auditoryDao.delete(2L);
        assertThat(expectedStorage.getExpectedActiveAuditoryDTOList(), is(auditoryDao.findAllActiveDTO()));
    }


    @Test
    @DataSet(value = "auditory/auditories.yml")
    void should_ReturnAuditoryDTO_When_FindDTOById() {
        assertThat(expectedStorage.getExpectedAuditoryDTO(), is(auditoryDao.findDTOById(1L)));
    }


    @Test
    @DataSet(value = "auditory/auditories.yml")
    @Transactional
    void should_DeleteAuditory_When_Delete() {
        auditoryDao.delete(2L);
        assertTrue(auditoryDao.findById(2L).get().isDeleted());
    }


    @Test
    @DataSet(value = "auditory/auditories.yml")
    @Transactional
    void should_UndeleteAuditory_When_Undelete() {
        auditoryDao.delete(2L);
        auditoryDao.undelete(2L);
        assertFalse(auditoryDao.findById(2L).get().isDeleted());
    }


    @Test
    @Transactional
    @DataSet(cleanBefore = true,
            skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "auditory/expected_auditories.yml", ignoreCols = "id")
    void should_CreateAuditory_When_SaveForCreate() {
        auditoryDao.save(expectedStorage.getAuditoryForCreate());
    }

    @Test
    @Transactional
    @DataSet(value = "auditory/auditories.yml")
    void should_UpdateAuditory_When_SaveForUpdate() {
        auditoryDao.save(expectedStorage.getUpdatedAuditory());
        Auditory actual = auditoryDao.findById(1L).get();
        Auditory expected = expectedStorage.getUpdatedAuditory();
        assertEquals(expected, actual);
    }
}
