package com.transaction.startup;

import com.google.gson.Gson;

import com.transaction.entity.Bank;
import com.transaction.entity.QBank;
import com.transaction.enums.CurrencyTypeConstant;
import com.transaction.enums.GenericStatus;
import com.transaction.enums.GenericStatusConstant;
import com.transaction.repository.BankRepository;
import com.transaction.repository.CurrencyRepository;
import com.transaction.repository.app.AppRepository;
import com.transaction.startup.data.BankJsonDto;
import com.transaction.startup.data.CurrencyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

;

@Slf4j
@RequiredArgsConstructor
@Named
public class MasterRecordsLoader {
    private final Gson gson;
    private final AppRepository appRepository;
    private final BankRepository bankRepository;
    private final CurrencyRepository currencyRepository;


    @PostConstruct
    public void init() throws IOException {
        loadBanks();
        loadCurrencies();
    }


    private void loadBanks() throws IOException {
        System.out.println("Loading banks...");
        String filePath = "/master_records/banks.json";
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath)))) {
            List<Bank> banks = appRepository.startJPAQuery(QBank.bank).fetch();
            Map<String, Bank> bankCodeMap = banks.stream().collect(Collectors.toMap(Bank::getCode, Function.identity()));

            BankJsonDto[] banksJson = gson.fromJson(gson.newJsonReader(reader), BankJsonDto[].class);

            List<Bank> newBanks = new ArrayList<>();
            for (BankJsonDto b : banksJson) {
                Bank existBank = bankCodeMap.get(b.getCode().toUpperCase());

                if (existBank == null) {
                    Bank bank = new Bank();
                    bank.setName(b.getName().toUpperCase());
                    bank.setCode(b.getCode().toUpperCase());
                    bank.setType(b.getBankType());
                    bank.setStatus(GenericStatus.ACTIVE);
                    bank.setCreatedAt(LocalDateTime.now());
                    bank.setFwId(b.getFwId());
                    bank.setFwCode(b.getFwCode());
                    newBanks.add(bank);
                } else {
                    if (existBank.getType() == null) {
                        existBank.setType(b.getBankType());
                    }
                    if (StringUtils.isNotBlank(b.getFwId()) && StringUtils.isNotBlank(b.getFwCode())) {
                        existBank.setFwId(b.getFwId());
                        existBank.setFwCode(b.getFwCode());
                        bankRepository.save(existBank);
                    }
                }
            }
            bankRepository.saveAll(newBanks);
            System.out.println(newBanks.size() + " banks successfully loaded.");
        }
    }

    private void loadCurrencies() throws IOException {
        System.out.println("Loading currencies...");
        String filePath = "/master_records/currency.json";
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath)))) {
            CurrencyDto[] currencies = gson.fromJson(gson.newJsonReader(reader), CurrencyDto[].class);
            List<com.transaction.entity.Currency> existingCurrencies = currencyRepository.findAll();

            existingCurrencies.forEach(currency -> {
                Optional<CurrencyDto> optionalCurrency = Arrays.stream(currencies)
                        .filter(c -> c.getIso4217Code().equalsIgnoreCase(currency.getIso4217Code()))
                        .findFirst();

                optionalCurrency.ifPresent(c -> {
                    if (currency.getMajorUnitName().isEmpty()) {
                        currency.setMinorUnitName(c.getMinorUnitName());
                        currency.setMajorUnitName(c.getMajorUnitName());
                    }
                    if (currency.getCurrencyType() != null) {
                        currency.setCurrencyType(CurrencyTypeConstant.valueOf(c.getCurrencyType()));
                    }
                    currencyRepository.save(currency);
                });
            });
            for (CurrencyDto c : currencies) {
                if (!c.getCurrencyType().equals("CRYPTO")) {
                    if (existingCurrencies.stream().noneMatch(currency -> currency.getIso4217Code().equalsIgnoreCase(c.getIso4217Code()))) {
                        com.transaction.entity.Currency currency = new com.transaction.entity.Currency();
                        currency.setName(c.getName());
                        currency.setIso4217Code(c.getIso4217Code());
                        currency.setStatus(GenericStatus.ACTIVE);
                        currency.setSymbol(c.getSymbol());
                        currency.setCreatedAt(LocalDateTime.now());
                        currency.setMajorUnitName(c.getMajorUnitName());
                        currency.setMinorUnitName(c.getMinorUnitName());
                        currency.setCurrencyType(CurrencyTypeConstant.valueOf(c.getCurrencyType()));
                        currencyRepository.save(currency);
                        existingCurrencies.add(currency);
                    }
                } else {
                    if (existingCurrencies.stream().noneMatch(currency -> currency.getName().equalsIgnoreCase(c.getName()))) {
                        com.transaction.entity.Currency currency = new com.transaction.entity.Currency();
                        currency.setName(c.getName());
                        currency.setIso4217Code(c.getIso4217Code());
                        currency.setStatus(GenericStatus.ACTIVE);
                        currency.setSymbol(c.getSymbol());
                        currency.setCreatedAt(LocalDateTime.now());
                        currency.setMajorUnitName(c.getMajorUnitName());
                        currency.setMinorUnitName(c.getMinorUnitName());
                        currency.setCurrencyType(CurrencyTypeConstant.valueOf(c.getCurrencyType()));
                        currencyRepository.save(currency);
                        existingCurrencies.add(currency);
                    }
                }
            }

            System.out.println("Currencies successfully loaded.");
        }
    }






}
