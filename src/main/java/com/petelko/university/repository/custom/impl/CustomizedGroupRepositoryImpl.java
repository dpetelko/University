package com.petelko.university.repository.custom.impl;

import com.petelko.university.model.Department;
import com.petelko.university.model.Department_;
import com.petelko.university.model.Group;
import com.petelko.university.model.Group_;
import com.petelko.university.model.dto.GroupDTO;
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

public class CustomizedGroupRepositoryImpl
        implements com.petelko.university.repository.custom.CustomizedGroupRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedGroupRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<GroupDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GroupDTO> query = cb.createQuery(GroupDTO.class);
        Root<Group> root = query.from(Group.class);
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<GroupDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' groups", result.size());
        return result;
    }

    @Override
    public List<GroupDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GroupDTO> query = cb.createQuery(GroupDTO.class);
        Root<Group> root = query.from(Group.class);
        query.where(cb.isFalse(root.get(Group_.deleted)));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(cb, root));
        List<GroupDTO> result = em.createQuery(query).getResultList();
        LOGGER.trace("Found '{}' active groups", result.size());
        return result;
    }

    @Override
    public GroupDTO findDTOById(Long id) {
        LOGGER.debug("findDTOById()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GroupDTO> query = cb.createQuery(GroupDTO.class);
        Root<Group> root = query.from(Group.class);
        query.where(cb.equal(root.get(Group_.id), id));
        query.multiselect(buildMultiSelect(root));
        GroupDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Group with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.trace("Found '{}'", result);
        return result;
    }
    
    @Override
    public GroupDTO findDTOByName(String name) {
        LOGGER.debug("findDTOByName()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GroupDTO> query = cb.createQuery(GroupDTO.class);
        Root<Group> root = query.from(Group.class);
        query.where(cb.equal(root.get(Group_.name), name));
        query.multiselect(buildMultiSelect(root));
        GroupDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Group with Name '%s' not found", name);
            throw new EntityNotFoundException(msg);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Group> update = cb.createCriteriaUpdate(Group.class);
        Root<Group> root = update.from(Group.class);
        update.set(root.get(Group_.deleted), true).where(cb.equal(root.get(Group_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Group with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Group with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Group> update = cb.createCriteriaUpdate(Group.class);
        Root<Group> root = update.from(Group.class);
        update.set(root.get(Group_.deleted), false).where(cb.equal(root.get(Group_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Group with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Group with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }

    private List<Selection<?>> buildMultiSelect(Root<Group> root) {
        Join<Group, Department> join = root.join(Group_.department, JoinType.LEFT);
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Group_.id));
        selectList.add(root.get(Group_.name));
        selectList.add(root.get(Group_.deleted));
        selectList.add(join.get(Department_.id));
        selectList.add(join.get(Department_.name));
        return selectList;
    }

    private List<Order> getOrderList(CriteriaBuilder cb, Root<Group> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Group_.NAME)));
        return orderList;
    }
}
