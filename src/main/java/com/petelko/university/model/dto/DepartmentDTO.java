package com.petelko.university.model.dto;

import com.petelko.university.service.validation.annotation.UniqueField;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@UniqueField(fieldName = "name", message = "{nameField.alreadyused}")
public class DepartmentDTO {

    @ApiModelProperty(notes = "${description.field.departments.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.departments.name}", name = "name", required = true)
    @NotBlank(message = "{name.notblank}")
    @Size(min = 2, max = 30, message = "{name.size}")
    private String name;
    @ApiModelProperty(notes = "${description.field.departments.description}", name = "description", required = false)
    @Size(max = 250, message = "{description.size}")
    private String description;
    @ApiModelProperty(notes = "${description.field.departments.deleted}", name = "deleted", required = false)
    private boolean deleted;
    @ApiModelProperty(notes = "${description.field.departments.facultyid}", name = "facultyId", required = false)
    private Long facultyId;
    @ApiModelProperty(notes = "${description.field.departments.facultyname}", name = "facultyName", required = false)
    private String facultyName;
    @ApiModelProperty(notes = "${description.field.departments.facultydescription}", name = "facultyDescription", required = false)
    private String facultyDescription;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id,
                         String name,
                         String description,
                         boolean deleted,
                         Long facultyId,
                         String facultyName,
                         String facultyDescription) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyDescription = facultyDescription;
    }

    public DepartmentDTO(Long id,
                         String name,
                         String description,
                         Long facultyId,
                         String facultyName,
                         String facultyDescription) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyDescription = facultyDescription;
    }

    public DepartmentDTO(String name,
                         String description,
                         Long facultyId,
                         String facultyName,
                         String facultyDescription) {
        this.name = name;
        this.description = description;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyDescription = facultyDescription;
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

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyDescription() {
        return facultyDescription;
    }

    public void setFacultyDescription(String facultyDescription) {
        this.facultyDescription = facultyDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentDTO)) return false;
        DepartmentDTO that = (DepartmentDTO) o;
        return isDeleted() == that.isDeleted() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getFacultyId(), that.getFacultyId()) &&
                Objects.equals(getFacultyName(), that.getFacultyName()) &&
                Objects.equals(getFacultyDescription(), that.getFacultyDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getName(), getDescription(),
                isDeleted(), getFacultyId(),
                getFacultyName(),
                getFacultyDescription());
    }

    @Override
    public String toString() {
        return "DepartmentDTO[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ", faculty_id=" + facultyId +
                ", faculty_name='" + facultyName + '\'' +
                ", faculty_description='" + facultyDescription + '\'' +
                ']';
    }
}

