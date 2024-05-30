package com.example.mediready.domain.scheduleNotification;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QScheduleNotification is a Querydsl query type for ScheduleNotification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleNotification extends EntityPathBase<ScheduleNotification> {

    private static final long serialVersionUID = -337874499L;

    public static final QScheduleNotification scheduleNotification = new QScheduleNotification("scheduleNotification");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public final StringPath type = createString("type");

    public QScheduleNotification(String variable) {
        super(ScheduleNotification.class, forVariable(variable));
    }

    public QScheduleNotification(Path<? extends ScheduleNotification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScheduleNotification(PathMetadata metadata) {
        super(ScheduleNotification.class, metadata);
    }

}

