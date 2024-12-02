package com.practicejpa02.JPA.qentity;

import com.practicejpa02.JPA.entity.Bank;
import com.practicejpa02.JPA.entity.UsersBank;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QBank is a Querydsl query type for Bank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBank extends EntityPathBase<Bank> {

    private static final long serialVersionUID = -323446533L;

    public static final QBank bank = new QBank("bank");

    public final NumberPath<Integer> account = createNumber("account", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> Money = createNumber("Money", Integer.class);

    public final StringPath name = createString("name");

    public final ListPath<UsersBank, QUsersBank> usersBanks = this.<UsersBank, QUsersBank>createList("usersBanks", UsersBank.class, QUsersBank.class, PathInits.DIRECT2);

    public QBank(String variable) {
        super(Bank.class, forVariable(variable));
    }

    public QBank(Path<? extends Bank> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBank(PathMetadata metadata) {
        super(Bank.class, metadata);
    }

}

