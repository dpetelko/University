package com.petelko.university.service.impl;

import com.petelko.university.model.Department;
import com.petelko.university.model.Subject;
import com.petelko.university.model.Subject_;
import com.petelko.university.model.dto.SubjectDTO;
import com.petelko.university.repository.SubjectRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.SubjectService;
import com.petelko.university.service.exception.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;
    private DepartmentService departmentService;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository,
                              DepartmentService departmentService) {
        this.subjectRepository = subjectRepository;
        this.departmentService = departmentService;
    }


    @Override
    public List<SubjectDTO> getAllDTO() {
        return subjectRepository.findAllDTO();
    }

    @Override
    public List<SubjectDTO> getAllActiveDTO() {
        return subjectRepository.findAllActiveDTO();
    }

    @Override
    public SubjectDTO getDTOById(Long id) {
        return subjectRepository.findDTOById(id);
    }

    @Override
    public SubjectDTO getDTOByName(String name) {
        return subjectRepository.findDTOByName(name);
    }

    @Transactional
    @Override
    public SubjectDTO create(SubjectDTO subjectDto) {
        Subject subject = convertToEntity(subjectDto);
        subject = create(subject);
        return convertToDto(subject);
    }

    @Transactional
    @Override
    public SubjectDTO update(SubjectDTO subjectDto) {
        Subject subject = convertToEntity(subjectDto);
        subject = update(subject);
        return convertToDto(subject);
    }

    @Override
    public boolean existsSubjectById(Long id) {
        return subjectRepository.existsSubjectById(id);
    }

    @Override
    public boolean isUniqueName(String name, Long id) {
        return isNew(id)
                ? !subjectRepository.existsByName(name)
                : !subjectRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public Subject getById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Subject with id '%s' not found", id)));
    }

    @Override
    public List<Subject> getAll() {
        Sort nameSort = Sort.by(Subject_.NAME);
        Sort sortOrder = nameSort.ascending();
        return subjectRepository.findAll(sortOrder);
    }

    @Transactional
    @Override
    public Subject create(Subject subject) {
        if (subject.getId() != null) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return subjectRepository.save(subject);
    }

    @Transactional
    @Override
    public Subject update(Subject subject) {
        Long id = subject.getId();
        if (id == null) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        checkIsExist(id);
        return subjectRepository.save(subject);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        subjectRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        checkIsExist(id);
        subjectRepository.undelete(id);
    }

    private static SubjectDTO convertToDto(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        subjectDTO.setDescription(subject.getDescription());
        subjectDTO.setDeleted(subject.isDeleted());
        if (subject.getDepartment() != null) {
            subjectDTO.setDepartmentId(subject.getDepartment().getId());
            subjectDTO.setDepartmentName(subject.getDepartment().getName());
            subjectDTO.setDepartmentDescription(subject.getDepartment().getDescription());
        }
        return subjectDTO;
    }

    private Subject convertToEntity(SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        if (subjectDTO.getId() != null) {
            subject = getById(subjectDTO.getId());
        }
        subject.setName(subjectDTO.getName());
        subject.setDescription(subjectDTO.getDescription());
        if (subjectDTO.getDepartmentId() != null) {
            Department department = departmentService.getById(subjectDTO.getDepartmentId());
            subject.setDepartment(department);
        } else {
            subject.setDepartment(null);
        }
        return subject;
    }

    private void checkIsExist(Long id) {
        if (!existsSubjectById(id)) {
            String msg = format("Subject with id '%s' not found!", id);
            throw new EntityNotFoundException(msg);
        }
    }


    private boolean isNew(Long id) {
        return Objects.isNull(id);
    }
}
