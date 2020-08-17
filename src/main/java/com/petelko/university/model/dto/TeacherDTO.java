package com.petelko.university.model.dto;

import com.petelko.university.service.validation.annotation.UniqueField;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@UniqueField(fieldName = "email", message = "{email.alreadyused}")
public class TeacherDTO {

    @ApiModelProperty(notes = "${description.field.teachers.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.teachers.firstname}", name = "firstName", required = true)
    @NotBlank(message = "{firstName.notblank}")
    @Size(min = 2, max = 20, message = "{lastName.size}")
    private String firstName;
    @ApiModelProperty(notes = "${description.field.teachers.lastname}", name = "lastName", required = true)
    @NotBlank(message = "{lastName.notblank}")
    @Size(min = 2, max = 20, message = "{lastName.size}")
    private String lastName;
    @ApiModelProperty(notes = "${description.field.teachers.deleted}", name = "deleted", required = false)
    private boolean deleted;
    @ApiModelProperty(notes = "${description.field.teachers.email}", name = "email", required = true)
    @NotBlank(message = "{email.notblank}")
    @Email(message = "{email.incorrect}")
    private String email;
    @ApiModelProperty(notes = "${description.field.teachers.phone}", name = "phoneNumber", required = true)
    @NotBlank(message = "{phoneNumber.notblank}")
    @Pattern(regexp = "\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|\r\n" +
            "2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|\r\n" +
            "4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$",
            message = "{phoneNumber.invalidformat}")
    private String phoneNumber;
    @ApiModelProperty(notes = "${description.field.teachers.subjectid}", name = "subjectId", required = false)
    private Long subjectId;
    @ApiModelProperty(notes = "${description.field.teachers.subjectname}", name = "subjectName", required = false)
    private String subjectName;
    @ApiModelProperty(notes = "${description.field.teachers.subjectdescription}", name = "subjectDescription", required = false)
    private String subjectDescription;
    @ApiModelProperty(notes = "${description.field.teachers.departmentid}", name = "departmentId", required = false)
    private Long departmentId;
    @ApiModelProperty(notes = "${description.field.teachers.departmentname}", name = "departmentName", required = false)
    private String departmentName;
    @ApiModelProperty(notes = "${description.field.teachers.departmentdescription}", name = "departmentDescription", required = false)
    private String departmentDescription;

    public TeacherDTO(Long id,
                      String firstName,
                      String lastName,
                      boolean deleted,
                      String email,
                      String phoneNumber,
                      Long subjectId,
                      String subjectName,
                      String subjectDescription,
                      Long departmentId,
                      String departmentName,
                      String departmentDescription) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
    }

    public TeacherDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      String phoneNumber,
                      Long subjectId,
                      String subjectName,
                      String subjectDescription,
                      Long departmentId,
                      String departmentName,
                      String departmentDescription) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
    }

    public TeacherDTO(Long id,
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

    public TeacherDTO() {
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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeacherDTO)) return false;
        TeacherDTO that = (TeacherDTO) o;
        return isDeleted() == that.isDeleted() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
                Objects.equals(getSubjectId(), that.getSubjectId()) &&
                Objects.equals(getSubjectName(), that.getSubjectName()) &&
                Objects.equals(getSubjectDescription(), that.getSubjectDescription()) &&
                Objects.equals(getDepartmentId(), that.getDepartmentId()) &&
                Objects.equals(getDepartmentName(), that.getDepartmentName()) &&
                Objects.equals(getDepartmentDescription(), that.getDepartmentDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getFirstName(),
                getLastName(),
                isDeleted(),
                getEmail(),
                getPhoneNumber(),
                getSubjectId(),
                getSubjectName(),
                getSubjectDescription(),
                getDepartmentId(),
                getDepartmentName(),
                getDepartmentDescription());
    }

    @Override
    public String toString() {
        return "TeacherDTO[" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", deleted=" + deleted +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectDescription='" + subjectDescription + '\'' +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", departmentDescription='" + departmentDescription + '\'' +
                ']';
    }
}

