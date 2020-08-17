package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.GroupDTO;

public interface CustomizedGroupRepository extends CustomizedRepository<GroupDTO> {
    GroupDTO findDTOByName(String name);
}
