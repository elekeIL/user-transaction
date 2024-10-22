package com.transaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransactionHistory is a Querydsl query type for TransactionHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransactionHistory extends EntityPathBase<TransactionHistory> {

    private static final long serialVersionUID = -603112924L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactionHistory transactionHistory = new QTransactionHistory("transactionHistory");

    public final QStatusEntity _super;

    public final StringPath accountNumber = createString("accountNumber");

    public final NumberPath<java.math.BigDecimal> amountPaid = createNumber("amountPaid", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final QUsers createdBy;

    public final QCurrency currency;

    public final StringPath description = createString("description");

    public final StringPath destinationAccountName = createString("destinationAccountName");

    public final StringPath destinationAccountNumber = createString("destinationAccountNumber");

    public final StringPath destinationBank = createString("destinationBank");

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastUpdatedAt;

    // inherited
    public final QUsers lastUpdatedBy;

    //inherited
    public final StringPath reasonForStatusChange;

    public final StringPath sourceAccountName = createString("sourceAccountName");

    public final StringPath sourceBank = createString("sourceBank");

    public final EnumPath<com.transaction.enums.TransactionStatus> status = createEnum("status", com.transaction.enums.TransactionStatus.class);

    public final DateTimePath<java.time.LocalDateTime> transactionDate = createDateTime("transactionDate", java.time.LocalDateTime.class);

    public final EnumPath<com.transaction.enums.TransactionType> transactionType = createEnum("transactionType", com.transaction.enums.TransactionType.class);

    public QTransactionHistory(String variable) {
        this(TransactionHistory.class, forVariable(variable), INITS);
    }

    public QTransactionHistory(Path<? extends TransactionHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactionHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactionHistory(PathMetadata metadata, PathInits inits) {
        this(TransactionHistory.class, metadata, inits);
    }

    public QTransactionHistory(Class<? extends TransactionHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QStatusEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.currency = inits.isInitialized("currency") ? new QCurrency(forProperty("currency"), inits.get("currency")) : null;
        this.id = _super.id;
        this.lastUpdatedAt = _super.lastUpdatedAt;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.reasonForStatusChange = _super.reasonForStatusChange;
    }

}

