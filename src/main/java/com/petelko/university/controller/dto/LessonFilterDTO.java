package com.petelko.university.controller.dto;

import com.petelko.university.service.validation.annotation.DatesOrderCheck;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

import static java.util.Objects.isNull;


@DatesOrderCheck
public class LessonFilterDTO {
    @ApiModelProperty(notes = "${description.field.LessonFilter.startdate}", name = "startDate", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @ApiModelProperty(notes = "${description.field.LessonFilter.enddate}", name = "endDate", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @ApiModelProperty(notes = "${description.field.LessonFilter.groupid}", name = "groupId", required = false)
    private Long groupId;
    @ApiModelProperty(notes = "${description.field.LessonFilter.teacherid}", name = "teacherId", required = false)
    private Long teacherId;

    public LessonFilterDTO(LocalDate startDate, LocalDate endDate, Long groupId, Long teacherId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.groupId = groupId;
        this.teacherId = teacherId;
    }

    public LessonFilterDTO() {
    }

    public LessonFilterDTO(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isEmpty() {
        return isNull(this.getStartDate())
                && isNull(this.getEndDate())
                && isNull(this.getGroupId())
                && isNull(this.getTeacherId());
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonFilterDTO)) return false;
        LessonFilterDTO that = (LessonFilterDTO) o;
        return Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getEndDate(), that.getEndDate()) &&
                Objects.equals(getGroupId(), that.getGroupId()) &&
                Objects.equals(getTeacherId(), that.getTeacherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate(), getGroupId(), getTeacherId());
    }

    @Override
    public String toString() {
        return "ReportRequestDTO[" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", groupId=" + groupId +
                ", teacherId=" + teacherId +
                ']';
    }
}
