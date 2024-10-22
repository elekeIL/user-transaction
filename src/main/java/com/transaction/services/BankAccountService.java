package com.transaction.services;

import com.transaction.dto.NewBankAccountDto;
import com.transaction.pojo.BankAccountPojo;

import java.math.BigDecimal;

public interface BankAccountService {
    BankAccountPojo createBankAccount(NewBankAccountDto bankAccountDto);

    BankAccountPojo getAvailableBalance(String accountNumber, String bankCode);
}
