package com.transaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStatusEntity is a Querydsl query type for StatusEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QStatusEntity extends EntityPathBase<StatusEntity> {

    private static final long serialVersionUID = 952408899L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStatusEntity statusEntity = new QStatusEntity("statusEntity");

    public final QBaseEntity _super;

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

    public final StringPath reasonForStatusChange = createString("reasonForStatusChange");

    public final EnumPath<com.transaction.enums.GenericStatus> status = createEnum("status", com.transaction.enums.GenericStatus.class);

    public QStatusEntity(String variable) {
        this(StatusEntity.class, forVariable(variable), INITS);
    }

    public QStatusEntity(Path<? extends StatusEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStatusEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStatusEntity(PathMetadata metadata, PathInits inits) {
        this(StatusEntity.class, metadata, inits);
    }

    public QStatusEntity(Class<? extends StatusEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.id = _super.id;
        this.lastUpdatedAt = _super.lastUpdatedAt;
        this.lastUpdatedBy = _super.lastUpdatedBy;
    }

}

