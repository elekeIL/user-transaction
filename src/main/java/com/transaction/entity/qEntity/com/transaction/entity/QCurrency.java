package com.transaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCurrency is a Querydsl query type for Currency
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCurrency extends EntityPathBase<Currency> {

    private static final long serialVersionUID = -979648545L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCurrency currency = new QCurrency("currency");

    public final QStatusEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final QUsers createdBy;

    public final EnumPath<com.transaction.enums.CurrencyTypeConstant> currencyType = createEnum("currencyType", com.transaction.enums.CurrencyTypeConstant.class);

    //inherited
    public final NumberPath<Long> id;

    public final StringPath iso4217Code = createString("iso4217Code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastUpdatedAt;

    // inherited
    public final QUsers lastUpdatedBy;

    public final StringPath majorUnitName = createString("majorUnitName");

    public final StringPath minorUnitName = createString("minorUnitName");

    public final StringPath name = createString("name");

    //inherited
    public final StringPath reasonForStatusChange;

    //inherited
    public final EnumPath<com.transaction.enums.GenericStatus> status;

    public final StringPath symbol = createString("symbol");

    public QCurrency(String variable) {
        this(Currency.class, forVariable(variable), INITS);
    }

    public QCurrency(Path<? extends Currency> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCurrency(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCurrency(PathMetadata metadata, PathInits inits) {
        this(Currency.class, metadata, inits);
    }

    public QCurrency(Class<? extends Currency> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QStatusEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.id = _super.id;
        this.lastUpdatedAt = _super.lastUpdatedAt;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.reasonForStatusChange = _super.reasonForStatusChange;
        this.status = _super.status;
    }

}

