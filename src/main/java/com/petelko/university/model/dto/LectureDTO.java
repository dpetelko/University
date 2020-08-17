package com.petelko.university.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petelko.university.service.validation.annotation.LectureDurationConstraint;
import com.petelko.university.service.validation.annotation.UniqueField;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Objects;

@UniqueField(fieldName = "name", message = "{nameField.alreadyused}")
@LectureDurationConstraint
public class LectureDTO {

    @ApiModelProperty(notes = "${description.field.lectures.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.lectures.name}", name = "name", required = true)
    @NotBlank(message = "{name.notblank}")
    @Size(min = 1, max = 30, message = "{name.size}")
    private String name;
    @ApiModelProperty(notes = "${description.field.lectures.starttime}", name = "startTime", required = true)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @ApiModelProperty(notes = "${description.field.lectures.endtime}", name = "endTime", required = true)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @ApiModelProperty(notes = "${description.field.lectures.deleted}", name = "deleted", required = false)
    private boolean deleted;

    public LectureDTO() {
    }

    public LectureDTO(Long id,
                      String name,
                      LocalTime startTime,
                      LocalTime endTime,
                      boolean deleted) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deleted = deleted;
    }

    public LectureDTO(Long id,
                      String name,
                      LocalTime startTime,
                      LocalTime endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LectureDTO(String name,
                      LocalTime startTime,
                      LocalTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonFormat(pattern = "HH:mm")
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "HH:mm")
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
        if (!(o instanceof LectureDTO)) return false;
        LectureDTO that = (LectureDTO) o;
        return isDeleted() == that.isDeleted() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getStartTime(), that.getStartTime()) &&
                Objects.equals(getEndTime(), that.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getName(),
                getStartTime(),
                getEndTime(),
                isDeleted());
    }

    @Override
    public String toString() {
        return "LectureDTO[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deleted=" + deleted +
                ']';
    }
}
