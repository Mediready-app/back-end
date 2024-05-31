package com.example.mediready.domain.Pill;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPill is a Querydsl query type for Pill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPill extends EntityPathBase<Pill> {

    private static final long serialVersionUID = -1693164547L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPill pill = new QPill("pill");

    public final StringPath backMark = createString("backMark");

    public final StringPath color = createString("color");

    public final StringPath form = createString("form");

    public final StringPath frontMark = createString("frontMark");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.example.mediready.domain.medicine.QMedicine medicine;

    public final StringPath shape = createString("shape");

    public QPill(String variable) {
        this(Pill.class, forVariable(variable), INITS);
    }

    public QPill(Path<? extends Pill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPill(PathMetadata metadata, PathInits inits) {
        this(Pill.class, metadata, inits);
    }

    public QPill(Class<? extends Pill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.medicine = inits.isInitialized("medicine") ? new com.example.mediready.domain.medicine.QMedicine(forProperty("medicine")) : null;
    }

}

