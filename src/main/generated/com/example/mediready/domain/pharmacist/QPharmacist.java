package com.example.mediready.domain.pharmacist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPharmacist is a Querydsl query type for Pharmacist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPharmacist extends EntityPathBase<Pharmacist> {

    private static final long serialVersionUID = 1820429565L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPharmacist pharmacist = new QPharmacist("pharmacist");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath licenseFileUrl = createString("licenseFileUrl");

    public final StringPath location = createString("location");

    public final NumberPath<Integer> mannerScore = createNumber("mannerScore", Integer.class);

    public final com.example.mediready.domain.user.QUser user;

    public QPharmacist(String variable) {
        this(Pharmacist.class, forVariable(variable), INITS);
    }

    public QPharmacist(Path<? extends Pharmacist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPharmacist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPharmacist(PathMetadata metadata, PathInits inits) {
        this(Pharmacist.class, metadata, inits);
    }

    public QPharmacist(Class<? extends Pharmacist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.mediready.domain.user.QUser(forProperty("user")) : null;
    }

}

