package com.petelko.university.repository.custom.impl;

import com.petelko.university.model.Auditory;
import com.petelko.university.model.Auditory_;
import com.petelko.university.model.Department;
import com.petelko.university.model.Department_;
import com.petelko.university.model.Group;
import com.petelko.university.model.Group_;
import com.petelko.university.model.Lecture;
import com.petelko.university.model.Lecture_;
import com.petelko.university.model.Lesson;
import com.petelko.university.model.Lesson_;
import com.petelko.university.model.Person_;
import com.petelko.university.model.Subject;
import com.petelko.university.model.Subject_;
import com.petelko.university.model.Teacher;
import com.petelko.university.model.Teacher_;
import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.repository.custom.CustomizedLessonRepository;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.repository.exception.QueryNotExecuteException;
import com.petelko.university.service.dto.FilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

public class CustomizedLessonRepositoryImpl implements CustomizedLessonRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedLessonRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<LessonDTO> findAllDTO() {
        LOGGER.debug("getAllDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LessonDTO> query = cb.createQuery(LessonDTO.class);
        Root<Lesson> root = query.from(Lesson.class);
        query.multiselect(buildMultiSelectForLessonDTO(root));
        query.orderBy(getOrderList(cb, root));
        List<LessonDTO> result = em.createQuery(query).getResultList();
        result.forEach(lesson -> lesson.setGroups(getGroupsForLesson(lesson.getId())));
        return result;
    }

    @Override
    public List<LessonDTO> findAllActiveDTO() {
        LOGGER.debug("getAllActiveDTO()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LessonDTO> query = cb.createQuery(LessonDTO.class);
        Root<Lesson> root = query.from(Lesson.class);
        query.where(cb.isFalse(root.get(Lesson_.deleted)));
        query.multiselect(buildMultiSelectForLessonDTO(root));
        query.orderBy(getOrderList(cb, root));
        List<LessonDTO> result = em.createQuery(query).getResultList();
        result.forEach(lesson -> lesson.setGroups(getGroupsForLesson(lesson.getId())));
        LOGGER.trace("Found '{}' active Lessons", result.size());
        return result;
    }

    @Override
    public LessonDTO findDTOById(Long id) {
        LOGGER.debug("getDTObyId()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LessonDTO> query = cb.createQuery(LessonDTO.class);
        Root<Lesson> root = query.from(Lesson.class);
        query.where(cb.equal(root.get(Lesson_.id), id));
        query.multiselect(buildMultiSelectForLessonDTO(root));
        LessonDTO result;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            String msg = format("Lesson with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        result.setGroups(getGroupsForLesson(id));
        LOGGER.trace("Found '{}'", result);
        return result;
    }


    public Set<Long> getGroupIdListFromLessonByDataAndLecture(LocalDate date, Long lectureId, Long auditoryId) {
        LOGGER.debug("getGroupIdListFromLessonByDataAndLecture({}, {})", date, lectureId);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Lecture> lectureJoin = root.join(Lesson_.lecture, JoinType.LEFT);
        predicates.add(cb.equal(lectureJoin.get(Lecture_.id), lectureId));

        Join<Lesson, Auditory> auditoryJoin = root.join(Lesson_.auditory, JoinType.LEFT);
        predicates.add(cb.notEqual(auditoryJoin.get(Auditory_.id), auditoryId));

        predicates.add(cb.equal(root.get(Lesson_.date), date));
        query.where(predicates.toArray(new Predicate[]{}));
        SetJoin<Lesson, Group> joinGroup = root.join(Lesson_.groups);
        query.multiselect(joinGroup.get(Group_.id));
        TypedQuery<Long> result = em.createQuery(query);
        return result.getResultStream().collect(toSet());
    }

    @Override
    public List<LessonDTO> getLessons(FilterDTO lessonFilter) {
        LOGGER.debug("getReport({})", lessonFilter);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LessonDTO> query = cb.createQuery(LessonDTO.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        LocalDate startDate = lessonFilter.getStartDate();
        if (nonNull(startDate)) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Lesson_.date), startDate));
        }
        LocalDate endDate = lessonFilter.getEndDate();
        if (nonNull(endDate)) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Lesson_.date), endDate));
        }
        Long teacherId = lessonFilter.getTeacherId();
        if (nonNull(teacherId)) {
            Join<Lesson, Teacher> teacherJoin = root.join(Lesson_.teacher, JoinType.LEFT);
            predicates.add(cb.equal(teacherJoin.get(Person_.id), teacherId));
        }
        Long groupId = lessonFilter.getGroupId();
        if (nonNull(groupId)) {
            SetJoin<Lesson, Group> joinGroup = root.join(Lesson_.groups);
            predicates.add(cb.equal(joinGroup.get(Group_.id), groupId));
        }

        query.where(predicates.toArray(new Predicate[]{}));
        query.multiselect(buildMultiSelectForLessonDTO(root));
        query.orderBy(getOrderList(cb, root));
        List<LessonDTO> result = em.createQuery(query).getResultList();
        result.forEach(lesson -> lesson.setGroups(getGroupsForLesson(lesson.getId())));
        return result;
    }

    private Set<GroupDTO> getGroupsForLesson(Long lessonId) {
        LOGGER.debug("getGroupsForLesson({})", lessonId);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GroupDTO> query = cb.createQuery(GroupDTO.class);
        Root<Lesson> root = query.from(Lesson.class);
        query.where(cb.equal(root.get(Lesson_.id), lessonId));
        query.multiselect(buildMultiSelectForGroupDTO(root));
        TypedQuery<GroupDTO> result = em.createQuery(query);
        return result.getResultStream().collect(toSet());
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("delete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Lesson> update = cb.createCriteriaUpdate(Lesson.class);
        Root<Lesson> root = update.from(Lesson.class);
        update.set(root.get(Lesson_.deleted), true)
                .where(cb.equal(root.get(Lesson_.id), id));
        int result = 0;
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Lesson with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Lesson with id '%s' not found for delete", id);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public void undelete(Long id) {
        LOGGER.debug("undelete() {}", id);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Lesson> update = cb.createCriteriaUpdate(Lesson.class);
        Root<Lesson> root = update.from(Lesson.class);
        int result = 0;
        update.set(root.get(Lesson_.deleted), false)
                .where(cb.equal(root.get(Lesson_.id), id));
        try {
            result = em.createQuery(update).executeUpdate();
        } catch (Exception e) {
            String msg = format("Unable to delete Lesson with ID '%s'", id);
            throw new QueryNotExecuteException(msg, e);
        }
        if (result != 1) {
            String msg = format("Lesson with id '%s' not found for restore", id);
            throw new EntityNotFoundException(msg);
        }
    }

    public boolean isAuditoryNotUsedByDateAndLecture(Long auditoryId, Long lectureId, Long lessonId, LocalDate date) {
        LOGGER.debug("isAuditoryNotUsedByDateAndLecture()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Boolean> query = cb.createQuery(Boolean.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Auditory> auditoryJoin = root.join(Lesson_.auditory, JoinType.LEFT);
        predicates.add(cb.equal(auditoryJoin.get(Auditory_.id), auditoryId));
        Join<Lesson, Lecture> lectureJoin = root.join(Lesson_.lecture, JoinType.LEFT);
        predicates.add(cb.equal(lectureJoin.get(Lecture_.id), lectureId));
        predicates.add(cb.equal(root.get(Lesson_.date), date));
        if (nonNull(lessonId)) {
            predicates.add(cb.notEqual(root.get(Lesson_.id), lessonId));
        }
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(cb.equal(cb.count(root), 0));
        return em.createQuery(query).getSingleResult();
    }


    @Override
    public boolean isTeacherNotUsedByDateAndLecture(Long teacherId, Long lectureId, Long lessonId, LocalDate date) {
        LOGGER.debug("isTeacherNotUsedByDateAndLecture()");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Boolean> query = cb.createQuery(Boolean.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Teacher> teacherJoin = root.join(Lesson_.teacher, JoinType.LEFT);
        predicates.add(cb.equal(teacherJoin.get(Teacher_.id), teacherId));
        Join<Lesson, Lecture> lectureJoin = root.join(Lesson_.lecture, JoinType.LEFT);
        predicates.add(cb.equal(lectureJoin.get(Lecture_.id), lectureId));
        predicates.add(cb.equal(root.get(Lesson_.date), date));
        if (nonNull(lessonId)) {
            predicates.add(cb.notEqual(root.get(Lesson_.id), lessonId));
        }
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(cb.equal(cb.count(root), 0));
        return em.createQuery(query).getSingleResult();
    }

    private List<Selection<?>> buildMultiSelectForGroupDTO(Root<Lesson> root) {
        SetJoin<Lesson, Group> joinGroup = root.join(Lesson_.groups);
        Path<Department> joinDep = root.join(Lesson_.groups).get(Group_.department);
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(joinGroup.get(Group_.id));
        selectList.add(joinGroup.get(Group_.name));
        selectList.add(joinGroup.get(Group_.deleted));
        selectList.add(joinDep.get(Department_.id));
        selectList.add(joinDep.get(Department_.name));
        return selectList;
    }

    private List<Selection<?>> buildMultiSelectForLessonDTO(Root<Lesson> root) {
        Join<Lesson, Subject> joinSubject = root.join(Lesson_.subject, JoinType.LEFT);
        Join<Lesson, Teacher> joinTeacher = root.join(Lesson_.teacher, JoinType.LEFT);
        Join<Lesson, Auditory> joinAuditory = root.join(Lesson_.auditory, JoinType.LEFT);
        Join<Lesson, Lecture> joinLecture = root.join(Lesson_.lecture, JoinType.LEFT);
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get(Lesson_.id));
        selectList.add(root.get(Lesson_.date));
        selectList.add(joinSubject.get(Subject_.id));
        selectList.add(joinSubject.get(Subject_.name));
        selectList.add(joinSubject.get(Subject_.description));
        selectList.add(joinTeacher.get(Teacher_.id));
        selectList.add(joinTeacher.get(Teacher_.firstName));
        selectList.add(joinTeacher.get(Teacher_.lastName));
        selectList.add(joinAuditory.get(Auditory_.id));
        selectList.add(joinAuditory.get(Auditory_.name));
        selectList.add(joinAuditory.get(Auditory_.description));
        selectList.add(joinAuditory.get(Auditory_.capacity));
        selectList.add(joinLecture.get(Lecture_.id));
        selectList.add(joinLecture.get(Lecture_.name));
        selectList.add(joinLecture.get(Lecture_.startTime));
        selectList.add(joinLecture.get(Lecture_.endTime));
        selectList.add(root.get(Lesson_.deleted));
        return selectList;
    }


    private List<Order> getOrderList(CriteriaBuilder cb, Root<Lesson> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(root.get(Lesson_.date)));
        orderList.add(cb.asc(root.get(Lesson_.lecture)));
        return orderList;
    }
}
