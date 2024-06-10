package com.example.mediready.domain.scheduleDate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleDate is a Querydsl query type for ScheduleDate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleDate extends EntityPathBase<ScheduleDate> {

    private static final long serialVersionUID = 1759940765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleDate scheduleDate = new QScheduleDate("scheduleDate");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final TimePath<java.time.LocalTime> notificationTime = createTime("notificationTime", java.time.LocalTime.class);

    public final StringPath notificationType = createString("notificationType");

    public final NumberPath<Integer> repeatCycle = createNumber("repeatCycle", Integer.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final com.example.mediready.domain.user.QUser user;

    public QScheduleDate(String variable) {
        this(ScheduleDate.class, forVariable(variable), INITS);
    }

    public QScheduleDate(Path<? extends ScheduleDate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleDate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleDate(PathMetadata metadata, PathInits inits) {
        this(ScheduleDate.class, metadata, inits);
    }

    public QScheduleDate(Class<? extends ScheduleDate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.mediready.domain.user.QUser(forProperty("user")) : null;
    }

}

