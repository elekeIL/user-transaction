package com.transaction.repository;

;
import com.transaction.entity.Bank;
import com.transaction.entity.BankAccount;
import com.transaction.enums.GenericStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    @Query("select b from BankAccount b where b.accountName = ?1")
    BankAccount findByAccountName(String name);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BankAccount> findFirstByAccountNumberAndBank(String accountNumber, Bank bank);

    Optional<BankAccount> findByIdAndStatus(Long id, GenericStatus status);
}
