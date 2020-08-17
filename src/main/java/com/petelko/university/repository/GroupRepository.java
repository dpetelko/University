package com.petelko.university.repository;

import com.petelko.university.repository.projections.GroupName;
import org.springframework.stereotype.Repository;

import com.petelko.university.model.Group;
import com.petelko.university.repository.custom.CustomizedGroupRepository;

@Repository
public interface GroupRepository extends GlobalRepository<Group, Long>, CustomizedGroupRepository {
    boolean existsGroupByDepartmentId(Long departmentId);
    boolean existsGroupById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    GroupName findNameById(Long id);
}
