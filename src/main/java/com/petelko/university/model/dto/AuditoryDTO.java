package com.petelko.university.model.dto;

import com.petelko.university.service.validation.annotation.UniqueField;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@UniqueField(fieldName = "name", message = "{nameField.alreadyused}")
public class AuditoryDTO {

    @ApiModelProperty(notes = "${description.field.auditories.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.auditories.name}", name = "name", required = true)
    @NotBlank(message = "{name.notblank}")
    @Pattern(regexp = "[A-Z]-[1-9][0-9][1-9]", message = "{auditoryName.invalidformat}")
    private String name;
    @ApiModelProperty(notes = "${description.field.auditories.description}", name = "description", required = false)
    @Size(max = 250, message = "{description.size}")
    private String description;
    @ApiModelProperty(notes = "${description.field.auditories.capacity}", name = "deleted", required = true)
    private int capacity;
    @ApiModelProperty(notes = "${description.field.auditories.deleted}", name = "deleted", required = false)
    private boolean deleted;

    public AuditoryDTO(Long id,
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

    public AuditoryDTO() {
    }

    public AuditoryDTO(Long id,
                       String name,
                       String description,
                       int capacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }

    public AuditoryDTO(String name,
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
        if (!(o instanceof AuditoryDTO)) return false;
        AuditoryDTO that = (AuditoryDTO) o;
        return getCapacity() == that.getCapacity() &&
                isDeleted() == that.isDeleted() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
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
        return "AuditoryDTO[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", deleted=" + deleted +
                ']';
    }
}
