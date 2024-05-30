package com.example.mediready.domain.likePharmacist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikePharmacistId is a Querydsl query type for LikePharmacistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLikePharmacistId extends BeanPath<LikePharmacistId> {

    private static final long serialVersionUID = 1034986008L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikePharmacistId likePharmacistId = new QLikePharmacistId("likePharmacistId");

    public final com.example.mediready.domain.pharmacist.QPharmacist pharmacist;

    public final com.example.mediready.domain.user.QUser user;

    public QLikePharmacistId(String variable) {
        this(LikePharmacistId.class, forVariable(variable), INITS);
    }

    public QLikePharmacistId(Path<? extends LikePharmacistId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikePharmacistId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikePharmacistId(PathMetadata metadata, PathInits inits) {
        this(LikePharmacistId.class, metadata, inits);
    }

    public QLikePharmacistId(Class<? extends LikePharmacistId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pharmacist = inits.isInitialized("pharmacist") ? new com.example.mediready.domain.pharmacist.QPharmacist(forProperty("pharmacist"), inits.get("pharmacist")) : null;
        this.user = inits.isInitialized("user") ? new com.example.mediready.domain.user.QUser(forProperty("user")) : null;
    }

}

