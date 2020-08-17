package com.petelko.university.controller.rest;

import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.service.StudentService;
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

@Api(tags = "Student controller", description = "REST APIs related to Student Entity")
@RestController
@RequestMapping("/api")
public class StudentRestController {

    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation(value = "${description.operation.students.getall}",
            responseContainer = "List",
            response = StudentDTO.class)
    @GetMapping("/students")
    public List<StudentDTO> getAll() {
        return studentService.getAllDTO();
    }

    @ApiOperation(value = "${description.operation.students.create}", response = StudentDTO.class)
    @PostMapping("/students")
    public ResponseEntity<StudentDTO> create(@Valid @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.create(studentDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.students.getbyid}", response = StudentDTO.class)
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(studentService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.students.update}", response = StudentDTO.class)
    @PutMapping("/students")
    public ResponseEntity<StudentDTO> update(@Valid @RequestBody StudentDTO student) {
        return new ResponseEntity<>(studentService.update(student), OK);
    }

    @ApiOperation(value = "${description.operation.students.delete}", response = HttpStatus.class)
    @GetMapping("/students/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        studentService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.students.undelete}", response = HttpStatus.class)
    @GetMapping("/students/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        studentService.undelete(id);
        return OK;
    }
}
