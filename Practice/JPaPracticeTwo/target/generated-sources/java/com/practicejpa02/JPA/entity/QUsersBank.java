package com.practicejpa02.JPA.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsersBank is a Querydsl query type for UsersBank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersBank extends EntityPathBase<UsersBank> {

    private static final long serialVersionUID = -663875323L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsersBank usersBank = new QUsersBank("usersBank");

    public final QBank bank;

    public final QUser user;

    public QUsersBank(String variable) {
        this(UsersBank.class, forVariable(variable), INITS);
    }

    public QUsersBank(Path<? extends UsersBank> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsersBank(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsersBank(PathMetadata metadata, PathInits inits) {
        this(UsersBank.class, metadata, inits);
    }

    public QUsersBank(Class<? extends UsersBank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bank = inits.isInitialized("bank") ? new QBank(forProperty("bank")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

