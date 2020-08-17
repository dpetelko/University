package com.petelko.university.service.impl;

import com.petelko.university.model.Group;
import com.petelko.university.model.Student;
import com.petelko.university.repository.StudentRepository;
import com.petelko.university.expectedstorage.ExpectedStudentStorage;
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
class StudentServiceImplTest {

    private ExpectedStudentStorage expectedStorage = new ExpectedStudentStorage();

    @Mock
    private StudentRepository mockStudentDao;
    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeAll
    public void setUpBeforeClass() {
            MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_ReturnStudent_When_GetById() {
        when(mockStudentDao.findById(1L))
                .thenReturn(expectedStorage.getExpectedStudent());
        assertEquals(
                (expectedStorage.getExpectedStudent().get()),
                studentService.getById(1L));
    }

    @Test
    void should_ReturnAllStudents_When_GetAll() {
        when(mockStudentDao.findAll((Sort) any())).thenReturn(expectedStorage.getExpectedStudentsList());
        assertEquals(
                expectedStorage.getExpectedStudentsList(),
                studentService.getAll());
    }

    @Test
    void should_CreateStudent_When_Create() {
        Student createdStudent = expectedStorage.getCreatedStudent();
        Student actualStudent = new Student("Dmitry", "Petelko", new Group(1L, "AA-11"));
        when(mockStudentDao.save(actualStudent))
                .thenReturn(createdStudent);
        assertEquals(
                createdStudent,
                studentService.create(actualStudent));
    }

    @Test
    void should_UpdateStudent_When_Update() {
        Student updatedStudent = expectedStorage.getUpdatedStudent();
        when(mockStudentDao.save(updatedStudent)).thenReturn(expectedStorage.getUpdatedStudent());
        when(mockStudentDao.existsStudentById(updatedStudent.getId())).thenReturn(true);
        assertEquals(
                expectedStorage.getUpdatedStudent(),
                studentService.update(updatedStudent));
    }

    @Test
    void should_DeleteStudent_When_Delete() {
        Long id = expectedStorage.getStudentForDelete().getId();
        doNothing().when(mockStudentDao).delete(id);
        when(mockStudentDao.existsStudentById(id)).thenReturn(true);
        studentService.delete(id);
        verify(mockStudentDao, times(1)).delete(id);
    }

    @Test
    void should_UndeleteStudent_When_Undelete() {
        Long id = expectedStorage.getStudentForDelete().getId();
        doNothing().when(mockStudentDao).undelete(id);
        when(mockStudentDao.existsStudentById(id)).thenReturn(true);
        studentService.undelete(id);
        verify(mockStudentDao, times(1)).undelete(id);
    }
}
