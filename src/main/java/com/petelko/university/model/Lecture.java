package com.petelko.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "lectures")
public class Lecture {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public Lecture(Long id,
                   String name,
                   LocalTime startTime,
                   LocalTime endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lecture(String name,
                   LocalTime startTime,
                   LocalTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lecture(Long id,
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

    public Lecture() {
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

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
        if (!(o instanceof Lecture)) return false;
        Lecture lecture = (Lecture) o;
        return isDeleted() == lecture.isDeleted() &&
                Objects.equals(getId(), lecture.getId()) &&
                Objects.equals(getName(), lecture.getName()) &&
                Objects.equals(getStartTime(), lecture.getStartTime()) &&
                Objects.equals(getEndTime(), lecture.getEndTime());
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
        return "Lecture[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deleted=" + deleted +
                ']';
    }
}
