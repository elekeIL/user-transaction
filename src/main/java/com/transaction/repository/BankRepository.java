package com.transaction.repository;

import com.transaction.entity.Bank;
import com.transaction.enums.GenericStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByCode(String code);

    Optional<Bank> findFirstByCode(String code);

    List<Bank> findAllByStatus(GenericStatusConstant status);

    Optional<Bank> findByFwCodeAndStatus(String bankCode, GenericStatusConstant active);
    Optional<Bank> findFirstByFwIdAndStatus(String fwId, GenericStatusConstant active);
}
