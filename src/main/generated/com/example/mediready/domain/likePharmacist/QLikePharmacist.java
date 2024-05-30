package com.example.mediready.domain.likePharmacist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikePharmacist is a Querydsl query type for LikePharmacist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikePharmacist extends EntityPathBase<LikePharmacist> {

    private static final long serialVersionUID = 63646749L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikePharmacist likePharmacist = new QLikePharmacist("likePharmacist");

    public final QLikePharmacistId id;

    public final BooleanPath status = createBoolean("status");

    public QLikePharmacist(String variable) {
        this(LikePharmacist.class, forVariable(variable), INITS);
    }

    public QLikePharmacist(Path<? extends LikePharmacist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikePharmacist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikePharmacist(PathMetadata metadata, PathInits inits) {
        this(LikePharmacist.class, metadata, inits);
    }

    public QLikePharmacist(Class<? extends LikePharmacist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QLikePharmacistId(forProperty("id"), inits.get("id")) : null;
    }

}

