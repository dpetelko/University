package com.petelko.university.service.validation.validator.uniquevalidator.impl;

import com.petelko.university.service.AuditoryService;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("AuditoryDTO")
public class AuditoryUniqueNameValidator implements UniqueValidator {

    private AuditoryService auditoryService;

    @Autowired
    public AuditoryUniqueNameValidator(AuditoryService auditoryService) {
        this.auditoryService = auditoryService;
    }

    @Override
    public boolean isUniqueField(String name, Long id) {
        return auditoryService.isUniqueName(name, id);
    }
}
