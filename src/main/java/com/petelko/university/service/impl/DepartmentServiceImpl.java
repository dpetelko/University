package com.petelko.university.service.impl;

import com.petelko.university.model.Department;
import com.petelko.university.model.Department_;
import com.petelko.university.model.Faculty;
import com.petelko.university.model.dto.DepartmentDTO;
import com.petelko.university.repository.DepartmentRepository;
import com.petelko.university.repository.GroupRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.FacultyService;
import com.petelko.university.service.exception.InvalidEntityException;
import com.petelko.university.service.exception.UnitNotEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private GroupRepository groupRepository;
    private FacultyService facultyService;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 FacultyService facultyService,
                                 GroupRepository groupRepository) {
        this.departmentRepository = departmentRepository;
        this.facultyService = facultyService;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<DepartmentDTO> getAllDTO() {
        return departmentRepository.findAllDTO();
    }

    @Override
    public List<DepartmentDTO> getAllActiveDTO() {
        return departmentRepository.findAllActiveDTO();
    }

    @Override
    public DepartmentDTO getDTOById(Long id) {
        return departmentRepository.findDTOById(id);
    }

    @Override
    public DepartmentDTO getDTOByName(String name) {
        return departmentRepository.findDTOByName(name);
    }

    @Transactional
    @Override
    public DepartmentDTO create(DepartmentDTO departmentDto) {
        Department department = convertToEntity(departmentDto);
        department = create(department);
        return convertToDto(department);
    }

    @Transactional
    @Override
    public DepartmentDTO update(DepartmentDTO departmentDto) {
        Department department = convertToEntity(departmentDto);
        department = update(department);
        return convertToDto(department);
    }

    @Override
    public boolean existsDepartmentById(Long id) {
        return departmentRepository.existsDepartmentById(id);
    }

    @Override
    public Department getById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Department with id '%s' not found", id)));
    }

    @Transactional
    @Override
    public List<Department> getAll() {
        Sort nameSort = Sort.by(Department_.NAME);
        Sort sortOrder = nameSort.ascending();
        return departmentRepository.findAll(sortOrder);
    }

    @Override
    public Department create(Department department) {
        if (department.getId() != null) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return departmentRepository.save(department);
    }

    @Transactional
    @Override
    public Department update(Department department) {
        Long id = department.getId();
        if (id == null) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        checkIsExist(id);
        return departmentRepository.save(department);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        checkIsEmpty(id);
        departmentRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        checkIsExist(id);
        departmentRepository.undelete(id);
    }

    @Override
    public boolean isUniqueName(String name, Long id) {
        return isNew(id)
                ? !departmentRepository.existsByName(name)
                : !departmentRepository.existsByNameAndIdNot(name, id);
    }

    private boolean isNew(Long id) {
        return Objects.isNull(id);
    }

    private static DepartmentDTO convertToDto(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        departmentDTO.setDescription(department.getDescription());
        departmentDTO.setDeleted(department.isDeleted());
        if (department.getFaculty() != null) {
            departmentDTO.setFacultyId(department.getFaculty().getId());
            departmentDTO.setFacultyName(department.getFaculty().getName());
            departmentDTO.setFacultyDescription(department.getFaculty().getDescription());
        }
        return departmentDTO;
    }

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();
        if (departmentDTO.getId() != null) {
            department = getById(departmentDTO.getId());
        }
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        if (departmentDTO.getFacultyId() != null) {
            Faculty faculty = facultyService.getById(departmentDTO.getFacultyId());
            department.setFaculty(faculty);
        } else {
            department.setFaculty(null);
        }
        return department;
    }

    private void checkIsExist(Long id) {
        if (!existsDepartmentById(id)) {
            String msg = format("Department with id '%s' not found!", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private void checkIsEmpty(Long id) {
        if (groupRepository.existsGroupByDepartmentId(id)) {
            throw new UnitNotEmptyException("Cannot delete not empty Department!");
        }
    }
}
