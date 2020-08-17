package com.petelko.university.service;

import com.petelko.university.model.Group;
import com.petelko.university.model.dto.GroupDTO;

import java.util.List;

public interface GroupService extends Service<Group> {
    List<GroupDTO> getAllDTO();
    GroupDTO getDTOById(Long id);
    GroupDTO create(GroupDTO group);
    GroupDTO update(GroupDTO group);
    List<GroupDTO> getAllActiveDTO();
    GroupDTO getDTOByName(String name);
    boolean existsGroupById(Long id);
    String getGroupNameById(Long id);
    boolean isUniqueName(String name, Long id);
}
