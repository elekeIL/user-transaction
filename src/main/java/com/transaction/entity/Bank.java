package com.transaction.entity;

import com.transaction.enums.BankTypeConstant;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Bank extends NameCodeEntity {
    @Basic
    private String shortName;
    @Basic
    private String fwCode;
    @Basic
    private String fwId;
    @Basic
    private Boolean fwRequiresBankBranch;
    @Basic
    @Enumerated(EnumType.STRING)
    private BankTypeConstant type;
}
