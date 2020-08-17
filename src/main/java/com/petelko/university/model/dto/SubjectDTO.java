package com.petelko.university.model.dto;

import com.petelko.university.service.validation.annotation.UniqueField;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@UniqueField(fieldName = "name", message = "{nameField.alreadyused}")
public class SubjectDTO {

    @ApiModelProperty(notes = "${description.field.subjects.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.subjects.name}", name = "name", required = true)
    @NotBlank(message = "{name.notblank}")
    @Size(min = 2, max = 30, message = "{name.size}")
    private String name;
    @ApiModelProperty(notes = "${description.field.subjects.description}", name = "description", required = false)
    @Size(max = 250, message = "{description.size}")
    private String description;
    @ApiModelProperty(notes = "${description.field.subjects.deleted}", name = "deleted", required = false)
    private boolean deleted;
    @ApiModelProperty(notes = "${description.field.subjects.departmentid}", name = "departmentId", required = false)
    private Long departmentId;
    @ApiModelProperty(notes = "${description.field.subjects.departmentname}", name = "departmentName", required = false)
    private String departmentName;
    @ApiModelProperty(notes = "${description.field.subjects.departmentdescription}", name = "departmentDescription", required = false)
    private String departmentDescription;

    public SubjectDTO(Long id,
                      String name,
                      boolean deleted,
                      String description,
                      Long departmentId,
                      String departmentName,
                      String departmentDescription) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
        this.description = description;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
    }

    public SubjectDTO(Long id,
                      String name,
                      String description,
                      Long departmentId,
                      String departmentName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public SubjectDTO(String name,
                      boolean deleted,
                      String description,
                      Long departmentId,
                      String departmentName,
                      String departmentDescription) {
        this.name = name;
        this.deleted = deleted;
        this.description = description;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
    }

    public SubjectDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SubjectDTO(String name) {
        this.name = name;
    }

    public SubjectDTO() {
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof SubjectDTO)) return false;
        SubjectDTO that = (SubjectDTO) o;
        return isDeleted() == that.isDeleted() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isDeleted(), getDescription());
    }

    @Override
    public String toString() {
        return "SubjectDTO[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted=" + deleted +
                ", description='" + description + '\'' +
                ']';
    }
}
