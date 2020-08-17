package com.petelko.university.service.dto;

import java.time.LocalDate;
import java.util.Objects;

public class FilterDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long groupId;
    private Long teacherId;

    public FilterDTO(LocalDate startDate, LocalDate endDate, Long groupId, Long teacherId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.groupId = groupId;
        this.teacherId = teacherId;
    }

    public FilterDTO() {
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
        if (!(o instanceof FilterDTO)) return false;
        FilterDTO filterDTO = (FilterDTO) o;
        return Objects.equals(getStartDate(), filterDTO.getStartDate()) &&
                Objects.equals(getEndDate(), filterDTO.getEndDate()) &&
                Objects.equals(getGroupId(), filterDTO.getGroupId()) &&
                Objects.equals(getTeacherId(), filterDTO.getTeacherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate(), getGroupId(), getTeacherId());
    }

    @Override
    public String toString() {
        return "FilterDTO[" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", groupId=" + groupId +
                ", teacherId=" + teacherId +
                ']';
    }
}
