package com.petelko.university.repository.custom.impl;

import com.petelko.university.model.Faculty;
import com.petelko.university.model.Faculty_;
import com.petelko.university.model.dto.FacultyDTO;
import com.petelko.university.repository.custom.CustomizedFacultyRepository;
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

public class CustomizedFacultyRepositoryImpl implements CustomizedFacultyRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedFacultyRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<FacultyDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FacultyDTO> query = cb.createQuery(FacultyDTO.class);
        Root<Faculty> root = query.from(Faculty.class);
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<FacultyDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' Faculties", result.size());
        return result;
    }

    @Override
    public List<FacultyDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FacultyDTO> query = cb.createQuery(FacultyDTO.class);
        Root<Faculty> root = query.from(Faculty.class);
        query.where(cb.isFalse(root.get(Faculty_.deleted)));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<FacultyDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' active Faculties", result.size());
        return result;
    }

    @Override
    public FacultyDTO findDTOById(Long id) {
        LOGGER.debug("getDTObyId()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FacultyDTO> query = cb.createQuery(FacultyDTO.class);
        Root<Faculty> root = query.from(Faculty.class);
        query.where(cb.equal(root.get(Faculty_.id), id));
        query.multiselect(buildMultiSelect(root));
        FacultyDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Faculty with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }

    @Override
    public FacultyDTO findDTOByName(String name) {
        LOGGER.debug("findDTOByName()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FacultyDTO> query = cb.createQuery(FacultyDTO.class);
        Root<Faculty> root = query.from(Faculty.class);
        query.where(cb.equal(root.get(Faculty_.name), name));
        query.multiselect(buildMultiSelect(root));
        FacultyDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Faculty with Name '%s' not found", name);
            throw new EntityNotFoundException(msg);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Faculty> update = cb.createCriteriaUpdate(Faculty.class);
        Root<Faculty> root = update.from(Faculty.class);
        update.set(root.get(Faculty_.deleted), true)
                .where(cb.equal(root.get(Faculty_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Faculty with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Faculty with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Faculty> update = cb.createCriteriaUpdate(Faculty.class);
        Root<Faculty> root = update.from(Faculty.class);
        int result = 0;
        update.set(root.get(Faculty_.deleted), false)
                .where(cb.equal(root.get(Faculty_.id), id));
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Faculty with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Faculty with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private List<Selection<?>> buildMultiSelect(Root<Faculty> root) {
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Faculty_.id));
        selectList.add(root.get(Faculty_.name));
        selectList.add(root.get(Faculty_.description));
        selectList.add(root.get(Faculty_.deleted));
        return selectList;
    }

    private List<Order> getOrderList(CriteriaBuilder cb, Root<Faculty> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Faculty_.NAME)));
        return orderList;
    }
}
