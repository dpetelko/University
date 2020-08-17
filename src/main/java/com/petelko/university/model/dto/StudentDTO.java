package com.petelko.university.model.dto;

import com.petelko.university.service.validation.annotation.MaxStudentsAtGroup;
import com.petelko.university.service.validation.annotation.UniqueField;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@MaxStudentsAtGroup(message = "{student.limitexceeded}")
@UniqueField(fieldName = "email", message = "{email.alreadyused}")
public class StudentDTO {

    @ApiModelProperty(notes = "${description.field.students.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.students.firstname}", name = "firstName", required = true)
    @NotBlank(message = "{firstName.notblank}")
    @Size(min = 2, max = 20, message = "{lastName.size}")
    private String firstName;
    @ApiModelProperty(notes = "${description.field.students.lastname}", name = "lastName", required = true)
    @NotBlank(message = "{lastName.notblank}")
    @Size(min = 2, max = 20, message = "{lastName.size}")
    private String lastName;
    @ApiModelProperty(notes = "${description.field.students.deleted}", name = "deleted", required = false)
    private boolean deleted;
    @ApiModelProperty(notes = "${description.field.students.email}", name = "email", required = true)
    @NotBlank(message = "{email.notblank}")
    @Email(message = "{email.incorrect}")
    private String email;
    @ApiModelProperty(notes = "${description.field.students.phone}", name = "phoneNumber", required = true)
    @NotBlank(message = "{phoneNumber.notblank}")
    @Pattern(regexp = "\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|\r\n" +
            "2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|\r\n" +
            "4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$",
            message = "{phoneNumber.invalidformat}")
    private String phoneNumber;
    @ApiModelProperty(notes = "${description.field.students.groupid}", name = "groupId", required = false)
    private Long groupId;
    @ApiModelProperty(notes = "${description.field.students.groupname}", name = "groupName", required = false)
    private String groupName;

    public StudentDTO(Long id,
                      String firstName,
                      String lastName,
                      boolean deleted,
                      String email,
                      String phoneNumber,
                      Long groupId,
                      String groupName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public StudentDTO(String firstName,
                      String lastName,
                      boolean deleted,
                      String email,
                      String phoneNumber,
                      Long groupId,
                      String groupName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public StudentDTO(String firstName,
                      String lastName,
                      String email,
                      String phoneNumber,
                      Long groupId,
                      String groupName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
        this.groupName = groupName;
    }


    public StudentDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      String phoneNumber,
                      Long groupId,
                      String groupName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
        this.groupName = groupName;
    }


    public StudentDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StudentDTO(String firstName,
                      String lastName,
                      Long groupId,
                      String groupName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public StudentDTO(Long id,
                      String firstName,
                      String lastName,
                      boolean deleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
    }

    public StudentDTO() {
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

    public boolean getDeleted() {
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, groupId, groupName, id, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StudentDTO)) {
            return false;
        }
        StudentDTO other = (StudentDTO) obj;
        return Objects.equals(firstName, other.firstName) && groupId == other.groupId
                && Objects.equals(groupName, other.groupName) && id == other.id
                && Objects.equals(lastName, other.lastName);
    }

    @Override
    public String toString() {
        return "StudentDTO [id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", email=" + email
                + ", phoneNumber=" + phoneNumber
                + ", groupId=" + groupId
                + ", groupName=" + groupName
                + "]";
    }
}
