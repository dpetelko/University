package com.petelko.university.controller.rest;

import com.petelko.university.model.dto.AuditoryDTO;
import com.petelko.university.service.AuditoryService;
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

@Api(tags = "Auditory controller", description = "REST APIs related to Auditory Entity")
@RestController
@RequestMapping("/api")
public class AuditoryRestController {

    private final AuditoryService auditoryService;

    @Autowired
    public AuditoryRestController(AuditoryService auditoryService) {
        this.auditoryService = auditoryService;
    }

    @ApiOperation(value = "${description.operation.auditories.getall}",
            responseContainer = "List",
            response = AuditoryDTO.class)
    @GetMapping("/auditories")
    public List<AuditoryDTO> getAll() {
        return auditoryService.getAllDTO();
    }

    @ApiOperation(value = "${description.operation.auditories.getall.active}",
            responseContainer = "List",
            response = AuditoryDTO.class)
    @GetMapping("/auditories/active")
    public List<AuditoryDTO> getAllActive() {
        return auditoryService.getAllActiveDTO();
    }

    @ApiOperation(value = "${description.operation.auditories.create}", response = AuditoryDTO.class)
    @PostMapping("/auditories")
    public ResponseEntity<AuditoryDTO> create(@Valid @RequestBody AuditoryDTO auditoryDTO) {
        return new ResponseEntity<>(auditoryService.create(auditoryDTO), CREATED);
    }

    @ApiOperation(value = "${description.operation.auditories.getbyid}", response = AuditoryDTO.class)
    @GetMapping("/auditories/{id}")
    public ResponseEntity<AuditoryDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(auditoryService.getDTOById(id), OK);
    }

    @ApiOperation(value = "${description.operation.auditories.update}", response = AuditoryDTO.class)
    @PutMapping("/auditories")
    public ResponseEntity<AuditoryDTO> update(@Valid @RequestBody AuditoryDTO auditoryDTO) {
        return new ResponseEntity<>(auditoryService.update(auditoryDTO), OK);
    }

    @ApiOperation(value = "${description.operation.auditories.delete}", response = HttpStatus.class)
    @GetMapping("/auditories/delete/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long id) {
        auditoryService.delete(id);
        return OK;
    }

    @ApiOperation(value = "${description.operation.auditories.undelete}", response = HttpStatus.class)
    @GetMapping("/auditories/undelete/{id}")
    public HttpStatus undelete(@PathVariable(value = "id") Long id) {
        auditoryService.undelete(id);
        return OK;
    }
}
