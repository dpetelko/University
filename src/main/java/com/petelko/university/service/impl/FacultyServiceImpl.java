package com.petelko.university.service.impl;

import com.petelko.university.model.Faculty;
import com.petelko.university.model.Faculty_;
import com.petelko.university.model.dto.FacultyDTO;
import com.petelko.university.repository.DepartmentRepository;
import com.petelko.university.repository.FacultyRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
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
public class FacultyServiceImpl implements FacultyService {

    private FacultyRepository facultyRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              DepartmentRepository departmentRepository) {
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<FacultyDTO> getAllDTO() {
        return facultyRepository.findAllDTO();
    }

    @Override
    public List<FacultyDTO> getAllActiveDTO() {
        return facultyRepository.findAllActiveDTO();
    }

    @Override
    public FacultyDTO getDTOById(Long id) {
        return facultyRepository.findDTOById(id);
    }

    @Override
    public FacultyDTO getDTOByName(String name) {
        return facultyRepository.findDTOByName(name);
    }

    @Transactional
    @Override
    public FacultyDTO create(FacultyDTO facultyDto) {
        Faculty faculty = convertToEntity(facultyDto);
        faculty = create(faculty);
        return convertToDto(faculty);
    }

    @Transactional
    @Override
    public FacultyDTO update(FacultyDTO facultyDto) {
        Faculty faculty = convertToEntity(facultyDto);
        faculty = update(faculty);
        return convertToDto(faculty);
    }

    @Override
    public boolean existsFacultyById(Long id) {
        return facultyRepository.existsFacultyById(id);
    }

    @Override
    public Faculty getById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Faculty with id '%s' not found", id)));

    }

    @Override
    public List<Faculty> getAll() {
        Sort nameSort = Sort.by(Faculty_.NAME);
        Sort sortOrder = nameSort.ascending();
        return facultyRepository.findAll(sortOrder);
    }

    @Transactional
    @Override
    public Faculty create(Faculty faculty) {
        if (faculty.getId() != null) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return facultyRepository.save(faculty);
    }

    @Transactional
    @Override
    public Faculty update(Faculty faculty) {
        Long id = faculty.getId();
        if (id == null) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        checkIsExist(id);
        return facultyRepository.save(faculty);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        checkIsEmpty(id);
        facultyRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        checkIsExist(id);
        facultyRepository.undelete(id);
    }

    @Override
    public boolean isUniqueName(String name, Long id) {
        return isNew(id)
                ? !facultyRepository.existsByName(name)
                : !facultyRepository.existsByNameAndIdNot(name, id);
    }

    private boolean isNew(Long id) {
        return Objects.isNull(id);
    }

    private static FacultyDTO convertToDto(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setName(faculty.getName());
        facultyDTO.setDescription(faculty.getDescription());
        facultyDTO.setDeleted(faculty.isDeleted());
        return facultyDTO;
    }

    private Faculty convertToEntity(FacultyDTO facultyDTO) {
        Faculty faculty = new Faculty();
        if (facultyDTO.getId() != null) {
            faculty = getById(facultyDTO.getId());
        }
        faculty.setName(facultyDTO.getName());
        faculty.setDescription(facultyDTO.getDescription());
        return faculty;
    }

    private void checkIsExist(Long id) {
        if (!existsFacultyById(id)) {
            String msg = format("Faculty with id '%s' not found!", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private void checkIsEmpty(Long id) {
        if (departmentRepository.existsDepartmentByFacultyId(id)) {
            throw new UnitNotEmptyException("Cannot delete not empty Faculty!");
        }
    }
}
