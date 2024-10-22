package com.transaction.entity;

import com.transaction.enums.TransactionStatus;
import com.transaction.enums.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class TransactionHistory extends StatusEntity {
    private BigDecimal amountPaid;
    private LocalDateTime transactionDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount userBackAccount;
    private String transactingBank;
    private String transactingAccountNumber;
    private String transactionAccountName;
    private String transactionReference;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

}
