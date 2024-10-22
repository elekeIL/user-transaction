package com.transaction.entity;

import com.transaction.enums.CurrencyTypeConstant;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Currency extends StatusEntity {
    @Basic(
            optional = false
    )
    @Column(
            nullable = false
    )
    private String name;
    @Basic(
            optional = false
    )
    @Column(
            unique = true,
            nullable = false
    )
    private String iso4217Code;
    @Basic(
            optional = false
    )
    @Column(
            nullable = false
    )
    private String symbol;
    @Basic
    private String majorUnitName;
    @Basic
    @Enumerated(EnumType.STRING)
    private CurrencyTypeConstant currencyType;
    @Basic
    private String minorUnitName;
}