package com.petelko.university.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;



    public Person(Long id,
                  String firstName,
                  String lastName,
                  boolean deleted,
                  String email,
                  String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    public Person(Long id,
                  String firstName,
                  String lastName,
                  String email,
                  String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    public Person(String firstName,
                  String lastName,
                  String email,
                  String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Person(String firstName,
                  String lastName,
                  boolean deleted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
    }

    public Person(Long id,
                  String firstName,
                  String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName,
                  String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, id, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        Person other = (Person) obj;
        return Objects.equals(firstName, other.firstName) &&
                Objects.equals(id, other.id) &&
                Objects.equals(email, other.email) &&
                Objects.equals(lastName, other.lastName);
    }

    @Override
    public String toString() {
        return "Person [id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", email=" + email
                + ", phoneNumber=" + phoneNumber
                + "]";
    }

}
