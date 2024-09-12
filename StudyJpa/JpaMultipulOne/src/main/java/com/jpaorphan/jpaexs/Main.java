package com.jpaorphan.jpaexs;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

public class Main 
{
	public static Map<EntityClassStyle, Long> setTestTables(EntityManagerFactory emf)
	{
		Map<EntityClassStyle, Long> maps = new HashMap<>();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx =   em.getTransaction();
		tx.begin();
		try
		{
			Customer customer = new Customer();
			customer.setName("kim");
			
			Order order = new Order();
			order.setDescription("o1");
			customer.addOrder(order);
			
			LineItem item1 = new LineItem();
			item1.setProductName("intem 1");
			item1.setQuantity(2);
			order.addLineItem(item1);
			
			em.persist(customer);
			tx.commit();
			
			
			boolean isPersistence = em.contains(order);
			System.out.println("order is Persist:  " + isPersistence);
			isPersistence = em.contains(item1);
			System.out.println("item1 is Persist:  " + isPersistence);
			
			boolean all = em.contains(order) && em.contains(item1);
			System.out.println("is Persist:  " + all);
			
			maps.put(EntityClassStyle.CUSTOMER, customer.getId());
			maps.put(EntityClassStyle.ORDER, order.getId());
			maps.put(EntityClassStyle.LINEITEM, item1.getId());
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
		}
		finally 
		{
			em.close();
		}
		return maps;
	}
	
	public static void occurenceOrphanEntity(EntityManagerFactory emf, Map<EntityClassStyle, Long> maps)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			Customer customer = em.find(Customer.class, maps.get(EntityClassStyle.CUSTOMER));
			Order order = em.find(Order.class, maps.get(EntityClassStyle.ORDER));
			LineItem lineitem = em.find(LineItem.class, maps.get(EntityClassStyle.LINEITEM));
			customer.removeOrder(order);
			
			if(em.contains(customer))
			{
				System.out.println("customer 영속상태");
			}
			else
			{
				System.out.println("customer 비영속 상태");
			}
			
			if(em.contains(order))
			{
				System.out.println("order 영속상태");
			}
			else
			{
				System.out.println("order 비영속 상태");
			}
			
			if(em.contains(lineitem))
			{
				System.out.println("lineitem 영속상태");
			}
			else
			{
				System.out.println("lineitem 비영속 상태");
			}
			
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
		}
	}

	public static void main(String[] args)
	{
		
		EntityManagerFactory emf = new Persistence().createEntityManagerFactory("jpabook3");

		Map<EntityClassStyle,Long>hi = setTestTables(emf);
		
		occurenceOrphanEntity(emf,hi);
	}

}
