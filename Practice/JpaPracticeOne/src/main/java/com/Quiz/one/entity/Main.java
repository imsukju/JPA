package com.Quiz.one.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<Test> tests = new ArrayList<>();
		
		try
		{
			tests = makeColumnRow(emf);
			TestMonkey(emf,tests);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			em.close();
			emf.close();
		}

	}
	
	
	public static List<Test> makeColumnRow(EntityManagerFactory emf)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<Test> tests = new ArrayList<>();
		try
		{
			tx.begin();
			Member mem1 = new Member();
			mem1.setName("김어쩌고");
			em.persist(mem1);
			Member mem2 = new Member();
			mem2.setName("박저쩌고");
			em.persist(mem2);
			
			Orders accepted  = new Orders();
			accepted .setName("합격");
			em.persist(accepted);
			
			Orders rejected = new Orders();
			rejected.setName("불합격");
			em.persist(rejected);
			em.flush();
			
			Test test = new Test();
			test.setMember(mem1);
			test.setOreders(rejected);
			em.persist(test);

			em.flush();
			
			Test test2 = new Test();
			test2.setMember(mem2);
			test2.setOreders(accepted);
			em.persist(test2);

			
			em.flush();

			tests.add(test);
			tests.add(test2);

			
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

		return tests;
	}
	
	public static void TestMonkey(EntityManagerFactory emf, List<Test> tests)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try
		{
			for(Test t : tests)
			{
				if(t.getOreders().getName() == "불합격")
				{
					System.out.println("유저 " + t.getMember().getId() + "블합격");
				}
			}
		}
		catch(Exception e)
		{

			e.printStackTrace();
		}
		finally 
		{
			
		}
	}
	

}
