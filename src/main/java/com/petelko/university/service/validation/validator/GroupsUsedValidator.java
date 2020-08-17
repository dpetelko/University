package com.petelko.university.service.validation.validator;

import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.service.GroupService;
import com.petelko.university.service.LessonService;
import com.petelko.university.service.validation.annotation.GroupsUsedCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class GroupsUsedValidator implements ConstraintValidator<GroupsUsedCheck, LessonDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupsUsedValidator.class);
    public static final String GROUPS = "groups";
    public static final String GROUPS_IS_USED_FOR_THIS_TIME = "Sorry, groups '%s' is used for this time!";
    public static final String EMPTY = "";
    public static final String SPACE = " ";

    @Autowired
    private LessonService lessonService;
    @Autowired
    private GroupService groupService;

    @Override
    public boolean isValid(LessonDTO lesson, ConstraintValidatorContext context) {
        String usedGroups = getUsedGroupNames(lesson);
        boolean result = Objects.equals(usedGroups, EMPTY);
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(format(GROUPS_IS_USED_FOR_THIS_TIME, usedGroups))
                    .addPropertyNode(GROUPS)
                    .addConstraintViolation();
        }
        return result;
    }

    private String getUsedGroupNames(LessonDTO lesson) {
        Long lectureId = lesson.getLectureId();
        Long auditoryId = lesson.getAuditoryId();
        LocalDate date = lesson.getDate();
        Set<Long> existingIdSet = lessonService.getBusyGroups(date, lectureId, auditoryId);
        Set<Long> incommingIdSet = getIncommingIdSet(lesson);
        existingIdSet.retainAll(incommingIdSet);
        return getGroupNames(existingIdSet);
    }

    private String getGroupNames(Set<Long> idSet) {
        return idSet.stream()
                .map(groupService::getGroupNameById)
                .collect(joining(SPACE));
    }

    private Set<Long> getIncommingIdSet(LessonDTO lesson) {
        return lesson.getGroups().stream()
                .map(GroupDTO::getId)
                .collect(toSet());
    }
}
