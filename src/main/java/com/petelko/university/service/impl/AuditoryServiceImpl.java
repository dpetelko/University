package com.petelko.university.service.impl;

import com.petelko.university.model.Auditory;
import com.petelko.university.model.Auditory_;
import com.petelko.university.model.dto.AuditoryDTO;
import com.petelko.university.repository.AuditoryRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.AuditoryService;
import com.petelko.university.service.exception.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
public class AuditoryServiceImpl implements AuditoryService {

    private AuditoryRepository auditoryRepository;

    @Autowired
    public AuditoryServiceImpl(AuditoryRepository auditoryRepository) {
        this.auditoryRepository = auditoryRepository;
    }

    @Override
    public List<AuditoryDTO> getAllDTO() {
        return auditoryRepository.findAllDTO();
    }

    @Override
    public List<AuditoryDTO> getAllActiveDTO() {
        return auditoryRepository.findAllActiveDTO();
    }

    @Override
    public AuditoryDTO getDTOById(Long id) {
        return auditoryRepository.findDTOById(id);
    }

    @Override
    public AuditoryDTO getDTOByName(String name) {
        return auditoryRepository.findDTOByName(name);
    }

    @Transactional
    @Override
    public AuditoryDTO create(AuditoryDTO auditoryDto) {
        Auditory auditory = convertToEntity(auditoryDto);
        auditory = create(auditory);
        return convertToDto(auditory);
    }

    @Transactional
    @Override
    public AuditoryDTO update(AuditoryDTO auditoryDto) {
        Auditory auditory = convertToEntity(auditoryDto);
        auditory = update(auditory);
        return convertToDto(auditory);
    }

    @Override
    public boolean existsAuditoryById(Long id) {
        return auditoryRepository.existsAuditoryById(id);
    }

    @Override
    public Auditory getById(Long id) {
        return auditoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Auditory with id '%s' not found", id)));
    }

    @Override
    public List<Auditory> getAll() {
        Sort nameSort = Sort.by(Auditory_.NAME);
        Sort sortOrder = nameSort.ascending();
        return auditoryRepository.findAll(sortOrder);
    }

    @Transactional
    @Override
    public Auditory create(Auditory auditory) {
        if (auditory.getId() != null) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return auditoryRepository.save(auditory);
    }

    @Transactional
    @Override
    public Auditory update(Auditory auditory) {
        Long id = auditory.getId();
        if (id == null) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        checkIsExist(id);
        return auditoryRepository.save(auditory);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        auditoryRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        checkIsExist(id);
        auditoryRepository.undelete(id);
    }

    @Override
    public boolean isUniqueName(String name, Long id) {
        return isNew(id)
                ? !auditoryRepository.existsByName(name)
                : !auditoryRepository.existsByNameAndIdNot(name, id);
    }

    private boolean isNew(Long id) {
        return Objects.isNull(id);
    }

    private static AuditoryDTO convertToDto(Auditory auditory) {
        AuditoryDTO auditoryDTO = new AuditoryDTO();
        auditoryDTO.setId(auditory.getId());
        auditoryDTO.setName(auditory.getName());
        auditoryDTO.setDescription(auditory.getDescription());
        auditoryDTO.setCapacity(auditory.getCapacity());
        auditoryDTO.setDeleted(auditory.isDeleted());
        return auditoryDTO;
    }

    private Auditory convertToEntity(AuditoryDTO auditoryDTO) {
        Auditory auditory = new Auditory();
        if (auditoryDTO.getId() != null) {
            auditory = getById(auditoryDTO.getId());
        }
        auditory.setName(auditoryDTO.getName());
        auditory.setDescription(auditoryDTO.getDescription());
        auditory.setCapacity(auditoryDTO.getCapacity());
        return auditory;
    }

    private void checkIsExist(Long id) {
        if (!existsAuditoryById(id)) {
            String msg = format("Auditory with id '%s' not found for undelete!", id);
            throw new EntityNotFoundException(msg);
        }
    }
}
