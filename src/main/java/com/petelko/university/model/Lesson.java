package com.petelko.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Subject.class)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "lessons_groups",
            joinColumns = { @JoinColumn(name = "lesson_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private Set<Group> groups = new HashSet<>();

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(targetEntity = Auditory.class)
    @JoinColumn(name = "auditory_id")
    private Auditory auditory;

    @ManyToOne(targetEntity = Lecture.class)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public Lesson() {
    }

    public Lesson(Long id,
                  Subject subject,
                  Teacher teacher,
                  Set<Group> groups,
                  LocalDate date,
                  Auditory auditory,
                  Lecture lecture,
                  boolean deleted) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.groups = groups;
        this.date = date;
        this.auditory = auditory;
        this.lecture = lecture;
        this.deleted = deleted;
    }

    public Lesson(Subject subject,
                  Teacher teacher,
                  Set<Group> groups,
                  LocalDate date,
                  Auditory auditory,
                  Lecture lecture,
                  boolean deleted) {
        this.subject = subject;
        this.teacher = teacher;
        this.groups = groups;
        this.date = date;
        this.auditory = auditory;
        this.lecture = lecture;
        this.deleted = deleted;
    }

    public Lesson(Subject subject,
                  Teacher teacher,
                  Set<Group> groups,
                  LocalDate date,
                  Auditory auditory,
                  Lecture lecture) {
        this.subject = subject;
        this.teacher = teacher;
        this.groups = groups;
        this.date = date;
        this.auditory = auditory;
        this.lecture = lecture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Auditory getAuditory() {
        return auditory;
    }

    public void setAuditory(Auditory auditory) {
        this.auditory = auditory;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
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
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return isDeleted() == lesson.isDeleted() &&
                Objects.equals(getId(), lesson.getId()) &&
                Objects.equals(getSubject(), lesson.getSubject()) &&
                Objects.equals(getTeacher(), lesson.getTeacher()) &&
                Objects.equals(getGroups(), lesson.getGroups()) &&
                Objects.equals(getDate(), lesson.getDate()) &&
                Objects.equals(getAuditory(), lesson.getAuditory()) &&
                Objects.equals(getLecture(), lesson.getLecture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getSubject(),
                getTeacher(),
                getGroups(),
                getDate(),
                getAuditory(),
                getLecture(),
                isDeleted());
    }

    @Override
    public String toString() {
        return "Lesson[" +
                "id=" + id +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", groups=" + groups +
                ", date=" + date +
                ", auditory=" + auditory +
                ", lecture=" + lecture +
                ", deleted=" + deleted +
                ']';
    }
}
