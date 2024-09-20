package com.sjjpa10.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavorateFood is a Querydsl query type for FavorateFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorateFood extends EntityPathBase<FavorateFood> {

    private static final long serialVersionUID = 421112901L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavorateFood favorateFood = new QFavorateFood("favorateFood");

    public final StringPath Foodname = createString("Foodname");

    public final NumberPath<Long> it = createNumber("it", Long.class);

    public final QMember member;

    public QFavorateFood(String variable) {
        this(FavorateFood.class, forVariable(variable), INITS);
    }

    public QFavorateFood(Path<? extends FavorateFood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavorateFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavorateFood(PathMetadata metadata, PathInits inits) {
        this(FavorateFood.class, metadata, inits);
    }

    public QFavorateFood(Class<? extends FavorateFood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

