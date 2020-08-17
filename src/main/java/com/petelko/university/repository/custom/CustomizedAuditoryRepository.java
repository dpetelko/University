package com.petelko.university.repository.custom;

import com.petelko.university.model.dto.AuditoryDTO;

public interface CustomizedAuditoryRepository extends CustomizedRepository<AuditoryDTO> {
    AuditoryDTO findDTOByName(String name);
}
