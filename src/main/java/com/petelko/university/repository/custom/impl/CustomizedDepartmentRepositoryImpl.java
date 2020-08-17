package com.petelko.university.repository.custom.impl;

import com.petelko.university.model.Department;
import com.petelko.university.model.Department_;
import com.petelko.university.model.Faculty;
import com.petelko.university.model.Faculty_;
import com.petelko.university.model.dto.DepartmentDTO;
import com.petelko.university.repository.custom.CustomizedDepartmentRepository;
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

public class CustomizedDepartmentRepositoryImpl implements CustomizedDepartmentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedDepartmentRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DepartmentDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentDTO> query = cb.createQuery(DepartmentDTO.class);
        Root<Department> root = query.from(Department.class);
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<DepartmentDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' Departments", result.size());
        return result;
    }

    @Override
    public List<DepartmentDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentDTO> query = cb.createQuery(DepartmentDTO.class);
        Root<Department> root = query.from(Department.class);
        query.where(cb.isFalse(root.get(Department_.deleted)));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<DepartmentDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' active Departments", result.size());
        return result;
    }

    @Override
    public DepartmentDTO findDTOById(Long id) {
        LOGGER.debug("getDTObyId()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentDTO> query = cb.createQuery(DepartmentDTO.class);
        Root<Department> root = query.from(Department.class);
        query.where(cb.equal(root.get(Department_.id), id));
        query.multiselect(buildMultiSelect(root));
        DepartmentDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Department with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }

    @Override
    public DepartmentDTO findDTOByName(String name) {
        LOGGER.debug("findDTOByName()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentDTO> query = cb.createQuery(DepartmentDTO.class);
        Root<Department> root = query.from(Department.class);
        query.where(cb.equal(root.get(Department_.name), name));
        query.multiselect(buildMultiSelect(root));
        DepartmentDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Department with name '%s' not found", name);
            throw new EntityNotFoundException(msg);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Department> update = cb.createCriteriaUpdate(Department.class);
        Root<Department> root = update.from(Department.class);
        update.set(root.get(Department_.deleted), true)
                .where(cb.equal(root.get(Department_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Department with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Department with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Department> update = cb.createCriteriaUpdate(Department.class);
        Root<Department> root = update.from(Department.class);
        int result = 0;
        update.set(root.get(Department_.deleted), false)
                .where(cb.equal(root.get(Department_.id), id));
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Department with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Department with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private List<Selection<?>> buildMultiSelect(Root<Department> root) {
        Join<Department, Faculty> joinFaculty = root.join(Department_.faculty, JoinType.LEFT);
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Department_.id));
        selectList.add(root.get(Department_.name));
        selectList.add(root.get(Department_.description));
        selectList.add(root.get(Department_.deleted));
        selectList.add(joinFaculty.get(Faculty_.id));
        selectList.add(joinFaculty.get(Faculty_.name));
        selectList.add(joinFaculty.get(Faculty_.description));
        return selectList;
    }

    private List<Order> getOrderList(CriteriaBuilder cb, Root<Department> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Department_.NAME)));
        return orderList;
    }
}
