package com.petelko.university.model.dto;

import com.petelko.university.service.validation.annotation.AuditoryOverflowCheck;
import com.petelko.university.service.validation.annotation.AuditoryUsedCheck;
import com.petelko.university.service.validation.annotation.GroupsUsedCheck;
import com.petelko.university.service.validation.annotation.TeacherUsedCheck;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@AuditoryUsedCheck
@TeacherUsedCheck
@GroupsUsedCheck
@AuditoryOverflowCheck
public class LessonDTO {

    @ApiModelProperty(notes = "${description.field.lessons.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.lessons.date}", name = "date", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{lesson.date.notnull}")
    private LocalDate date;
    @ApiModelProperty(notes = "${description.field.lessons.groups}", name = "groups", required = true)
    @NotEmpty(message = "{lesson.groups.notnull}")
    private Set<GroupDTO> groups;
    @ApiModelProperty(notes = "${description.field.lessons.subjectid}", name = "subjectId", required = true)
    @NotNull(message = "{lesson.subject.name.notnull}")
    private Long subjectId;
    @ApiModelProperty(notes = "${description.field.lessons.subjectname}", name = "subjectName", required = false)
    private String subjectName;
    @ApiModelProperty(notes = "${description.field.lessons.subjectdescription}", name = "subjectDescription", required = false)
    private String subjectDescription;
    @ApiModelProperty(notes = "${description.field.lessons.teacherid}", name = "teacherId", required = true)
    @NotNull(message = "{lesson.teacher.name.notnull}")
    private Long teacherId;
    @ApiModelProperty(notes = "${description.field.lessons.teacherfirstname}", name = "teacherFirstName", required = false)
    private String teacherFirstName;
    @ApiModelProperty(notes = "${description.field.lessons.teacherlastname}", name = "teacherLastName", required = false)
    private String teacherLastName;
    @ApiModelProperty(notes = "${description.field.lessons.auditoryid}", name = "auditoryId", required = true)
    @NotNull(message = "{lesson.auditory.name.notnull}")
    private Long auditoryId;
    @ApiModelProperty(notes = "${description.field.lessons.auditoryname}", name = "auditoryName", required = false)
    private String auditoryName;
    @ApiModelProperty(notes = "${description.field.lessons.auditorydescription}", name = "auditoryDescription", required = false)
    private String auditoryDescription;
    @ApiModelProperty(notes = "${description.field.lessons.auditorycapacity}", name = "auditoryCapacity", required = false)
    private int auditoryCapacity;
    @ApiModelProperty(notes = "${description.field.lessons.lectureid}", name = "lectureId", required = true)
    @NotNull(message = "{lesson.lecture.name.notnull}")
    private Long lectureId;
    @ApiModelProperty(notes = "${description.field.lessons.lecturename}", name = "auditoryName", required = false)
    private String lectureName;
    @ApiModelProperty(notes = "${description.field.lessons.lecturestarttime}", name = "auditoryName", required = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime lectureStartTime;
    @ApiModelProperty(notes = "${description.field.lessons.lectureendtime}", name = "auditoryName", required = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime lectureEndTime;
    @ApiModelProperty(notes = "${description.field.lessons.deleted}", name = "deleted", required = false)
    private boolean deleted;

    public LessonDTO() {
    }

    public LessonDTO(Long id,
                     LocalDate date,
                     Set<GroupDTO> groups,
                     Long subjectId,
                     String subjectName,
                     String subjectDescription,
                     Long teacherId,
                     String teacherFirstName,
                     String teacherLastName,
                     Long auditoryId,
                     String auditoryName,
                     String auditoryDescription,
                     int auditoryCapacity,
                     Long lectureId,
                     String lectureName,
                     LocalTime lectureStartTime,
                     LocalTime lectureEndTime,
                     boolean deleted) {
        this.id = id;
        this.date = date;
        this.groups = groups;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.teacherId = teacherId;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.auditoryId = auditoryId;
        this.auditoryName = auditoryName;
        this.auditoryDescription = auditoryDescription;
        this.auditoryCapacity = auditoryCapacity;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.lectureStartTime = lectureStartTime;
        this.lectureEndTime = lectureEndTime;
        this.deleted = deleted;
    }

    public LessonDTO(Long id,
                     LocalDate date,
                     Long subjectId,
                     String subjectName,
                     String subjectDescription,
                     Long teacherId,
                     String teacherFirstName,
                     String teacherLastName,
                     Long auditoryId,
                     String auditoryName,
                     String auditoryDescription,
                     int auditoryCapacity,
                     Long lectureId,
                     String lectureName,
                     LocalTime lectureStartTime,
                     LocalTime lectureEndTime) {
        this.id = id;
        this.date = date;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.teacherId = teacherId;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.auditoryId = auditoryId;
        this.auditoryName = auditoryName;
        this.auditoryDescription = auditoryDescription;
        this.auditoryCapacity = auditoryCapacity;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.lectureStartTime = lectureStartTime;
        this.lectureEndTime = lectureEndTime;
    }

    public LessonDTO(LocalDate date,
                     Set<GroupDTO> groups,
                     Long subjectId,
                     String subjectName,
                     String subjectDescription,
                     Long teacherId,
                     String teacherFirstName,
                     String teacherLastName,
                     Long auditoryId,
                     String auditoryName,
                     String auditoryDescription,
                     int auditoryCapacity,
                     Long lectureId,
                     String lectureName,
                     LocalTime lectureStartTime,
                     LocalTime lectureEndTime) {
        this.date = date;
        this.groups = groups;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.teacherId = teacherId;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.auditoryId = auditoryId;
        this.auditoryName = auditoryName;
        this.auditoryDescription = auditoryDescription;
        this.auditoryCapacity = auditoryCapacity;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.lectureStartTime = lectureStartTime;
        this.lectureEndTime = lectureEndTime;
    }

    public LessonDTO(Long id,
                     LocalDate date,
                     Long subjectId,
                     String subjectName,
                     String subjectDescription,
                     Long teacherId,
                     String teacherFirstName,
                     String teacherLastName,
                     Long auditoryId,
                     String auditoryName,
                     String auditoryDescription,
                     int auditoryCapacity,
                     Long lectureId,
                     String lectureName,
                     LocalTime lectureStartTime,
                     LocalTime lectureEndTime,
                     boolean deleted) {
        this.id = id;
        this.date = date;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.teacherId = teacherId;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.auditoryId = auditoryId;
        this.auditoryName = auditoryName;
        this.auditoryDescription = auditoryDescription;
        this.auditoryCapacity = auditoryCapacity;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.lectureStartTime = lectureStartTime;
        this.lectureEndTime = lectureEndTime;
        this.deleted = deleted;
    }

    public void addGroup(GroupDTO group) {
        this.groups.add(group);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<GroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupDTO> groups) {
        this.groups = groups;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public Long getAuditoryId() {
        return auditoryId;
    }

    public void setAuditoryId(Long auditoryId) {
        this.auditoryId = auditoryId;
    }

    public String getAuditoryName() {
        return auditoryName;
    }

    public void setAuditoryName(String auditoryName) {
        this.auditoryName = auditoryName;
    }

    public String getAuditoryDescription() {
        return auditoryDescription;
    }

    public void setAuditoryDescription(String auditoryDescription) {
        this.auditoryDescription = auditoryDescription;
    }

    public int getAuditoryCapacity() {
        return auditoryCapacity;
    }

    public void setAuditoryCapacity(int auditoryCapacity) {
        this.auditoryCapacity = auditoryCapacity;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public LocalTime getLectureStartTime() {
        return lectureStartTime;
    }

    public void setLectureStartTime(LocalTime lectureStartTime) {
        this.lectureStartTime = lectureStartTime;
    }

    public LocalTime getLectureEndTime() {
        return lectureEndTime;
    }

    public void setLectureEndTime(LocalTime lectureEndTime) {
        this.lectureEndTime = lectureEndTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonDTO)) return false;
        LessonDTO lessonDTO = (LessonDTO) o;
        return getAuditoryCapacity() == lessonDTO.getAuditoryCapacity() &&
                isDeleted() == lessonDTO.isDeleted() &&
                Objects.equals(getId(), lessonDTO.getId()) &&
                Objects.equals(getDate(), lessonDTO.getDate()) &&
                Objects.equals(getGroups(), lessonDTO.getGroups()) &&
                Objects.equals(getSubjectId(), lessonDTO.getSubjectId()) &&
                Objects.equals(getSubjectName(), lessonDTO.getSubjectName()) &&
                Objects.equals(getSubjectDescription(), lessonDTO.getSubjectDescription()) &&
                Objects.equals(getTeacherId(), lessonDTO.getTeacherId()) &&
                Objects.equals(getTeacherFirstName(), lessonDTO.getTeacherFirstName()) &&
                Objects.equals(getTeacherLastName(), lessonDTO.getTeacherLastName()) &&
                Objects.equals(getAuditoryId(), lessonDTO.getAuditoryId()) &&
                Objects.equals(getAuditoryName(), lessonDTO.getAuditoryName()) &&
                Objects.equals(getAuditoryDescription(), lessonDTO.getAuditoryDescription()) &&
                Objects.equals(getLectureId(), lessonDTO.getLectureId()) &&
                Objects.equals(getLectureName(), lessonDTO.getLectureName()) &&
                Objects.equals(getLectureStartTime(), lessonDTO.getLectureStartTime()) &&
                Objects.equals(getLectureEndTime(), lessonDTO.getLectureEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getDate(),
                getGroups(),
                getSubjectId(),
                getSubjectName(),
                getSubjectDescription(),
                getTeacherId(),
                getTeacherFirstName(),
                getTeacherLastName(),
                getAuditoryId(),
                getAuditoryName(),
                getAuditoryDescription(),
                getAuditoryCapacity(),
                getLectureId(),
                getLectureName(),
                getLectureStartTime(),
                getLectureEndTime(),
                isDeleted());
    }

    @Override
    public String toString() {
        return "LessonDTO[" +
                "id=" + id +
                ", date=" + date +
                ", groups=" + groups +
                ", subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectDescription='" + subjectDescription + '\'' +
                ", teacherId=" + teacherId +
                ", teacherFirstName='" + teacherFirstName + '\'' +
                ", teacherLastName='" + teacherLastName + '\'' +
                ", auditoryId=" + auditoryId +
                ", auditoryName='" + auditoryName + '\'' +
                ", auditoryDescription='" + auditoryDescription + '\'' +
                ", auditoryCapacity=" + auditoryCapacity +
                ", lectureId=" + lectureId +
                ", lectureName='" + lectureName + '\'' +
                ", lectureStartTime=" + lectureStartTime +
                ", lectureEndTime=" + lectureEndTime +
                ", deleted=" + deleted +
                ']';
    }
}
