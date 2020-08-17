package com.petelko.university.repository.custom.impl;

import com.petelko.university.model.Auditory;
import com.petelko.university.model.Auditory_;
import com.petelko.university.model.dto.AuditoryDTO;
import com.petelko.university.repository.custom.CustomizedAuditoryRepository;
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

public class CustomizedAuditoryRepositoryImpl implements CustomizedAuditoryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedAuditoryRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<AuditoryDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AuditoryDTO> query = cb.createQuery(AuditoryDTO.class);
        Root<Auditory> root = query.from(Auditory.class);
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<AuditoryDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' Auditories", result.size());
        return result;
    }

    @Override
    public List<AuditoryDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AuditoryDTO> query = cb.createQuery(AuditoryDTO.class);
        Root<Auditory> root = query.from(Auditory.class);
        query.where(cb.isFalse(root.get(Auditory_.deleted)));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<AuditoryDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' active Auditories", result.size());
        return result;
    }

    @Override
    public AuditoryDTO findDTOById(Long id) {
        LOGGER.debug("getDTObyId()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AuditoryDTO> query = cb.createQuery(AuditoryDTO.class);
        Root<Auditory> root = query.from(Auditory.class);
        query.where(cb.equal(root.get(Auditory_.id), id));
        query.multiselect(buildMultiSelect(root));
        AuditoryDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Auditory with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }

    @Override
    public AuditoryDTO findDTOByName(String name) {
        LOGGER.debug("findDTOByName()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AuditoryDTO> query = cb.createQuery(AuditoryDTO.class);
        Root<Auditory> root = query.from(Auditory.class);
        query.where(cb.equal(root.get(Auditory_.name), name));
        query.multiselect(buildMultiSelect(root));
        AuditoryDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Auditory with Name '%s' not found", name);
            throw new EntityNotFoundException(msg);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Auditory> update = cb.createCriteriaUpdate(Auditory.class);
        Root<Auditory> root = update.from(Auditory.class);
        update.set(root.get(Auditory_.deleted), true)
                .where(cb.equal(root.get(Auditory_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Auditory with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Auditory with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Auditory> update = cb.createCriteriaUpdate(Auditory.class);
        Root<Auditory> root = update.from(Auditory.class);
        int result = 0;
        update.set(root.get(Auditory_.deleted), false)
                .where(cb.equal(root.get(Auditory_.id), id));
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Auditory with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Auditory with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private List<Selection<?>> buildMultiSelect(Root<Auditory> root) {
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Auditory_.id));
        selectList.add(root.get(Auditory_.name));
        selectList.add(root.get(Auditory_.description));
        selectList.add(root.get(Auditory_.capacity));
        selectList.add(root.get(Auditory_.deleted));
        return selectList;
    }

    private List<Order> getOrderList(CriteriaBuilder cb, Root<Auditory> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Auditory_.NAME)));
        return orderList;
    }
}
