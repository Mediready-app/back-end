package com.example.mediready.domain.dur;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDurId is a Querydsl query type for DurId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDurId extends BeanPath<DurId> {

    private static final long serialVersionUID = -472185890L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDurId durId = new QDurId("durId");

    public final com.example.mediready.domain.medicine.QMedicine id1;

    public final com.example.mediready.domain.medicine.QMedicine id2;

    public QDurId(String variable) {
        this(DurId.class, forVariable(variable), INITS);
    }

    public QDurId(Path<? extends DurId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDurId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDurId(PathMetadata metadata, PathInits inits) {
        this(DurId.class, metadata, inits);
    }

    public QDurId(Class<? extends DurId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id1 = inits.isInitialized("id1") ? new com.example.mediready.domain.medicine.QMedicine(forProperty("id1")) : null;
        this.id2 = inits.isInitialized("id2") ? new com.example.mediready.domain.medicine.QMedicine(forProperty("id2")) : null;
    }

}

