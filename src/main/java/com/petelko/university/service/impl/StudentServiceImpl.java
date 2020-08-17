package com.petelko.university.service.impl;

import com.petelko.university.model.Group;
import com.petelko.university.model.Student;
import com.petelko.university.model.Student_;
import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.repository.StudentRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.GroupService;
import com.petelko.university.service.StudentService;
import com.petelko.university.service.exception.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private GroupService groupService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, GroupService groupService) {
        this.studentRepository = studentRepository;
        this.groupService = groupService;
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Student with id '%s' not found", id)));
    }

    @Override
    public int countByGroupId(Long id) {
        return studentRepository.countByGroupId(id);
    }

    @Override
    public StudentDTO getDTOById(Long id) {
        return studentRepository.findDTOById(id);
    }

    @Override
    public List<Student> getAll() {
        Sort lastNameSort = Sort.by(Student_.LAST_NAME);
        Sort firstNameSort = Sort.by(Student_.FIRST_NAME);
        Sort sortOrder = lastNameSort.and(firstNameSort).ascending();
        return studentRepository.findAll(sortOrder);
    }

    @Override
    public List<StudentDTO> getAllDTO() {
        return studentRepository.findAllDTO();
    }

    @Override
    public List<StudentDTO> getAllActiveDTO() {
        return studentRepository.findAllActiveDTO();
    }

    @Override
    public StudentDTO getDTOByEmail(String email) {
        return studentRepository.findDTOByEmail(email);
    }

    @Override
    public boolean existsStudentByIdAndGroupId(Long id, Long groupId) {
        return studentRepository.existsStudentByIdAndGroupId(id, groupId);
    }

    @Override
    public boolean isUniqueEmail(String email, Long id) {
        return isNew(id)
                ? !studentRepository.existsByEmail(email)
                : !studentRepository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public boolean existsStudentById(Long id) {
        return studentRepository.existsStudentById(id);
    }

    @Transactional
    @Override
    public Student create(Student student) {
        if (student.getId() != null) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public StudentDTO create(StudentDTO studentDto) {
        Student student = convertToEntity(studentDto);
        student = create(student);
        return convertToDto(student);

    }

    @Transactional
    @Override
    public Student update(Student student) {
        Long id = student.getId();
        if (id == null) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        checkIsExist(id);
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public StudentDTO update(StudentDTO studentDto) {
        Student student = convertToEntity(studentDto);
        student = update(student);
        return convertToDto(student);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        studentRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        checkIsExist(id);
        studentRepository.undelete(id);
    }

    private static StudentDTO convertToDto(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setDeleted(student.isDeleted());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        if (student.getGroup() != null) {
            studentDTO.setGroupId(student.getGroup().getId());
            studentDTO.setGroupName(student.getGroup().getName());
        }
        return studentDTO;
    }

    private Student convertToEntity(StudentDTO studentDto) {
        Student student = new Student();
        if (studentDto.getId() != null) {
            student = getById(studentDto.getId());
        }
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setPhoneNumber(studentDto.getPhoneNumber());
        if (studentDto.getGroupId() != null) {
            Group group = groupService.getById(studentDto.getGroupId());
            student.setGroup(group);
        } else {
            student.setGroup(null);
        }
        return student;
    }

    private void checkIsExist(Long id) {
        if (!existsStudentById(id)) {
            String msg = format("Student with id '%s' not found!", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private boolean isNew(Long id) {
        return Objects.isNull(id);
    }
}
