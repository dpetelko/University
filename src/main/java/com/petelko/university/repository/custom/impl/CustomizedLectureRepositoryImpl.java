package com.petelko.university.repository.custom.impl;

import com.petelko.university.model.Lecture;
import com.petelko.university.model.Lecture_;
import com.petelko.university.model.dto.LectureDTO;
import com.petelko.university.repository.custom.CustomizedLectureRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.repository.exception.QueryNotExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class CustomizedLectureRepositoryImpl implements CustomizedLectureRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedLectureRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<LectureDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LectureDTO> query = cb.createQuery(LectureDTO.class);
        Root<Lecture> root = query.from(Lecture.class);
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<LectureDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' Lectures", result.size());
        return result;
    }

    @Override
    public List<LectureDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LectureDTO> query = cb.createQuery(LectureDTO.class);
        Root<Lecture> root = query.from(Lecture.class);
        query.where(cb.isFalse(root.get(Lecture_.deleted)));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<LectureDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' active Lectures", result.size());
        return result;
    }

    @Override
    public LectureDTO findDTOById(Long id) {
        LOGGER.debug("getDTObyId()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LectureDTO> query = cb.createQuery(LectureDTO.class);
        Root<Lecture> root = query.from(Lecture.class);
        query.where(cb.equal(root.get(Lecture_.id), id));
        query.multiselect(buildMultiSelect(root));
        LectureDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Lecture with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }

    @Override
    public LectureDTO findDTOByName(String name) {
        LOGGER.debug("findDTOByName()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LectureDTO> query = cb.createQuery(LectureDTO.class);
        Root<Lecture> root = query.from(Lecture.class);
        query.where(cb.equal(root.get(Lecture_.name), name));
        query.multiselect(buildMultiSelect(root));
        LectureDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Lecture with Name '%s' not found", name);
            throw new EntityNotFoundException(msg);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Lecture> update = cb.createCriteriaUpdate(Lecture.class);
        Root<Lecture> root = update.from(Lecture.class);
        update.set(root.get(Lecture_.deleted), true)
                .where(cb.equal(root.get(Lecture_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Lecture with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Lecture with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Lecture> update = cb.createCriteriaUpdate(Lecture.class);
        Root<Lecture> root = update.from(Lecture.class);
        int result = 0;
        update.set(root.get(Lecture_.deleted), false)
                .where(cb.equal(root.get(Lecture_.id), id));
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Lecture with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Lecture with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private List<Selection<?>>  buildMultiSelect(Root<Lecture> root) {
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Lecture_.id));
        selectList.add(root.get(Lecture_.name));
        selectList.add(root.get(Lecture_.startTime));
        selectList.add(root.get(Lecture_.endTime));
        selectList.add(root.get(Lecture_.deleted));
        return selectList;
    }

    private List<Order> getOrderList(CriteriaBuilder cb, Root<Lecture> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Lecture_.NAME)));
        return orderList;
    }
}
