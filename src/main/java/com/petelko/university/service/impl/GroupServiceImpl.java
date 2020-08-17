package com.petelko.university.service.impl;

import com.petelko.university.model.Department;
import com.petelko.university.model.Group;
import com.petelko.university.model.Group_;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.repository.GroupRepository;
import com.petelko.university.repository.StudentRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.GroupService;
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
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private DepartmentService departmentService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            StudentRepository studentRepository,
                            DepartmentService departmentService) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
    }

    @Override
    public Group getById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        format("Group with id '%s' not found", id)));
    }

    @Override
    public GroupDTO getDTOById(Long id) {
        return groupRepository.findDTOById(id);
    }

    @Override
    public List<Group> getAll() {
        Sort sortOrder = Sort.by(Group_.NAME);
        return groupRepository.findAll(sortOrder);
    }

    @Override
    public List<GroupDTO> getAllDTO() {
        return groupRepository.findAllDTO();
    }

    @Override
    public List<GroupDTO> getAllActiveDTO() {
        return groupRepository.findAllActiveDTO();
    }

    @Override
    public GroupDTO getDTOByName(String name) {
        return groupRepository.findDTOByName(name);
    }

    @Override
    public boolean existsGroupById(Long id) {
        return groupRepository.existsGroupById(id);
    }

    @Override
    public String getGroupNameById(Long id) {
        return groupRepository.findNameById(id).getName();
    }

    @Transactional
    @Override
    public Group create(Group group) {
        if (group.getId() != null) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return groupRepository.save(group);
    }

    @Transactional
    @Override
    public GroupDTO create(GroupDTO groupDto) {
        Group group = convertToEntity(groupDto);
        group = create(group);
        return convertToDto(group);

    }

    @Transactional
    @Override
    public Group update(Group group) {
        Long id = group.getId();
        if (id == null) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        checkIsExist(id);
        return groupRepository.save(group);
    }

    @Transactional
    @Override
    public GroupDTO update(GroupDTO groupDto) {
        Group group = convertToEntity(groupDto);
        group = update(group);
        return convertToDto(group);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        checkIsEmpty(id);
        groupRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        checkIsExist(id);
        groupRepository.undelete(id);
    }

    @Override
    public boolean isUniqueName(String name, Long id) {
        return isNew(id)
                ? !groupRepository.existsByName(name)
                : !groupRepository.existsByNameAndIdNot(name, id);
    }

    private boolean isNew(Long id) {
        return Objects.isNull(id);
    }

    public GroupDTO convertToDto(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setDeleted(group.isDeleted());
        if (group.getDepartment() != null) {
            groupDTO.setDepartmentId(group.getDepartment().getId());
            groupDTO.setDepartmentName(group.getDepartment().getName());
        }
        return groupDTO;
    }

    public Group convertToEntity(GroupDTO groupDto) {
        Group group = new Group();
        if (groupDto.getId() != null) {
            group = getById(groupDto.getId());
        }
        group.setName(groupDto.getName());
        if (groupDto.getDepartmentId() != null) {
            Department department = departmentService.getById(groupDto.getDepartmentId());
            group.setDepartment(department);
        } else {
            group.setDepartment(null);
        }
        return group;
    }

    private void checkIsEmpty(Long id) {
        if (studentRepository.existsStudentByGroupId(id)) {
            throw new UnitNotEmptyException("Cannot delete not empty group!");
        }
    }

    private void checkIsExist(Long id) {
        if (!existsGroupById(id)) {
            String msg = format("Group with id '%s' not found!", id);
            throw new EntityNotFoundException(msg);
        }
    }
}
