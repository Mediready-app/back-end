package com.example.mediready.domain.scheduleMedicine;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleMedicineId is a Querydsl query type for ScheduleMedicineId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QScheduleMedicineId extends BeanPath<ScheduleMedicineId> {

    private static final long serialVersionUID = -1926343528L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleMedicineId scheduleMedicineId = new QScheduleMedicineId("scheduleMedicineId");

    public final com.example.mediready.domain.medicine.QMedicine medicine;

    public final com.example.mediready.domain.scheduleDate.QScheduleDate scheduleDate;

    public QScheduleMedicineId(String variable) {
        this(ScheduleMedicineId.class, forVariable(variable), INITS);
    }

    public QScheduleMedicineId(Path<? extends ScheduleMedicineId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleMedicineId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleMedicineId(PathMetadata metadata, PathInits inits) {
        this(ScheduleMedicineId.class, metadata, inits);
    }

    public QScheduleMedicineId(Class<? extends ScheduleMedicineId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.medicine = inits.isInitialized("medicine") ? new com.example.mediready.domain.medicine.QMedicine(forProperty("medicine")) : null;
        this.scheduleDate = inits.isInitialized("scheduleDate") ? new com.example.mediready.domain.scheduleDate.QScheduleDate(forProperty("scheduleDate")) : null;
    }

}

