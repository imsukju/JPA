package com.practicejpa02.JPA.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BANK_ID")
    private Long id;

    @OneToMany(mappedBy = "bank")
    List<UsersBank> usersBanks;

    private String name;

    private int account;

    private int Money;

    private void modifiermoney(int a)
    {
        Money += a;
    }


}
