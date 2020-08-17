package com.petelko.university.controller.rest;

import com.petelko.university.model.dto.FacultyDTO;
import com.petelko.university.service.FacultyService;
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

@Api(tags = "Faculties controller", description = "REST APIs related to Faculties Entity")
@RestController
@RequestMapping("/api")
public class FacultyRestController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyRestController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @ApiOperation(value = "${description.operation.faculties.getall}",
            responseContainer = "List",
            response = FacultyDTO.class)
    @GetMapping("/faculties")
    public List<FacultyDTO> getAll() {
        return facultyService.getAllDTO();
    }

    @ApiOperation(value = "${description.operation.faculties.getall.active}",
            responseContainer = "List",
            response = FacultyDTO.class)
    @GetMapping("/faculties/active")
    public List<FacultyDTO> getAllActive() {
        return facultyService.getAllActiveDTO();
    }

    @ApiOperation(value = "${description.operation.faculties.create}", response = FacultyDTO.class)
    @PostMapping("/faculties")
    public ResponseEntity<FacultyDTO> create(@Valid @RequestBody FacultyDTO facultyDTO) {
        return new ResponseEntity<>(facultyService.create(facultyDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.faculties.getbyid}", response = FacultyDTO.class)
    @GetMapping("/faculties/{id}")
    public ResponseEntity<FacultyDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(facultyService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.faculties.update}", response = FacultyDTO.class)
    @PutMapping("/faculties")
    public ResponseEntity<FacultyDTO> update(@Valid @RequestBody FacultyDTO facultyDTO) {
        return new ResponseEntity<>(facultyService.update(facultyDTO), OK);
    }

    @ApiOperation(value = "${description.operation.faculties.delete}", response = HttpStatus.class)
    @GetMapping("/faculties/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        facultyService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.faculties.undelete}", response = HttpStatus.class)
    @GetMapping("/faculties/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        facultyService.undelete(id);
        return OK;
    }
}
