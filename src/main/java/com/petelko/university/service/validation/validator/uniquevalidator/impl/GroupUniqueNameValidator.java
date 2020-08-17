package com.petelko.university.service.validation.validator.uniquevalidator.impl;

import com.petelko.university.service.GroupService;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("GroupDTO")
public class GroupUniqueNameValidator implements UniqueValidator {


    private GroupService groupService;

    @Autowired
    public GroupUniqueNameValidator(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public boolean isUniqueField(String name, Long id) {
        return groupService.isUniqueName(name, id);
    }
}
