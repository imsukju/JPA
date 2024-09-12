package com.jpaorphan.jpaexs;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

public class Main 
{
	static String enters = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
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
//			em.persist(order);
//			em.persist(item1);
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
			customer.removeOrder(order); // cascade 옵션을 줬으므로 flush 나 commit 을 할경우 엔티티매니저 영속이 끊김
			//order는 orphan entity class  object
			// cascade = CascadeType.REMOVE 로 설정이 된 경우
			// 편의 removeOrder 메서드가 영향을 미치지 못함... 그래서 직접 remove를 해줌
			em.flush();
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
//			Thread.sleep(5000);
			order.removeLineItem(lineitem);
			testThree(em,maps,order,lineitem);
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
	
	public static void testThree(EntityManager em, Map<EntityClassStyle, Long> maps, Order orderp, LineItem lineitemp)
	{

//		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		System.out.println(enters);
		try
		{
			tx.begin();
			Customer customer = em.find(Customer.class, maps.get(EntityClassStyle.CUSTOMER));
			Order order = em.find(Order.class, maps.get(EntityClassStyle.ORDER));
			LineItem item1 = em.find(LineItem.class, maps.get(EntityClassStyle.LINEITEM));
		
			
			Order norder = null;
			LineItem nitem1 = null;
			
			if(em.contains(customer))
			{
				System.out.println("customer 영속상태");
				if(customer.getOrders() == null)
				{
					customer.addOrder(norder);
				}
			}
			else
			{
				System.out.println("customer 비영속 상태");
				em.merge(customer);
			}
			
			if(em.contains(order))
			{
				System.out.println("order 영속상태");
			}
			else
			{
				System.out.println("order 비영속 상태");
				norder = em.merge(orderp);	//옛날 영속상태 order 를ㄴ엏음
				em.flush();
				

				
			}
			
		
			if((em.contains(item1))||(em.contains(lineitemp)))
			{
				System.out.println("lineitem 영속상태");
			}
			else
			{
				System.out.println("lineitem 비영속 상태");
				nitem1= em.merge(lineitemp);
				norder.addLineItem(nitem1);
				
			}
//			customer.addOrder(norder);
//			norder.addLineItem(nitem1);

			em.flush();
			em.refresh(customer);
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
		emf.close();
	}

}
