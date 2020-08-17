package com.petelko.university.repository.custom.impl;

import com.petelko.university.model.Group;
import com.petelko.university.model.Group_;
import com.petelko.university.model.Student;
import com.petelko.university.model.Student_;
import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.repository.custom.CustomizedStudentRepository;
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

public class CustomizedStudentRepositoryImpl implements CustomizedStudentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedStudentRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<StudentDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentDTO> query = cb.createQuery(StudentDTO.class);
        Root<Student> root = query.from(Student.class);
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<StudentDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' students", result.size());
        return result;
    }
    
    @Override
    public List<StudentDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentDTO> query = cb.createQuery(StudentDTO.class);
        Root<Student> root = query.from(Student.class);
        query.where(cb.isFalse(root.get(Student_.deleted)));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<StudentDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' active students", result.size());
        return result;
    }

    @Override
    public StudentDTO findDTOById(Long id) {
        LOGGER.debug("getDTObyId()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentDTO> query = cb.createQuery(StudentDTO.class);
        Root<Student> root = query.from(Student.class);
        query.where(cb.equal(root.get(Student_.id), id));
        query.multiselect(buildMultiSelect(root));
        StudentDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Student with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }
    
    @Override
    public StudentDTO findDTOByEmail(String email) {
        LOGGER.debug("existsDTOByEmail()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentDTO> query = cb.createQuery(StudentDTO.class);
        Root<Student> root = query.from(Student.class);
        query.where(cb.equal(root.get(Student_.email), email));
        query.multiselect(buildMultiSelect(root));
        StudentDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Student with Email '%s' not found", email);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Student> update = cb.createCriteriaUpdate(Student.class);
        Root<Student> root = update.from(Student.class);
        update.set(root.get(Student_.deleted), true)
                .where(cb.equal(root.get(Student_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Student with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Student with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Student> update = cb.createCriteriaUpdate(Student.class);
        Root<Student> root = update.from(Student.class);
        int result = 0;
        update.set(root.get(Student_.deleted), false)
                .where(cb.equal(root.get(Student_.id), id));
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Student with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Student with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }
    
    private List<Selection<?>>  buildMultiSelect(Root<Student> root) {
        Join<Student, Group> join = root.join(Student_.group, JoinType.LEFT);
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Student_.id));
        selectList.add(root.get(Student_.firstName));
        selectList.add(root.get(Student_.lastName));
        selectList.add(root.get(Student_.deleted));
        selectList.add(root.get(Student_.email));
        selectList.add(root.get(Student_.phoneNumber));
        selectList.add(join.get(Group_.id));
        selectList.add(join.get(Group_.name));
        return selectList;
    }
    
    private List<Order> getOrderList(CriteriaBuilder cb, Root<Student> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Student_.LAST_NAME)));
        orderList.add(cb.asc(root.get(Student_.FIRST_NAME)));
        return orderList;
    }

}
