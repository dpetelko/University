package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedTeacherStorage;
import com.petelko.university.model.Teacher;
import com.petelko.university.model.dto.TeacherDTO;
import com.petelko.university.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
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
class TeacherRepositoryIntegrationTest {

    private ExpectedTeacherStorage expectedStorage = new ExpectedTeacherStorage();

    @Autowired
    private TeacherRepository teacherDao;

    @Test
    @DataSet(value = "teacher/teachers.yml")
    void should_ReturnAllTeachers_When_FindAllDTO() {
        assertThat(expectedStorage.getExpectedTeacherDTOList(), is(teacherDao.findAllDTO()));
    }

    @Test
    @DataSet(value = "teacher/teachers.yml")
    @Transactional
    void should_ReturnAllActiveTeacherDTO_When_FindAllActiveDTO() {
        teacherDao.delete(3L);
        List<TeacherDTO> actual = teacherDao.findAllActiveDTO();
        assertThat(expectedStorage.getExpectedActiveTeacherDTOList(), is((actual)));
    }

    @Test
    @DataSet(value = "teacher/teachers.yml")
    void should_ReturnTeacher_When_FindDTOById() {
        assertEquals(expectedStorage.getExpectedTeacherDTO(), teacherDao.findDTOById(1L));
    }

    @Test
    @DataSet(value = "teacher/teachers.yml")
    void should_ReturnTeacher_When_FindDTOByEmail() {
        assertEquals(expectedStorage.getExpectedTeacherDTO(), teacherDao.findDTOByEmail("teacher1@11.ru"));
    }

    @Test
    @DataSet(value = "teacher/teachers.yml")
    @Transactional
    void should_DeleteTeacher_When_Delete() {
        teacherDao.delete(3L);
        assertTrue(teacherDao.findById(3L).get().isDeleted());
    }

    @Test
    @DataSet(value = "teacher/teachers.yml")
    @Transactional
    void should_UndeleteTeacher_When_Undelete() {
        teacherDao.delete(3L);
        teacherDao.undelete(3L);
        assertFalse(teacherDao.findById(3L).get().isDeleted());
    }

    @Test
    @Transactional
    @DataSet(value = "teacher/tables.yml",
            cleanBefore = true,
            skipCleaningFor = {"flyway_schema_history"})
    @ExpectedDataSet(value = "teacher/expected_teachers.yml", ignoreCols = "id")
    void should_CreateTeacher_When_SaveForCreate() {
        teacherDao.save(expectedStorage.getTeacherForCreate());
    }

    @Test
    @DataSet(value = "teacher/teachers.yml")
    @Transactional
    void should_UpdateTeacher_When_SaveForUpdate() {
        teacherDao.save(expectedStorage.getUpdatedTeacher());
        Teacher actual = teacherDao.findById(1L).get();
        Teacher expected = expectedStorage.getUpdatedTeacher();
        assertEquals(expected, actual);
    }
}
