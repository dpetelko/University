package com.petelko.university.model.dto;

import com.petelko.university.service.validation.annotation.UniqueField;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;


@UniqueField(fieldName = "name", message = "{nameField.alreadyused}")
public class GroupDTO {

    @ApiModelProperty(notes = "${description.field.groups.id}", name = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "${description.field.groups.name}", name = "name", required = true)
    @NotBlank(message = "{groupName.notblank}")
    @Pattern(regexp = "[A-Z][A-Z]-[1-9][1-9]", message = "{groupName.invalidformat}")
    private String name;
    @ApiModelProperty(notes = "${description.field.groups.deleted}", name = "deleted", required = false)
    private boolean deleted;
    @ApiModelProperty(notes = "${description.field.groups.departmentid}", name = "departmentId", required = false)
    private Long departmentId;
    @ApiModelProperty(notes = "${description.field.groups.departmentname}", name = "departmentName", required = false)
    private String departmentName;

    public GroupDTO(Long id,
                    String name,
                    Long departmentId,
                    String departmentName) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }


    public GroupDTO(Long id,
                    String name,
                    boolean deleted,
                    Long departmentId,
                    String departmentName) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }


    public GroupDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupDTO(String name) {
        this.name = name;
    }

    public GroupDTO() {
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupDTO)) return false;
        GroupDTO groupDTO = (GroupDTO) o;
        return getId().equals(groupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "GroupDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted=" + deleted +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
