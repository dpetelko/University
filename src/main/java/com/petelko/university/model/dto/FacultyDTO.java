package com.petelko.university.model.dto;

import com.petelko.university.service.validation.annotation.UniqueField;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@UniqueField(fieldName = "name", message = "{nameField.alreadyused}")
public class FacultyDTO {

    @ApiModelProperty(notes = "${description.field.faculties.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.faculties.name}", name = "name", required = true)
    @NotBlank(message = "{name.notblank}")
    @Size(min = 2, max = 30, message = "{name.size}")
    private String name;
    @ApiModelProperty(notes = "${description.field.faculties.description}", name = "description", required = false)
    @Size(max = 250, message = "{description.size}")
    private String description;
    @ApiModelProperty(notes = "${description.field.faculties.deleted}", name = "deleted", required = false)
    private boolean deleted;

    public FacultyDTO() {
    }

    public FacultyDTO(Long id, String name, String description, boolean deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
    }

    public FacultyDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public FacultyDTO(String name, String description) {
        this.name = name;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacultyDTO)) return false;
        FacultyDTO that = (FacultyDTO) o;
        return isDeleted() == that.isDeleted() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getName(),
                getDescription(),
                isDeleted());
    }

    @Override
    public String toString() {
        return "FacultyDTO[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ']';
    }
}
