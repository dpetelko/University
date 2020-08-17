package com.petelko.university.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @ManyToOne(targetEntity = Subject.class)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id")
    private Department department;

    public Teacher() {
    }

    public Teacher(Long id,
                   String firstName,
                   String lastName,
                   boolean deleted,
                   String email,
                   String phoneNumber,
                   Subject subject) {
        super(id, firstName, lastName, deleted, email, phoneNumber);
        this.subject = subject;
    }

    public Teacher(Long id,
                   String firstName,
                   String lastName,
                   String email,
                   String phoneNumber,
                   Subject subject) {
        super(id, firstName, lastName, email, phoneNumber);
        this.subject = subject;
    }

    public Teacher(String firstName,
                   String lastName,
                   String email,
                   String phoneNumber,
                   Subject subject) {
        super(firstName, lastName, email, phoneNumber);
        this.subject = subject;
    }

    public Teacher(String firstName,
                   String lastName,
                   boolean deleted,
                   Subject subject) {
        super(firstName, lastName, deleted);
        this.subject = subject;
    }

    public Teacher(Long id,
                   String firstName,
                   String lastName,
                   Subject subject) {
        super(id, firstName, lastName);
        this.subject = subject;
    }

    public Teacher(String firstName,
                   String lastName,
                   Subject subject) {
        super(firstName, lastName);
        this.subject = subject;
    }

    public Teacher(Long id,
                   String firstName,
                   String lastName,
                   String email,
                   String phoneNumber) {
        super(id, firstName, lastName, email, phoneNumber);
    }

    public Teacher(Long id,
                   String firstName,
                   String lastName,
                   boolean deleted,
                   String email,
                   String phoneNumber,
                   Subject subject,
                   Department department) {
        super(id, firstName, lastName, deleted, email, phoneNumber);
        this.subject = subject;
        this.department = department;
    }

    public Teacher(Long id,
                   String firstName,
                   String lastName,
                   String email,
                   String phoneNumber,
                   Subject subject,
                   Department department) {
        super(id, firstName, lastName, email, phoneNumber);
        this.subject = subject;
        this.department = department;
    }

    public Teacher(String firstName,
                   String lastName,
                   String email,
                   String phoneNumber,
                   Subject subject,
                   Department department) {
        super(firstName, lastName, email, phoneNumber);
        this.subject = subject;
        this.department = department;
    }

    public Teacher(String firstName,
                   String lastName,
                   boolean deleted,
                   Subject subject,
                   Department department) {
        super(firstName, lastName, deleted);
        this.subject = subject;
        this.department = department;
    }

    public Teacher(Long id,
                   String firstName,
                   String lastName,
                   Subject subject,
                   Department department) {
        super(id, firstName, lastName);
        this.subject = subject;
        this.department = department;
    }

    public Teacher(String firstName,
                   String lastName,
                   Subject subject,
                   Department department) {
        super(firstName, lastName);
        this.subject = subject;
        this.department = department;
    }

    public Teacher(Subject subject,
                   Department department) {
        this.subject = subject;
        this.department = department;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(subject);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) obj;
        return Objects.equals(subject, other.subject);
    }

    @Override
    public String toString() {
        return "Teacher [Id=" + getId()
                + ", FirstName=" + getFirstName()
                + ", LastName=" + getLastName()
                + ", email=" + getEmail()
                + ", phoneNumber=" + getPhoneNumber()
                + ", deleted=" + isDeleted()
                + ", Subject=" + subject
                + "]";
    }
}
