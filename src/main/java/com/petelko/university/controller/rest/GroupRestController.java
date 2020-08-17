package com.petelko.university.controller.rest;

import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Group controller", description = "REST APIs related to Group Entity")
@RestController
@RequestMapping("/api")
public class GroupRestController {

    private final GroupService groupService;

    @Autowired
    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @ApiOperation(value = "${description.operation.groups.getall}",
            responseContainer = "List",
            response = GroupDTO.class)
    @GetMapping("/groups")
    public List<GroupDTO> getAll() {
        return groupService.getAllDTO();
    }

    @ApiOperation(value = "${description.operation.groups.getall.active}",
            responseContainer = "List",
            response = GroupDTO.class)
    @GetMapping("/groups/active")
    public List<GroupDTO> getAllActive() {
        return groupService.getAllActiveDTO();
    }

    @ApiOperation(value = "${description.operation.groups.create}", response = GroupDTO.class)
    @PostMapping("/groups")
    public ResponseEntity<GroupDTO> create(@Valid @RequestBody GroupDTO groupDTO) {
        return new ResponseEntity<>(groupService.create(groupDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.groups.getbyid}", response = GroupDTO.class)
    @GetMapping("/groups/{id}")
    public ResponseEntity<GroupDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(groupService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.groups.update}", response = GroupDTO.class)
    @PutMapping("/groups")
    public ResponseEntity<GroupDTO> update(@Valid @RequestBody GroupDTO group) {
        return new ResponseEntity<>(groupService.update(group), OK);
    }

    @ApiOperation(value = "${description.operation.groups.delete}", response = HttpStatus.class)
    @GetMapping("/groups/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        groupService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.groups.undelete}", response = HttpStatus.class)
    @GetMapping("/groups/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        groupService.undelete(id);
        return OK;
    }
}
