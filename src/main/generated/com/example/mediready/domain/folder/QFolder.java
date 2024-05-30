package com.example.mediready.domain.folder;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFolder is a Querydsl query type for Folder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFolder extends EntityPathBase<Folder> {

    private static final long serialVersionUID = 1099816381L;

    public static final QFolder folder = new QFolder("folder");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> priority = createNumber("priority", Integer.class);

    public QFolder(String variable) {
        super(Folder.class, forVariable(variable));
    }

    public QFolder(Path<? extends Folder> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFolder(PathMetadata metadata) {
        super(Folder.class, metadata);
    }

}

