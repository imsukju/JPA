package com.jpaOne.jpaexs;

import javax.persistence.*;



public class Main {

	public static void main(String... str)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		
		try
		{	tx.begin();
		
			Member mem = new Member();
			mem.setName("kim");
			em.persist(mem);
			
			em.flush();
			em.clear();
			
			Member foundmem = em.find(Member.class, mem.getId());
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
		}
		finally
		{
			em.close();
			emf.close();
		}
	}
}
