package com.transaction.serviceimpl;

import com.transaction.dto.NewBankAccountDto;
import com.transaction.entity.Bank;
import com.transaction.entity.BankAccount;
import com.transaction.entity.Currency;
import com.transaction.entity.Users;
import com.transaction.enums.GenericStatus;
import com.transaction.pojo.BankAccountPojo;
import com.transaction.repository.BankAccountRepository;
import com.transaction.repository.BankRepository;
import com.transaction.repository.CurrencyRepository;
import com.transaction.repository.UserRepository;
import com.transaction.repository.app.AppRepository;
import com.transaction.services.BankAccountService;
import com.transaction.utils.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Service

public class BankAccountServiceImpl implements BankAccountService {
    private final BankRepository bankRepository;
    private final CurrencyRepository currencyRepository;
    private final AppRepository appRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private static final int ACCOUNT_NUMBER_LENGTH = 10;


    @Override
    public BankAccountPojo createBankAccount(NewBankAccountDto bankAccountDto) {
        Users user = userRepository.findByEmail(bankAccountDto.getOwnerEmail()).orElseThrow(()-> new ErrorResponse(HttpStatus.BAD_REQUEST, "User not registered, please register before creating an account"));
        Bank bank = bankRepository.findByCode(bankAccountDto.getBankCode()).orElseThrow(()-> new ErrorResponse(HttpStatus.BAD_REQUEST, "Bank not found"));
        Currency currency = currencyRepository.findByName(bankAccountDto.getCurrency()).orElseThrow(()-> new ErrorResponse(HttpStatus.BAD_REQUEST, "Specified currency not found"));
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountName(user.getDisplayName().toUpperCase());
        bankAccount.setAccountNumber(generateAccountNumber());
        bankAccount.setBank(bank);
        bankAccount.setBankName(bank.getShortName());
        bankAccount.setCreatedAt(LocalDateTime.now());
        bankAccount.setBalance(bankAccountDto.getOpeningAmount());
        bankAccount.setCurrency(currency);

        if(StringUtils.isNotBlank(bankAccountDto.getDescription())){
            bankAccount.setDescription(bankAccount.getDescription());
        }
        bankAccount.setStatus(GenericStatus.ACTIVE);
        bankAccount.setUser(user);

        appRepository.persist(bankAccount);

        return new BankAccountPojo(bankAccount.getAccountName(), bankAccount.getAccountNumber(), currency.getName(), bankAccount.getBalance());
    }


    @Override
    public BankAccountPojo getAvailableBalance(String accountNumber, String bankCode) {
        Bank bank = bankRepository.findByCode(bankCode).orElseThrow(()-> new ErrorResponse(HttpStatus.BAD_REQUEST, "Bank not found"));
        BankAccount bankAccount = bankAccountRepository.findFirstByAccountNumberAndBank(accountNumber, bank).orElseThrow(()-> new ErrorResponse(HttpStatus.BAD_REQUEST, "Not account details found for teh specified input"));
        Currency currency = appRepository.unproxy(Currency.class, bankAccount.getCurrency());
        return new BankAccountPojo(bankAccount.getAccountName(), bankAccount.getAccountNumber(), currency.getName(), bankAccount.getBalance());
    }


    public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }
        return accountNumber.toString();
    }
}
