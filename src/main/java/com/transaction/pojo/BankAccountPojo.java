package com.transaction.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountPojo {
    private String accountNumber;
    private String accountName;
    private String currencyType;
    private BigDecimal availableBalance;

    public BankAccountPojo(String accountNumber, String accountName, String currencyType, BigDecimal availableBalance){
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.currencyType = currencyType;
        this.availableBalance = availableBalance;
    }
}
