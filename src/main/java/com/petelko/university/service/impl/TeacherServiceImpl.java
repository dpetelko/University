package com.petelko.university.service.impl;

import com.petelko.university.model.Department;
import com.petelko.university.model.Subject;
import com.petelko.university.model.Teacher;
import com.petelko.university.model.Teacher_;
import com.petelko.university.model.dto.TeacherDTO;
import com.petelko.university.repository.TeacherRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.SubjectService;
import com.petelko.university.service.TeacherService;
import com.petelko.university.service.exception.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;
    private SubjectService subjectService;
    private DepartmentService departmentService;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              SubjectService subjectService,
                              DepartmentService departmentService) {
        this.teacherRepository = teacherRepository;
        this.subjectService = subjectService;
        this.departmentService = departmentService;
    }

    @Override
    public List<TeacherDTO> getAllDTO() {
        return teacherRepository.findAllDTO();
    }

    @Override
    public List<TeacherDTO> getAllActiveDTO() {
        return teacherRepository.findAllActiveDTO();
    }

    @Override
    public TeacherDTO getDTOById(Long id) {
        return teacherRepository.findDTOById(id);
    }

    @Transactional
    @Override
    public TeacherDTO create(TeacherDTO teacherDto) {
        Teacher teacher = convertToEntity(teacherDto);
        teacher = create(teacher);
        return convertToDto(teacher);
    }

    @Transactional
    @Override
    public TeacherDTO update(TeacherDTO teacherDto) {
        Teacher teacher = convertToEntity(teacherDto);
        teacher = update(teacher);
        return convertToDto(teacher);
    }

    @Override
    public boolean existsTeacherById(Long id) {
        return teacherRepository.existsTeacherById(id);
    }

    @Override
    public boolean isUniqueEmail(String email, Long id) {
        return isNew(id)
                ? !teacherRepository.existsByEmail(email)
                : !teacherRepository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public Teacher getById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Teacher with id '%s' not found", id)));
    }

    @Override
    public List<Teacher> getAll() {
        Sort lastNameSort = Sort.by(Teacher_.LAST_NAME);
        Sort firstNameSort = Sort.by(Teacher_.FIRST_NAME);
        Sort sortOrder = lastNameSort.and(firstNameSort).ascending();
        return teacherRepository.findAll(sortOrder);
    }

    @Transactional
    @Override
    public Teacher create(Teacher teacher) {
        if (teacher.getId() != null) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return teacherRepository.save(teacher);
    }

    @Transactional
    @Override
    public Teacher update(Teacher teacher) {
        Long id = teacher.getId();
        if (id == null) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        checkIsExist(id);
        return teacherRepository.save(teacher);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        teacherRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        checkIsExist(id);
        teacherRepository.undelete(id);
    }

    private static TeacherDTO convertToDto(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setFirstName(teacher.getFirstName());
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setDeleted(teacher.isDeleted());
        teacherDTO.setEmail(teacher.getEmail());
        teacherDTO.setPhoneNumber(teacher.getPhoneNumber());
        if (teacher.getSubject() != null) {
            teacherDTO.setSubjectId(teacher.getSubject().getId());
            teacherDTO.setSubjectName(teacher.getSubject().getName());
            teacherDTO.setSubjectDescription(teacher.getSubject().getDescription());
        }
        if (teacher.getDepartment() != null) {
            teacherDTO.setDepartmentId(teacher.getDepartment().getId());
            teacherDTO.setDepartmentName(teacher.getDepartment().getName());
            teacherDTO.setDepartmentDescription(teacher.getDepartment().getDescription());
        }
        return teacherDTO;
    }

    private Teacher convertToEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        if (teacherDTO.getId() != null) {
            teacher = getById(teacherDTO.getId());
        }
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        if (teacherDTO.getSubjectId() != null) {
            Subject subject = subjectService.getById(teacherDTO.getSubjectId());
            teacher.setSubject(subject);
        } else {
            teacher.setSubject(null);
        }
        if (teacherDTO.getDepartmentId() != null) {
            Department department = departmentService.getById(teacherDTO.getDepartmentId());
            teacher.setDepartment(department);
        } else {
            teacher.setDepartment(null);
        }
        return teacher;
    }

    private void checkIsExist(Long id) {
        if (!existsTeacherById(id)) {
            String msg = format("Teacher with id '%s' not found!", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private boolean isNew(Long id) {
        return Objects.isNull(id);
    }
}
