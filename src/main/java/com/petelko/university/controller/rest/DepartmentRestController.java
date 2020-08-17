package com.petelko.university.controller.rest;

import com.petelko.university.model.dto.DepartmentDTO;
import com.petelko.university.service.DepartmentService;
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

@Api(tags = "Department controller", description = "REST APIs related to Department Entity")
@RestController
@RequestMapping("/api")
public class DepartmentRestController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentRestController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "${description.operation.departments.getall}",
            responseContainer = "List",
            response = DepartmentDTO.class)
    @GetMapping("/departments")
    public List<DepartmentDTO> getAll() {
        return departmentService.getAllDTO();
    }

    @ApiOperation(value = "${description.operation.departments.getall.active}",
            responseContainer = "List",
            response = DepartmentDTO.class)
    @GetMapping("/departments/active")
    public List<DepartmentDTO> getAllActive() {
        return departmentService.getAllActiveDTO();
    }

    @ApiOperation(value = "${description.operation.departments.create}", response = DepartmentDTO.class)
    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> create(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.create(departmentDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.departments.getbyid}", response = DepartmentDTO.class)
    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(departmentService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.departments.update}", response = DepartmentDTO.class)
    @PutMapping("/departments")
    public ResponseEntity<DepartmentDTO> update(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.update(departmentDTO), OK);
    }

    @ApiOperation(value = "${description.operation.departments.delete}", response = HttpStatus.class)
    @GetMapping("/departments/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        departmentService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.departments.undelete}", response = HttpStatus.class)
    @GetMapping("/departments/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        departmentService.undelete(id);
        return OK;
    }
}
