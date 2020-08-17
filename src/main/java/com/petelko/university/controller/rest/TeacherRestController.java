package com.petelko.university.controller.rest;

import com.petelko.university.model.dto.SubjectDTO;
import com.petelko.university.model.dto.TeacherDTO;
import com.petelko.university.service.TeacherService;
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

@Api(tags = "Teacher controller", description = "REST APIs related to Teacher Entity")
@RestController
@RequestMapping("/api")
public class TeacherRestController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation(value = "${description.operation.teachers.getall}",
            responseContainer = "List",
            response = TeacherDTO.class)
    @GetMapping("/teachers")
    public List<TeacherDTO> getAll() {
        return teacherService.getAllDTO();
    }

    @ApiOperation(value = "${description.operation.teachers.getall.active}",
            responseContainer = "List",
            response = TeacherDTO.class)
    @GetMapping("/teachers/active")
    public List<TeacherDTO> getAllActive() {
        return teacherService.getAllActiveDTO();
    }

    @ApiOperation(value = "${description.operation.teachers.create}", response = SubjectDTO.class)
    @PostMapping("/teachers")
    public ResponseEntity<TeacherDTO> create(@Valid @RequestBody TeacherDTO teacherDTO) {
        return new ResponseEntity<>(teacherService.create(teacherDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.teachers.getbyid}", response = TeacherDTO.class)
    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(teacherService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.teachers.update}", response = TeacherDTO.class)
    @PutMapping("/teachers")
    public ResponseEntity<TeacherDTO> update(@Valid @RequestBody TeacherDTO teacherDTO) {
        return new ResponseEntity<>(teacherService.update(teacherDTO), OK);
    }

    @ApiOperation(value = "${description.operation.teachers.delete}", response = HttpStatus.class)
    @GetMapping("/teachers/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        teacherService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.teachers.undelete}", response = HttpStatus.class)
    @GetMapping("/teachers/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        teacherService.undelete(id);
        return OK;
    }
}
