package com.transaction.sequences;

import com.transaction.qualifier.BankCodeSequence;
import com.transaction.utils.etc.SequenceGeneratorImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Locale;

@Named
@BankCodeSequence
public class BankCodeSequenceGenerator extends SequenceGeneratorImpl {

    @Inject
    public BankCodeSequenceGenerator(@Qualifier("defaultEntityManager") EntityManager entityManager, @Qualifier("defaultTransactionTemplate") TransactionTemplate transactionTemplate) {
        super(entityManager, transactionTemplate, "bank_identifier_sequence");
    }

    @Override
    public String getNext() {
        return String.format(Locale.ENGLISH, "BAK%010d", getNextLong());
    }
}

