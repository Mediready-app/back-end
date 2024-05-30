package com.example.mediready.domain.dur;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDur is a Querydsl query type for Dur
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDur extends EntityPathBase<Dur> {

    private static final long serialVersionUID = 1250903907L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDur dur = new QDur("dur");

    public final QDurId durIdPk;

    public QDur(String variable) {
        this(Dur.class, forVariable(variable), INITS);
    }

    public QDur(Path<? extends Dur> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDur(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDur(PathMetadata metadata, PathInits inits) {
        this(Dur.class, metadata, inits);
    }

    public QDur(Class<? extends Dur> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.durIdPk = inits.isInitialized("durIdPk") ? new QDurId(forProperty("durIdPk"), inits.get("durIdPk")) : null;
    }

}

