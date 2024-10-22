package com.transaction.entity;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class NameCodeEntity extends StatusEntity {
    @Basic(
            optional = false
    )
    @Column(
            nullable = false
    )
    protected String name;
    @Basic(
            optional = false
    )
    @Column(
            unique = true,
            nullable = false
    )
    protected String code;
}