package com.petelko.university.controller.formatter;

import com.petelko.university.model.dto.GroupDTO;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class GroupDTOFormatter implements Formatter<GroupDTO> {

    @Override
    public GroupDTO parse(String id, Locale locale) {
        GroupDTO groupDto = new GroupDTO();
        groupDto.setId(Long.parseLong(id));
        return groupDto;
    }

    @Override
    public String print(GroupDTO object, Locale locale) {
        return object.getId().toString();
    }
}
