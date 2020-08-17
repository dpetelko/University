package com.petelko.university.expectedstorage;

import com.petelko.university.model.Auditory;
import com.petelko.university.model.dto.AuditoryDTO;

import java.util.ArrayList;
import java.util.List;

public class ExpectedAuditoryStorage {

    public List<AuditoryDTO> getExpectedAuditoryDTOList() {
        List<AuditoryDTO> auditoryList = new ArrayList<>();
        auditoryList.add(new AuditoryDTO(1L, "AAA", "Large auditory", 200));
        auditoryList.add(new AuditoryDTO(2L, "BBB", "Small auditory", 50));
        return auditoryList;
    }

    public List<AuditoryDTO> getExpectedActiveAuditoryDTOList() {
        List<AuditoryDTO> auditoryList = new ArrayList<>();
        auditoryList.add(new AuditoryDTO(1L, "AAA", "Large auditory", 200));
        return auditoryList;
    }

    public AuditoryDTO getExpectedAuditoryDTO() {
        return new AuditoryDTO(1L, "AAA", "Large auditory", 200);
    }

    public Auditory getAuditoryForCreate() {
        return new Auditory("CCC", "Middle auditory", 100);
    }

    public Auditory getCreatedAuditory() {
        return new Auditory(1L, "CCC", "Middle auditory", 100);
    }

    public Auditory getUpdatedAuditory() {
        return new Auditory(1L, "CCC", "Middle auditory", 100);
    }
}
