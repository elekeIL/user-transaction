package com.transaction.dto;

import com.transaction.entity.Currency;
import com.transaction.enums.TransactionStatus;
import com.transaction.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
@Getter
@Setter
public class NewTransactionDto {
    private BigDecimal amount;
    private String description;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private String transactingBank;
    private String transactingAccountNumber;
    private String transactingAccountName;
    private String currency;
}
