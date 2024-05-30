package com.example.mediready.domain.scheduleDate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QScheduleDate is a Querydsl query type for ScheduleDate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleDate extends EntityPathBase<ScheduleDate> {

    private static final long serialVersionUID = 1759940765L;

    public static final QScheduleDate scheduleDate = new QScheduleDate("scheduleDate");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> repeatCycle = createNumber("repeatCycle", Integer.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QScheduleDate(String variable) {
        super(ScheduleDate.class, forVariable(variable));
    }

    public QScheduleDate(Path<? extends ScheduleDate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScheduleDate(PathMetadata metadata) {
        super(ScheduleDate.class, metadata);
    }

}

