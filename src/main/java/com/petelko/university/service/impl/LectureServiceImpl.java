package com.petelko.university.service.impl;

import com.petelko.university.model.Lecture;
import com.petelko.university.model.Lecture_;
import com.petelko.university.model.dto.LectureDTO;
import com.petelko.university.repository.LectureRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.LectureService;
import com.petelko.university.service.exception.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
public class LectureServiceImpl implements LectureService {


    private LectureRepository lectureRepository;

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<LectureDTO> getAllDTO() {
        return lectureRepository.findAllDTO();
    }

    @Override
    public List<LectureDTO> getAllActiveDTO() {
        return lectureRepository.findAllActiveDTO();
    }

    @Override
    public LectureDTO getDTOById(Long id) {
        return lectureRepository.findDTOById(id);
    }

    @Override
    public LectureDTO getDTOByName(String name) {
        return lectureRepository.findDTOByName(name);
    }

    @Transactional
    @Override
    public LectureDTO create(LectureDTO lectureDto) {
        Lecture lecture = convertToEntity(lectureDto);
        lecture = create(lecture);
        return convertToDto(lecture);
    }

    @Transactional
    @Override
    public LectureDTO update(LectureDTO lectureDto) {
        Lecture lecture = convertToEntity(lectureDto);
        lecture = update(lecture);
        return convertToDto(lecture);
    }

    @Override
    public boolean existsLectureById(Long id) {
        return lectureRepository.existsLectureById(id);
    }

    @Override
    public Lecture getById(Long id) {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Lecture with id '%s' not found", id)));
    }

    @Override
    public List<Lecture> getAll() {
        Sort nameSort = Sort.by(Lecture_.NAME);
        Sort sortOrder = nameSort.ascending();
        return lectureRepository.findAll(sortOrder);
    }

    @Transactional
    @Override
    public Lecture create(Lecture lecture) {
        if (lecture.getId() != null) {
            throw new InvalidEntityException("Id must be NULL for create!");
        }
        return lectureRepository.save(lecture);
    }

    @Transactional
    @Override
    public Lecture update(Lecture lecture) {
        Long id = lecture.getId();
        if (id == null) {
            throw new InvalidEntityException("Id cannot be NULL for update!");
        }
        checkIsExist(id);
        return lectureRepository.save(lecture);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        lectureRepository.delete(id);
    }

    @Transactional
    @Override
    public void undelete(Long id) {
        checkIsExist(id);
        lectureRepository.undelete(id);
    }

    @Override
    public boolean isUniqueName(String name, Long id) {
        return isNew(id)
                ? !lectureRepository.existsByName(name)
                : !lectureRepository.existsByNameAndIdNot(name, id);
    }

    private boolean isNew(Long id) {
        return Objects.isNull(id);
    }

    private static LectureDTO convertToDto(Lecture lecture) {
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setId(lecture.getId());
        lectureDTO.setName(lecture.getName());
        lectureDTO.setStartTime(lecture.getStartTime());
        lectureDTO.setEndTime(lecture.getEndTime());
        lectureDTO.setDeleted(lecture.isDeleted());
        return lectureDTO;
    }

    private Lecture convertToEntity(LectureDTO lectureDTO) {
        Lecture lecture = new Lecture();
        if (lectureDTO.getId() != null) {
            lecture = getById(lectureDTO.getId());
        }
        lecture.setName(lectureDTO.getName());
        lecture.setStartTime(lectureDTO.getStartTime());
        lecture.setEndTime(lectureDTO.getEndTime());
        return lecture;
    }

    private void checkIsExist(Long id) {
        if (!existsLectureById(id)) {
            String msg = format("Lecture with id '%s' not found for delete!", id);
            throw new EntityNotFoundException(msg);
        }
    }
}
