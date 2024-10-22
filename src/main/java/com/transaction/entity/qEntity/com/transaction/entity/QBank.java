package com.transaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBank is a Querydsl query type for Bank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBank extends EntityPathBase<Bank> {

    private static final long serialVersionUID = -1802900214L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBank bank = new QBank("bank");

    public final QNameCodeEntity _super;

    //inherited
    public final StringPath code;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final QUsers createdBy;

    public final StringPath fwCode = createString("fwCode");

    public final StringPath fwId = createString("fwId");

    public final BooleanPath fwRequiresBankBranch = createBoolean("fwRequiresBankBranch");

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastUpdatedAt;

    // inherited
    public final QUsers lastUpdatedBy;

    //inherited
    public final StringPath name;

    //inherited
    public final StringPath reasonForStatusChange;

    public final StringPath shortName = createString("shortName");

    //inherited
    public final EnumPath<com.transaction.enums.GenericStatus> status;

    public final EnumPath<com.transaction.enums.BankTypeConstant> type = createEnum("type", com.transaction.enums.BankTypeConstant.class);

    public QBank(String variable) {
        this(Bank.class, forVariable(variable), INITS);
    }

    public QBank(Path<? extends Bank> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBank(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBank(PathMetadata metadata, PathInits inits) {
        this(Bank.class, metadata, inits);
    }

    public QBank(Class<? extends Bank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QNameCodeEntity(type, metadata, inits);
        this.code = _super.code;
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.id = _super.id;
        this.lastUpdatedAt = _super.lastUpdatedAt;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.name = _super.name;
        this.reasonForStatusChange = _super.reasonForStatusChange;
        this.status = _super.status;
    }

}

