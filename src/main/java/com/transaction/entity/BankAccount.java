package com.transaction.entity;

import com.transaction.enums.GenericStatusConstant;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class BankAccount extends StatusEntity {
    private String bankName;
    private String accountName;
    @Column(
            nullable = false,
            length = 2048
    )
    private String accountNumber;

    private String description;

    private BigDecimal balance;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    private Currency currency;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Bank bank;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Users user;
}