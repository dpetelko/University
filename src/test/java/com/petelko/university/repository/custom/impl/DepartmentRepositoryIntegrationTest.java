package com.petelko.university.repository.custom.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.petelko.university.expectedstorage.ExpectedDepartmentStorage;
import com.petelko.university.model.Department;
import com.petelko.university.repository.DepartmentRepository;
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
class DepartmentRepositoryIntegrationTest {

    private ExpectedDepartmentStorage expectedStorage = new ExpectedDepartmentStorage();

    @Autowired
    private DepartmentRepository departmentDao;

    @Test
    @DataSet(value = "department/departments.yml")
    void should_ReturnAllDepartment_When_FindAllDTO() {
        assertThat(expectedStorage.getExpectedDepartmentDTOList(), is(departmentDao.findAllDTO()));
    }

    @Test
    @DataSet(value = "department/departments.yml")
    void should_ReturnAllActiveDepartmentDTO_When_FindAllActiveDTO() {
        departmentDao.delete(2L);
        assertThat(expectedStorage.getExpectedActiveDepartmentDTOList(), is(departmentDao.findAllActiveDTO()));
    }

    @Test
    @DataSet(value = "department/departments.yml")
    void should_ReturnDepartment_When_FindDTOById() {
        assertThat(expectedStorage.getExpectedDepartmentDTO(), is(departmentDao.findDTOById(1L)));
    }

    @Test
    @DataSet(value = "department/departments.yml")
    @Transactional
    void should_DeleteDepartment_When_Delete() {
        departmentDao.delete(2L);
        assertTrue(departmentDao.findById(2L).get().isDeleted());
    }

    @Test
    @DataSet(value = "department/departments.yml")
    @Transactional
    void should_UndeleteDepartment_When_Undelete() {
        departmentDao.delete(2L);
        departmentDao.undelete(2L);
        assertFalse(departmentDao.findById(2L).get().isDeleted());
    }

    @Test
    @Transactional
    @DataSet(value = "department/faculties.yml",
            cleanBefore = true,
            skipCleaningFor = {"flyway_schema_history"})
    @ExpectedDataSet(value = "department/expected_departments.yml", ignoreCols = "id")
    void should_CreateDepartment_When_SaveForCreate() {
        departmentDao.save(expectedStorage.getDepartmentForCreate());
    }

    @Test
    @DataSet(value = "department/departments.yml")
    @Transactional
    void should_UpdateDepartment_When_SaveForUpdate() {
        departmentDao.save(expectedStorage.getUpdatedDepartment());
        Department actual = departmentDao.findById(2L).get();
        Department expected = expectedStorage.getUpdatedDepartment();
        assertEquals(expected, actual);
    }
}
