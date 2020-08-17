package com.petelko.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "department")
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "department")
    private Set<Subject> subjects;

    @OneToMany(mappedBy = "department")
    private Set<Group> groups;


    public Department() {
    }

    public Department(Long id,
                      String name,
                      String description,
                      Faculty faculty,
                      Set<Teacher> teachers,
                      Set<Subject> subjects,
                      Set<Group> groups) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.faculty = faculty;
        this.teachers = teachers;
        this.subjects = subjects;
        this.groups = groups;
    }

    public Department(String name,
                      String description,
                      Faculty faculty,
                      Set<Teacher> teachers,
                      Set<Subject> subjects,
                      Set<Group> groups) {
        this.name = name;
        this.description = description;
        this.faculty = faculty;
        this.teachers = teachers;
        this.subjects = subjects;
        this.groups = groups;
    }

    public Department(Long id,
                      String name,
                      String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Department(Long id,
                      String name) {
        this.id = id;
        this.name = name;
    }

    public Department(Long id,
                      String name,
                      String description,
                      Faculty faculty) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.faculty = faculty;
    }

    public Department(String name,
                      String description) {
        this.name = name;
        this.description = description;
    }

    public Department(String name,
                      String description,
                      Faculty faculty) {
        this.name = name;
        this.description = description;
        this.faculty = faculty;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
