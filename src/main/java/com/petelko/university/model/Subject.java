package com.petelko.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id")
    private Department department;

    public Subject(Long id,
                   String name,
                   String description,
                   boolean deleted,
                   Department department) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
        this.department = department;
    }

    public Subject(Long id,
                   String name,
                   String description,
                   Department department) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.department = department;
    }

    public Subject(String name,
                   String description,
                   Department department) {
        this.name = name;
        this.description = description;
        this.department = department;
    }

    public Subject(String name,
                   String description,
                   boolean deleted,
                   Department department) {
        this.name = name;
        this.description = description;
        this.deleted = deleted;
        this.department = department;
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Subject() {
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) obj;
        return Objects.equals(description, other.description) &&
                Objects.equals(id, other.id) &&
                Objects.equals(name, other.name);
    }


    @Override
    public String toString() {
        return "Subject[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ", department=" + department +
                "]";
    }
}
