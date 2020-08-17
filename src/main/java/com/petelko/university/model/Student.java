package com.petelko.university.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends Person {

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "group_id")
    private Group group;



    public Student(Long id,
                   String firstName,
                   String lastName,
                   boolean deleted,
                   String email,
                   String phoneNumber,
                   Group group) {
        super(id, firstName, lastName, deleted, email, phoneNumber);
        this.group = group;
    }
    
    public Student(Long id,
                   String firstName,
                   String lastName,
                   String email,
                   String phoneNumber,
                   Group group) {
        super(id, firstName, lastName, email, phoneNumber);
        this.group = group;
    }
    
    public Student(String firstName,
                   String lastName,
                   String email,
                   String phoneNumber,
                   Group group) {
        super(firstName, lastName, email, phoneNumber);
        this.group = group;
    }

    public Student(Long id,
                   String firstName,
                   String lastName,
                   Group group) {
        super(id, firstName, lastName);
        this.group = group;
    }

    public Student(String firstName,
                   String lastName,
                   boolean deleted,
                   Group group) {
        super(firstName, lastName, deleted);
        this.group = group;
    }

    public Student(String firstName,
                   String lastName,
                   Group group) {
        super(firstName, lastName);
        this.group = group;
    }

    public Student() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student [Id=" + getId()
                + ", FirstName=" + getFirstName()
                + ", LastName=" + getLastName()
                + ", email=" + getEmail()
                + ", phoneNumber=" + getPhoneNumber()
                + ", deleted=" + isDeleted()
                + ", Group=" + group
                + "]";
    }
}
