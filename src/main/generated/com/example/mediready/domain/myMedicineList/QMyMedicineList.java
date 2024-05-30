package com.example.mediready.domain.myMedicineList;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMyMedicineList is a Querydsl query type for MyMedicineList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyMedicineList extends EntityPathBase<MyMedicineList> {

    private static final long serialVersionUID = 1606379773L;

    public static final QMyMedicineList myMedicineList = new QMyMedicineList("myMedicineList");

    public final DatePath<java.time.LocalDate> expirationDate = createDate("expirationDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QMyMedicineList(String variable) {
        super(MyMedicineList.class, forVariable(variable));
    }

    public QMyMedicineList(Path<? extends MyMedicineList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMyMedicineList(PathMetadata metadata) {
        super(MyMedicineList.class, metadata);
    }

}

