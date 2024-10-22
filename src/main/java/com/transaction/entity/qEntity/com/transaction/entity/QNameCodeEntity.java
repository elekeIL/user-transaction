package com.transaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNameCodeEntity is a Querydsl query type for NameCodeEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QNameCodeEntity extends EntityPathBase<NameCodeEntity> {

    private static final long serialVersionUID = 12499561L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNameCodeEntity nameCodeEntity = new QNameCodeEntity("nameCodeEntity");

    public final QStatusEntity _super;

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final QUsers createdBy;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastUpdatedAt;

    // inherited
    public final QUsers lastUpdatedBy;

    public final StringPath name = createString("name");

    //inherited
    public final StringPath reasonForStatusChange;

    //inherited
    public final EnumPath<com.transaction.enums.GenericStatus> status;

    public QNameCodeEntity(String variable) {
        this(NameCodeEntity.class, forVariable(variable), INITS);
    }

    public QNameCodeEntity(Path<? extends NameCodeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNameCodeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNameCodeEntity(PathMetadata metadata, PathInits inits) {
        this(NameCodeEntity.class, metadata, inits);
    }

    public QNameCodeEntity(Class<? extends NameCodeEntity> type, PathMetadata metadata, PathInits inits) {
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

