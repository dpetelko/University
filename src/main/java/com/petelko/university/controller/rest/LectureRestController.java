package com.petelko.university.controller.rest;

import com.petelko.university.model.dto.LectureDTO;
import com.petelko.university.service.LectureService;
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

@Api(tags = "Lectures controller", description = "REST APIs related to Lecture Entity")
@RestController
@RequestMapping("/api")
public class LectureRestController {

    private final LectureService lectureService;

    @Autowired
    public LectureRestController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @ApiOperation(value = "${description.operation.lectures.getall}",
            responseContainer = "List",
            response = LectureDTO.class)
    @GetMapping("/lectures")
    public List<LectureDTO> getAll() {
        return lectureService.getAllDTO();
    }

    @ApiOperation(value = "${description.operation.lectures.getall.active}",
            responseContainer = "List",
            response = LectureDTO.class)
    @GetMapping("/lectures/active")
    public List<LectureDTO> getAllActive() {
        return lectureService.getAllActiveDTO();
    }

    @ApiOperation(value = "${description.operation.lectures.create}", response = LectureDTO.class)
    @PostMapping("/lectures")
    public ResponseEntity<LectureDTO> create(@Valid @RequestBody LectureDTO lectureDTO) {
        return new ResponseEntity<>(lectureService.create(lectureDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.lectures.getbyid}", response = LectureDTO.class)
    @GetMapping("/lectures/{id}")
    public ResponseEntity<LectureDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(lectureService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.lectures.update}", response = LectureDTO.class)
    @PutMapping("/lectures")
    public ResponseEntity<LectureDTO> update(@Valid @RequestBody LectureDTO lectureDTO) {
        return new ResponseEntity<>(lectureService.update(lectureDTO), OK);
    }

    @ApiOperation(value = "${description.operation.lectures.delete}", response = HttpStatus.class)
    @GetMapping("/lectures/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        lectureService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.lectures.undelete}", response = HttpStatus.class)
    @GetMapping("/lectures/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        lectureService.undelete(id);
        return OK;
    }
}
