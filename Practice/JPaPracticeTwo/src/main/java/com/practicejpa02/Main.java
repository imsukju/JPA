package com.practicejpa02;

import com.practicejpa02.Ioc.service.BankService;
import com.practicejpa02.JPA.entity.Bank;
import com.practicejpa02.JPA.entity.User;
import com.practicejpa02.JPA.entity.UsersBank;
import org.h2.bnf.Bnf;

import javax.persistence.*;

public class Main {
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook2");


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try
		{
			tx.begin();
			User user = new User();
			user.setName("Jack");
			em.persist(user);
			em.flush();

			User user2 = new User();
			user2.setName("Jill");
			em.persist(user2);
			em.flush();


			Bank bank = new Bank();

			bank.setName("Bank1");
			bank.setAccount(123456);
			bank.setMoney(5000);
			em.persist(bank);

			Bank bank2 = new Bank();
			bank2.setName("Bank2");
			bank2.setAccount(654321);
			bank2.setMoney(5500);
			em.persist(bank2);

			UsersBank usersBank1 = new UsersBank();
			usersBank1.setBank(bank);
			usersBank1.setUser(user);
			em.persist(usersBank1);

			UsersBank usersBank2 = new UsersBank();
			usersBank2.setBank(bank2);
			usersBank2.setUser(user2);
			em.persist(usersBank2);

			em.flush();

			tx.commit();
			BankService.sendMoney(emf, bank2, 5000, bank, 123456);



		}

		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();

		}

		finally
		{
			em.close();
			emf.close();

		}
	}

}
