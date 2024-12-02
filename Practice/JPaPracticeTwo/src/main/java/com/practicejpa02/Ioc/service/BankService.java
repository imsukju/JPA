package com.practicejpa02.Ioc.service;

import com.practicejpa02.JPA.entity.Bank;
import com.practicejpa02.JPA.entity.QBank;
import com.practicejpa02.JPA.entity.QUsersBank;
import com.practicejpa02.JPA.entity.UsersBank;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.*;
import java.util.List;


public class BankService {

    public static void sendMoney(EntityManagerFactory emf, Bank sendbank, int money, Bank bank, int account)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {

            tx.begin();

            QBank qbank = QBank.bank;
            QUsersBank usersBank = QUsersBank.usersBank;
            sendbank = em.merge(sendbank);
            sendbank.setMoney(sendbank.getMoney() - money);
            List<UsersBank> usersBanks = new JPAQuery<>(em).select(usersBank).from(usersBank).fetch();
            UsersBank usersBankz = new JPAQuery<>(em).select(usersBank).from(usersBank)
                    .where(usersBank.bank.id.eq(bank.getId()).and(usersBank.bank.account.eq(account)))
                    .fetchOne();

            if (usersBankz != null)
            {
                receiveDeposit(em, money, usersBankz.getBank(), usersBankz.getBank().getAccount());
            }
            else
            {
                System.out.println("계좌를 찾을 수 없습니다.");
            }
;
        tx.commit();


        }
        catch(Exception e)
        {
            tx.rollback();

            e.printStackTrace();
        }
        finally
        {
            em.close();
        }
    }

    public static void receiveDeposit(EntityManager em, int money, Bank bank, int account)
    {
        try
        {
            bank.setMoney(bank.getMoney() + money);
        }
        catch(Exception e)
        {

            e.printStackTrace();
        }
        finally
        {
            em.close();
        }
    }
}
