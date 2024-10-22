package com.transaction.repository;

import com.transaction.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findFirstByIso4217CodeIgnoreCase(String string);


    @Query("select c from Currency as c where lower(c.name) = lower(?1)")
    Optional<Currency> findByName(String string);

    Currency getById(long id);

    List<Currency> findByIso4217CodeIn(Set<String> codes);

    List<Currency> findByIdIn(Set<Long> ids);

    Optional<Currency> findFirstByIso4217Code(String iso4217Code);
    Optional<Currency> findByIso4217CodeIgnoreCase(String iso4217Code);
    List<Currency> findByIso4217CodeIgnoreCaseIn(List<String> iso4217Code);

}
