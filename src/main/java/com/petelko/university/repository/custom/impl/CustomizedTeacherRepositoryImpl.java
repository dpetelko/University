package com.petelko.university.repository.custom.impl;


import com.petelko.university.model.Department;
import com.petelko.university.model.Department_;
import com.petelko.university.model.Subject;
import com.petelko.university.model.Subject_;
import com.petelko.university.model.Teacher;
import com.petelko.university.model.Teacher_;
import com.petelko.university.model.dto.TeacherDTO;
import com.petelko.university.repository.custom.CustomizedTeacherRepository;
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

public class CustomizedTeacherRepositoryImpl implements CustomizedTeacherRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedTeacherRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TeacherDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TeacherDTO> query = cb.createQuery(TeacherDTO.class);
        Root<Teacher> root = query.from(Teacher.class);
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<TeacherDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' teachers", result.size());
        return result;
    }

    @Override
    public List<TeacherDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TeacherDTO> query = cb.createQuery(TeacherDTO.class);
        Root<Teacher> root = query.from(Teacher.class);
        query.where(cb.isFalse(root.get(Teacher_.deleted)));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<TeacherDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' active teachers", result.size());
        return result;
    }

    @Override
    public TeacherDTO findDTOById(Long id) {
        LOGGER.debug("getDTObyId()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TeacherDTO> query = cb.createQuery(TeacherDTO.class);
        Root<Teacher> root = query.from(Teacher.class);
        query.where(cb.equal(root.get(Teacher_.id), id));
        query.multiselect(buildMultiSelect(root));
        TeacherDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Teacher with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }


    @Override
    public TeacherDTO findDTOByEmail(String email) {
        LOGGER.debug("existsDTOByEmail()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TeacherDTO> query = cb.createQuery(TeacherDTO.class);
        Root<Teacher> root = query.from(Teacher.class);
        query.where(cb.equal(root.get(Teacher_.email), email));
        query.multiselect(buildMultiSelect(root));
        TeacherDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Teacher with Email '%s' not found", email);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }


    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Teacher> update = cb.createCriteriaUpdate(Teacher.class);
        Root<Teacher> root = update.from(Teacher.class);
        update.set(root.get(Teacher_.deleted), true)
                .where(cb.equal(root.get(Teacher_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Teacher with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Teacher with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Teacher> update = cb.createCriteriaUpdate(Teacher.class);
        Root<Teacher> root = update.from(Teacher.class);
        int result = 0;
        update.set(root.get(Teacher_.deleted), false)
                .where(cb.equal(root.get(Teacher_.id), id));
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Teacher with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Teacher with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private List<Selection<?>>  buildMultiSelect(Root<Teacher> root) {
        Join<Teacher, Subject> joinSubject = root.join(Teacher_.subject, JoinType.LEFT);
        Join<Teacher, Department> joinDepartment = root.join(Teacher_.department, JoinType.LEFT);
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Teacher_.id));
        selectList.add(root.get(Teacher_.firstName));
        selectList.add(root.get(Teacher_.lastName));
        selectList.add(root.get(Teacher_.deleted));
        selectList.add(root.get(Teacher_.email));
        selectList.add(root.get(Teacher_.phoneNumber));
        selectList.add(joinSubject.get(Subject_.id));
        selectList.add(joinSubject.get(Subject_.name));
        selectList.add(joinSubject.get(Subject_.description));
        selectList.add(joinDepartment.get(Department_.id));
        selectList.add(joinDepartment.get(Department_.name));
        selectList.add(joinDepartment.get(Department_.description));
        return selectList;
    }

    private List<Order> getOrderList(CriteriaBuilder cb, Root<Teacher> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Teacher_.LAST_NAME)));
        orderList.add(cb.asc(root.get(Teacher_.FIRST_NAME)));
        return orderList;
    }
}
