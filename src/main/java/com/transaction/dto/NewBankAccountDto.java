package com.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NewBankAccountDto {
    private String bankCode;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerEmail;
    private String currency;
    private BigDecimal openingAmount;
    private String description;
}
