package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedStudentStorage;
import com.petelko.university.model.Student;
import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.Comparator;
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
class StudentRepositoryIntegrationTest {

    private ExpectedStudentStorage expectedStorage = new ExpectedStudentStorage();

    @Autowired
    private StudentRepository studentDao;

    @Test
    @DataSet(value = "student/students.yml")
    @Transactional
    void should_DeleteStudent_When_Delete() {
        studentDao.delete(2L);
        assertTrue(studentDao.findById(2L).get().isDeleted());
    }

    @Test
    @DataSet(value = "student/students.yml")
    @Transactional
    void should_UndeleteStudent_When_Undelete() {
        studentDao.delete(2L);
        studentDao.undelete(2L);
        assertFalse(studentDao.findById(2L).get().isDeleted());
    }

    @Test
    @DataSet(value = "student/students.yml")
    void should_ReturnStudent_When_FindDTOById() {
        assertEquals(expectedStorage.getExpectedStudentDTO(), studentDao.findDTOById(1L));
    }

    @Test
    @DataSet(value = "student/students.yml")
    void should_ReturnStudentDTO_When_FindById() {
        assertEquals(expectedStorage.getExpectedStudent(), studentDao.findById(1L));
    }

    @Test
    @DataSet(value = "student/students.yml")
    void should_ReturnAllStudents_When_FindAll() {
        assertThat(expectedStorage.getExpectedStudentsList(), is(studentDao.findAll()));
    }

    @Test
    @DataSet(value = "student/students.yml")
    void should_ReturnAllStudents_When_FindAllDTO() {
        List<StudentDTO> actual = studentDao.findAllDTO();
        actual.sort(Comparator.comparing(StudentDTO::getId));
        assertThat(expectedStorage.getExpectedStudentDTOList(), is(actual));
    }

    @Test
    @DataSet(value = "student/students.yml")
    @Transactional
    void should_ReturnAllActiveStudents_When_FindByDeletedFalse() {
        studentDao.delete(2L);
        assertEquals(expectedStorage.getExpectedActiveStudentsList(), studentDao.findByDeletedFalse());
    }

    @Test
    @DataSet(value = "student/students.yml")
    @Transactional
    void should_ReturnAllActiveStudentDTO_When_FindAllActiveDTO() {
        studentDao.delete(2L);
        List<StudentDTO> actual = studentDao.findAllActiveDTO();
        actual.sort(Comparator.comparing(StudentDTO::getId));
        assertThat(expectedStorage.getExpectedActiveStudentDTOList(), is((actual)));
    }

    @Test
    @Transactional
    @DataSet(value = "student/groups.yml",
            cleanBefore = true,
            skipCleaningFor = {"flyway_schema_history"})
    @ExpectedDataSet(value = "student/expected_students.yml", ignoreCols = "id")
    void should_CreateStudent_When_SaveForCreate() {
        studentDao.save(expectedStorage.getCreatedStudent2());
    }

    @Test
    @DataSet(value = "student/students.yml")
    @Transactional
    void should_UpdateStudent_When_SaveForUpdate() {
        studentDao.save(expectedStorage.getUpdatedStudent());
        Student actual = studentDao.findById(1L).get();
        Student expected = expectedStorage.getUpdatedStudent();
        assertEquals(expected, actual);
    }
}
