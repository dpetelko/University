package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedLessonStorage;
import com.petelko.university.model.Lesson;
import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
class LessonRepositoryIntegrationTest {

    private ExpectedLessonStorage expectedStorage = new ExpectedLessonStorage();

    @Autowired
    private LessonRepository lessonDao;

    @Test
    @DataSet(value = "lesson/lessons.yml")
    void should_ReturnAllLessonDTO_When_FindAllDTO() {
        List<LessonDTO> lessonDTOList = new ArrayList<>();
        lessonDTOList.add(expectedStorage.getExpectedLessonDTOid1());
        lessonDTOList.add(expectedStorage.getExpectedLessonDTOid2());
        assertThat(lessonDTOList, is(lessonDao.findAllDTO()));
    }

    @Test
    @DataSet(value = "lesson/lessons.yml")
    void should_ReturnAllActiveLectureDTO_When_FindAllActiveDTO() {
        lessonDao.delete(2L);
        List<LessonDTO> lessonDTOList = new ArrayList<>();
        lessonDTOList.add(expectedStorage.getExpectedLessonDTOid1());
        assertThat(lessonDTOList, is(lessonDao.findAllActiveDTO()));
    }

    @Test
    @DataSet(value = "lesson/lessons.yml")
    void should_ReturnLessonDTO_When_FindDTOById() {
        assertThat(expectedStorage.getExpectedLessonDTOid1(), is(lessonDao.findDTOById(1L)));
    }

    @Test
    @DataSet(value = "lesson/lessons.yml")
    @Transactional
    void should_DeleteLesson_When_Delete() {
        lessonDao.delete(2L);
        assertTrue(lessonDao.findById(2L).get().isDeleted());
    }

    @Test
    @DataSet(value = "lesson/lessons.yml")
    @Transactional
    void should_UndeleteLesson_When_Undelete() {
        lessonDao.delete(2L);
        lessonDao.undelete(2L);
        assertFalse(lessonDao.findById(2L).get().isDeleted());
    }

    @Test
    @Transactional
    @DataSet(value = "lesson/tables.yml",
            cleanBefore = true,
            skipCleaningFor = {"flyway_schema_history"})
    @ExpectedDataSet(value = "lesson/expected_lessons.yml", ignoreCols = "id")
    void should_CreateLesson_When_SaveForCreate() {
        lessonDao.save(expectedStorage.getLessonForCreate());
    }

    @Test
    @DataSet(value = "lesson/lessons.yml")
    @Transactional
    void should_UpdateLecture_When_SaveForUpdate() {
        lessonDao.save(expectedStorage.getUpdatedLesson());
        Lesson actual = lessonDao.findById(1L).get();
        Lesson expected = expectedStorage.getUpdatedLesson();
        assertEquals(expected, actual);
    }
}
