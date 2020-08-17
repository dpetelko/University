package com.petelko.university.controller.rest;

import com.petelko.university.controller.dto.LessonFilterDTO;
import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.model.dto.SubjectDTO;
import com.petelko.university.service.LessonService;
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
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Lesson controller", description = "REST APIs related to Lesson Entity")
@RestController
@RequestMapping("/api")
public class LessonRestController {

    private final LessonService lessonService;

    @Autowired
    public LessonRestController( LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @ApiOperation(value = "${description.operation.lessons.getlessons}",
            responseContainer = "List",
            response = LessonDTO.class)
    @GetMapping("/lessons")
    public List<LessonDTO> getReport(@Valid @RequestBody LessonFilterDTO lessonFilter) {
        if (lessonFilter.isEmpty()) {
            lessonFilter = new LessonFilterDTO(LocalDate.now(), null, null, null);
        }
        return lessonService.getLessons(lessonFilter);
    }

    @ApiOperation(value = "${description.operation.lessons.getall.active}",
            responseContainer = "List",
            response = LessonDTO.class)
    @GetMapping("/lessons/active")
    public List<LessonDTO> getAllActive() {
        return lessonService.getAllActiveDTO();
    }

    @ApiOperation(value = "${description.operation.lessons.create}", response = SubjectDTO.class)
    @PostMapping("/lessons")
    public ResponseEntity<LessonDTO> create(@Valid @RequestBody LessonDTO lessonDTO) {
        return new ResponseEntity<>(lessonService.create(lessonDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.lessons.getbyid}", response = LessonDTO.class)
    @GetMapping("/lessons/{id}")
    public ResponseEntity<LessonDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(lessonService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.lessons.update}", response = LessonDTO.class)
    @PutMapping("/lessons")
    public ResponseEntity<LessonDTO> update(@Valid @RequestBody LessonDTO lessonDTO) {
        return new ResponseEntity<>(lessonService.update(lessonDTO), OK);
    }

    @ApiOperation(value = "${description.operation.lessons.delete}", response = HttpStatus.class)
    @GetMapping("/lessons/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        lessonService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.lessons.undelete}", response = HttpStatus.class)
    @GetMapping("/lessons/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        lessonService.undelete(id);
        return OK;
    }
}
