package com.petelko.university.repository.custom.impl;

import com.petelko.university.model.Department;
import com.petelko.university.model.Department_;
import com.petelko.university.model.Subject;
import com.petelko.university.model.Subject_;
import com.petelko.university.model.dto.SubjectDTO;
import com.petelko.university.repository.custom.CustomizedSubjectRepository;
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class CustomizedSubjectRepositoryImpl implements CustomizedSubjectRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedSubjectRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SubjectDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SubjectDTO> query = cb.createQuery(SubjectDTO.class);
        Root<Subject> root = query.from(Subject.class);
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<SubjectDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' subjects", result.size());
        return result;
    }

    @Override
    public List<SubjectDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SubjectDTO> query = cb.createQuery(SubjectDTO.class);
        Root<Subject> root = query.from(Subject.class);
        query.where(cb.isFalse(root.get(Subject_.deleted)));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<SubjectDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' active subjects", result.size());
        return result;
    }

    @Override
    public SubjectDTO findDTOById(Long id) {
        LOGGER.debug("getDTObyId()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SubjectDTO> query = cb.createQuery(SubjectDTO.class);
        Root<Subject> root = query.from(Subject.class);
        query.where(cb.equal(root.get(Subject_.id), id));
        query.multiselect(buildMultiSelect(root));
        SubjectDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Subject with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }

    @Override
    public SubjectDTO findDTOByName(String name) {
        LOGGER.debug("findDTOByName()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SubjectDTO> query = cb.createQuery(SubjectDTO.class);
        Root<Subject> root = query.from(Subject.class);
        query.where(cb.equal(root.get(Subject_.name), name));
        query.multiselect(buildMultiSelect(root));
        SubjectDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Subject with Name '%s' not found", name);
            throw new EntityNotFoundException(msg);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Subject> update = cb.createCriteriaUpdate(Subject.class);
        Root<Subject> root = update.from(Subject.class);
        update.set(root.get(Subject_.deleted), true)
                .where(cb.equal(root.get(Subject_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Subject with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Subject with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Subject> update = cb.createCriteriaUpdate(Subject.class);
        Root<Subject> root = update.from(Subject.class);
        int result = 0;
        update.set(root.get(Subject_.deleted), false)
                .where(cb.equal(root.get(Subject_.id), id));
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Subject with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Subject with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private List<Selection<?>> buildMultiSelect(Root<Subject> root) {
        Join<Subject, Department> join = root.join(Subject_.department, JoinType.LEFT);
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Subject_.id));
        selectList.add(root.get(Subject_.name));
        selectList.add(root.get(Subject_.deleted));
        selectList.add(root.get(Subject_.description));
        selectList.add(join.get(Department_.id));
        selectList.add(join.get(Department_.name));
        selectList.add(join.get(Department_.description));
        return selectList;
    }

    private List<Order> getOrderList(CriteriaBuilder cb, Root<Subject> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Subject_.NAME)));
        return orderList;
    }
}
