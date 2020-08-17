package com.petelko.university.controller.rest;

import com.petelko.university.model.dto.SubjectDTO;
import com.petelko.university.service.SubjectService;
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

@Api(tags = "Subject controller", description = "REST APIs related to Subject Entity")
@RestController
@RequestMapping("/api")
public class SubjectRestController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectRestController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @ApiOperation(value = "${description.operation.subjects.getall}",
            responseContainer = "List",
            response = SubjectDTO.class)
    @GetMapping("/subjects")
    public List<SubjectDTO> getAll() {
        return subjectService.getAllDTO();
    }

    @ApiOperation(value = "${description.operation.subjects.getall.active}",
            responseContainer = "List",
            response = SubjectDTO.class)
    @GetMapping("/subjects/active")
    public List<SubjectDTO> getAllActive() {
        return subjectService.getAllActiveDTO();
    }

    @ApiOperation(value = "${description.operation.subjects.create}", response = SubjectDTO.class)
    @PostMapping("/subjects")
    public ResponseEntity<SubjectDTO> create(@Valid @RequestBody SubjectDTO subjectDTO) {
        return new ResponseEntity<>(subjectService.create(subjectDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.subjects.getbyid}", response = SubjectDTO.class)
    @GetMapping("/subjects/{id}")
    public ResponseEntity<SubjectDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(subjectService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.subjects.update}", response = SubjectDTO.class)
    @PutMapping("/subjects")
    public ResponseEntity<SubjectDTO> update(@Valid @RequestBody SubjectDTO subjectDTO) {
        return new ResponseEntity<>(subjectService.update(subjectDTO), OK);
    }

    @ApiOperation(value = "${description.operation.subjects.delete}", response = HttpStatus.class)
    @GetMapping("/subjects/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        subjectService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.subjects.undelete}", response = HttpStatus.class)
    @GetMapping("/subjects/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        subjectService.undelete(id);
        return OK;
    }
}
