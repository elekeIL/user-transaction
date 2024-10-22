package com.transaction.services;

import com.transaction.dto.NewTransactionDto;
import com.transaction.entity.TransactionHistory;

public interface TransactionService {


    TransactionHistory executeTransaction(NewTransactionDto transactionDto, String userAccountNumber, String userBankCode);
}
