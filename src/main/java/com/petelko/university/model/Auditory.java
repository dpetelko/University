package com.petelko.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "auditories")
public class Auditory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public Auditory(Long id,
                    String name,
                    String description,
                    int capacity,
                    boolean deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.deleted = deleted;
    }

    public Auditory() {
    }

    public Auditory(Long id,
                    String name,
                    String description,
                    int capacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }

    public Auditory(String name,
                    String description,
                    int capacity) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
        if (!(o instanceof Auditory)) return false;
        Auditory auditory = (Auditory) o;
        return getCapacity() == auditory.getCapacity() &&
                isDeleted() == auditory.isDeleted() &&
                Objects.equals(getId(), auditory.getId()) &&
                Objects.equals(getName(), auditory.getName()) &&
                Objects.equals(getDescription(), auditory.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getName(),
                getDescription(),
                getCapacity(),
                isDeleted());
    }

    @Override
    public String toString() {
        return "Auditory[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", deleted=" + deleted +
                ']';
    }
}
