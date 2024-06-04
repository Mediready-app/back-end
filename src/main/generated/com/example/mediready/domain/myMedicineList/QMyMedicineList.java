package com.example.mediready.domain.myMedicineList;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyMedicineList is a Querydsl query type for MyMedicineList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyMedicineList extends EntityPathBase<MyMedicineList> {

    private static final long serialVersionUID = 1606379773L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyMedicineList myMedicineList = new QMyMedicineList("myMedicineList");

    public final DatePath<java.time.LocalDate> expirationDate = createDate("expirationDate", java.time.LocalDate.class);

    public final com.example.mediready.domain.folder.QFolder folder;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.mediready.domain.medicine.QMedicine medicine;

    public final com.example.mediready.domain.user.QUser user;

    public QMyMedicineList(String variable) {
        this(MyMedicineList.class, forVariable(variable), INITS);
    }

    public QMyMedicineList(Path<? extends MyMedicineList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyMedicineList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyMedicineList(PathMetadata metadata, PathInits inits) {
        this(MyMedicineList.class, metadata, inits);
    }

    public QMyMedicineList(Class<? extends MyMedicineList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.folder = inits.isInitialized("folder") ? new com.example.mediready.domain.folder.QFolder(forProperty("folder"), inits.get("folder")) : null;
        this.medicine = inits.isInitialized("medicine") ? new com.example.mediready.domain.medicine.QMedicine(forProperty("medicine")) : null;
        this.user = inits.isInitialized("user") ? new com.example.mediready.domain.user.QUser(forProperty("user")) : null;
    }

}

