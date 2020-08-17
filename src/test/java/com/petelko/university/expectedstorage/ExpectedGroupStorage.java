package com.petelko.university.expectedstorage;

import com.petelko.university.model.Group;
import com.petelko.university.model.dto.GroupDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpectedGroupStorage {
    public Optional<Group> getExpectedGroup() {
        return Optional.of(new Group(1L, "AA-11"));
    }

    public GroupDTO getExpectedGroupDTO() {
        return new GroupDTO(1L, "AA-11");
    }

    public List<Group> getExpectedGroupsList() {
        List<Group> groupsList = new ArrayList<>();
        groupsList.add(new Group(1L, "AA-11"));
        groupsList.add(new Group(2L, "BB-22"));
        groupsList.add(new Group(3L, "CC-33"));
        return groupsList;
    }

    public List<GroupDTO> getExpectedGroupDTOList() {
        List<GroupDTO> groupsList = new ArrayList<>();
        groupsList.add(new GroupDTO(1L, "AA-11"));
        groupsList.add(new GroupDTO(2L, "BB-22"));
        groupsList.add(new GroupDTO(3L, "CC-33"));
        return groupsList;
    }

    public Group getGroupForCreate() {
        return new Group("AA-11");
    }

    public Group getCreatedGroup() {
        return new Group(1L, "AA-11");
    }

    public Group getUpdatedGroup() {
        return new Group(2L, "XX-99");
    }

    public Group getGroupForDelete() {
        return new Group(3L, "CC-33");
    }
}
