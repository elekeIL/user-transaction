package com.transaction.entity;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Setting extends BaseEntity {
    @Basic(
            optional = false
    )
    @Column(
            nullable = false
    )
    private @NotBlank String name;
    @Basic(
            optional = false
    )
    @Column(
            nullable = false,
            length = 2048
    )
    private String value;
    @Basic
    private String description;
    @Basic
    private Boolean editable;
}
