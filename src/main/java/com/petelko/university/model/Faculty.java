package com.petelko.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "faculties")
public class Faculty {

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

    @OneToMany
    private Set<Department> departments;

    public Faculty(Long id,
                   String name,
                   String description,
                   Set<Department> departments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.departments = departments;
    }

    public Faculty(Long id,
                   String name,
                   String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Faculty(String name,
                   String description,
                   Set<Department> departments) {
        this.name = name;
        this.description = description;
        this.departments = departments;
    }

    public Faculty(String name,
                   String description) {
        this.name = name;
        this.description = description;
    }

    public Faculty() {
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

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
        if (!(obj instanceof Faculty)) {
            return false;
        }
        Faculty other = (Faculty) obj;
        return Objects.equals(description, other.description) &&
                Objects.equals(id, other.id) &&
                Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Faculty [id=" + id
                + ", name=" + name
                + ", description=" + description
                + "]";
    }
}
