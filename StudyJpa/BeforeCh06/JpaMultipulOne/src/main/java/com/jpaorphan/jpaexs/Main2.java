package com.jpaorphan.jpaexs;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;
import javax.transaction.Transaction;

public class Main2 {

	public static void main(String... strings)
	{
		EntityManagerFactory emf = new Persistence().createEntityManagerFactory("jpabook3");
		
	}

	public static <K,V> Map<K, V> practice(EntityManagerFactory emf)
	{
		Map<K,V> maps = new HashMap<>();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		Customer cus = new Customer();
		Order or = new Order();
		LineItem lit = new LineItem();
		
		try
		{
			tx.begin();
			cus.addOrder(or);
			or.addLineItem(lit);
			
			em.persist(cus);
			tx.commit();
		}
		catch(Exception e)
		{
			tx.rollback();
			e.printStackTrace();
		}

		return maps;
	}
}
