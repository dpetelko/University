package com.petelko.university.service;

import com.petelko.university.model.Auditory;
import com.petelko.university.model.dto.AuditoryDTO;

import java.util.List;

public interface AuditoryService extends Service<Auditory> {
    List<AuditoryDTO> getAllDTO();
    List<AuditoryDTO> getAllActiveDTO();
    AuditoryDTO getDTOById(Long id);
    AuditoryDTO create(AuditoryDTO auditoryDto);
    AuditoryDTO update(AuditoryDTO auditoryDto);
    boolean existsAuditoryById(Long id);
    AuditoryDTO getDTOByName(String name);
    boolean isUniqueName(String name, Long id);
}
