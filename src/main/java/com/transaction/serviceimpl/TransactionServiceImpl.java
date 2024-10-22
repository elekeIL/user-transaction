package com.transaction.serviceimpl;

import com.transaction.dto.NewTransactionDto;
import com.transaction.entity.Bank;
import com.transaction.entity.BankAccount;
import com.transaction.entity.Currency;
import com.transaction.entity.TransactionHistory;
import com.transaction.enums.GenericStatus;
import com.transaction.enums.TransactionStatus;
import com.transaction.enums.TransactionType;
import com.transaction.repository.BankAccountRepository;
import com.transaction.repository.BankRepository;
import com.transaction.repository.CurrencyRepository;
import com.transaction.repository.app.AppRepository;
import com.transaction.services.TransactionService;
import com.transaction.utils.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final BankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;
    private final CurrencyRepository currencyRepository;
    private final AppRepository appRepository;
    @Override
    public TransactionHistory executeTransaction(NewTransactionDto transactionDto, String userAccountNumber, String userBankCode) {
        Bank bank = bankRepository.findByCode(userBankCode).orElseThrow(()-> new ErrorResponse(HttpStatus.BAD_REQUEST, "Bank not found"));
        BankAccount bankAccount = bankAccountRepository.findFirstByAccountNumberAndBank(userAccountNumber, bank)
                .orElseThrow(()-> new ErrorResponse(HttpStatus.BAD_REQUEST, "No account with the specified details found"));

        Currency currency = currencyRepository.findByName(transactionDto.getCurrency()).orElseThrow(()-> new ErrorResponse(HttpStatus.BAD_REQUEST, "Specified currency not found"));

        if (transactionDto.getAmount() == null || transactionDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid transaction amount");
        }

        if (transactionDto.getTransactionType() == TransactionType.DEBIT
                && bankAccount.getBalance().compareTo(transactionDto.getAmount()) < 0) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Insufficient funds");

        }

        if (transactionDto.getTransactionType() == TransactionType.DEBIT) {
            bankAccount.setBalance(bankAccount.getBalance().subtract(transactionDto.getAmount()));
        } else if (transactionDto.getTransactionType() == TransactionType.CREDIT) {
            bankAccount.setBalance(bankAccount.getBalance().add(transactionDto.getAmount()));
        }

        TransactionHistory transaction = new TransactionHistory();
        transaction.setAmountPaid(transactionDto.getAmount());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setUserBackAccount(bankAccount);
        transaction.setTransactingBank(transactionDto.getTransactingBank());
        transaction.setTransactionAccountName(transactionDto.getTransactingAccountName());
        transaction.setTransactingAccountNumber(transactionDto.getTransactingAccountNumber());
        transaction.setCurrency(currency);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(GenericStatus.ACTIVE);

        appRepository.persist(transaction);

        return transaction;
    }

}
