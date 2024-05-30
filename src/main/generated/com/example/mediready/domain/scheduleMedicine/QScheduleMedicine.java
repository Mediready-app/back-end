package com.example.mediready.domain.scheduleMedicine;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleMedicine is a Querydsl query type for ScheduleMedicine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleMedicine extends EntityPathBase<ScheduleMedicine> {

    private static final long serialVersionUID = 1142128285L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleMedicine scheduleMedicine = new QScheduleMedicine("scheduleMedicine");

    public final QScheduleMedicineId id;

    public QScheduleMedicine(String variable) {
        this(ScheduleMedicine.class, forVariable(variable), INITS);
    }

    public QScheduleMedicine(Path<? extends ScheduleMedicine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleMedicine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleMedicine(PathMetadata metadata, PathInits inits) {
        this(ScheduleMedicine.class, metadata, inits);
    }

    public QScheduleMedicine(Class<? extends ScheduleMedicine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QScheduleMedicineId(forProperty("id"), inits.get("id")) : null;
    }

}

