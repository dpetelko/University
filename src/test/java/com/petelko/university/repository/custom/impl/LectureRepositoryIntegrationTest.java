package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedLectureStorage;
import com.petelko.university.model.Lecture;
import com.petelko.university.repository.LectureRepository;
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
class LectureRepositoryIntegrationTest {

    private ExpectedLectureStorage expectedStorage = new ExpectedLectureStorage();

    @Autowired
    private LectureRepository lectureDao;

    @Test
    @DataSet(value = "lecture/lectures.yml")
    void should_ReturnAllLectureDTO_When_FindAllDTO() {
        assertThat(expectedStorage.getExpectedLectureDTOList(), is(lectureDao.findAllDTO()));
    }

    @Test
    @DataSet(value = "lecture/lectures.yml")
    void should_ReturnAllActiveLectureDTO_When_FindAllActiveDTO() {
        lectureDao.delete(2L);
        assertThat(expectedStorage.getExpectedActiveLectureDTOList(), is(lectureDao.findAllActiveDTO()));
    }

    @Test
    @DataSet(value = "lecture/lectures.yml")
    void should_ReturnLectureDTO_When_FindDTOById() {
        assertThat(expectedStorage.getExpectedLectureDTO(), is(lectureDao.findDTOById(1L)));
    }

    @Test
    @DataSet(value = "lecture/lectures.yml")
    @Transactional
    void should_DeleteLecture_When_Delete() {
        lectureDao.delete(2L);
        assertTrue(lectureDao.findById(2L).get().isDeleted());
    }

    @Test
    @DataSet(value = "lecture/lectures.yml")
    @Transactional
    void should_UndeleteLecture_When_Undelete() {
        lectureDao.delete(2L);
        lectureDao.undelete(2L);
        assertFalse(lectureDao.findById(2L).get().isDeleted());
    }

    @Test
    @Transactional
    @DataSet(cleanBefore = true,
            skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "lecture/expected_lectures.yml", ignoreCols = "id")
    void should_CreateLecture_When_SaveForCreate() {
        lectureDao.save(expectedStorage.getLectureForCreate());
    }

    @Test
    @DataSet(value = "lecture/lectures.yml")
    @Transactional
    void should_UpdateLecture_When_SaveForUpdate() {
        lectureDao.save(expectedStorage.getUpdatedLecture());
        Lecture actual = lectureDao.findById(1L).get();
        Lecture expected = expectedStorage.getUpdatedLecture();
        assertEquals(expected, actual);
    }
}
