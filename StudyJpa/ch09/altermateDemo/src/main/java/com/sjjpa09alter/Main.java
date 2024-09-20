package com.sjjpa09alter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.*;

import com.sjjpa09alter.entity.Address;
import com.sjjpa09alter.entity.AddressEntity;
import com.sjjpa09alter.entity.FavorateFood;
import com.sjjpa09alter.entity.Member;
import com.sjjpa09alter.utilities.JpaBooks;


public class Main {

	static final int Team_NUMBERS = 10;
	static final int MEMBER_NUMBERS = 10;

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();
			List<Long> membersIds = JpaBooks.initMemberTeamSampleData(emf, Team_NUMBERS, MEMBER_NUMBERS);
			Long memberid = insertFavoriteFood(membersIds);
			updateAddress(memberid);
			updatefavoritefood(memberid);
			

		}

		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();


			// TODO: handle exception
		} finally {
			em.close();
			emf.close();
		}

	}

	public static Long insertFavoriteFood(List<Long> memberIds) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Long id = -1L;
		try {
			tx.begin();
			Member member = em.find(Member.class, JpaBooks.generateRandomId(memberIds));
			id = member.getId();
			
			FavorateFood pizza = FavorateFood.builder().Foodname("피자").member(member).build();
			
			FavorateFood donkatsu = FavorateFood.builder().Foodname("돈까스").member(member).build();
			
			FavorateFood jajangmyun = FavorateFood.builder().Foodname("짜장면").member(member).build();
			
			
			member.getFavoriteFoods().add(pizza);
			member.getFavoriteFoods().add(donkatsu);
			member.getFavoriteFoods().add(jajangmyun);
			
			AddressEntity address1 = AddressEntity.builder().address(new Address("456 Elm st", "Gwangjy", "12345")).member(member).build();
			AddressEntity address2 = AddressEntity.builder().address(new Address("789 Eldo st", "Daejeon", "12345")).member(member).build();
			member.getAddressList().add(address1);
			member.getAddressList().add(address2);
		
			em.persist(member);
			
			em.flush();
			em.clear();
			tx.commit();

		}

		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		} finally {
			em.close();
		}
		return id;

	}
	
	public static void updateAddress(Long memberid)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Long id = -1L;
		try {
			tx.begin();
			Member member = em.find(Member.class, memberid);
			
			List<AddressEntity> addrList = member.getAddressList();
			if(!addrList.isEmpty())
			{
				AddressEntity addressToRemove = addrList.get(0);
				addrList.remove(addressToRemove);
			}
			
			AddressEntity newAddress = AddressEntity.builder().address(new Address("st Free","Newyork","23231")).member(member).build();
			addrList.add(newAddress);

		
			em.flush();
			em.clear();
			tx.commit();

		}

		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			em.close();
		}

	}
	
	public static void updatefavoritefood(Long memberid)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Long id = -1L;
		try {
			tx.begin();
			Member member = em.find(Member.class, memberid);
			
			List<FavorateFood> foodlist = member.getFavoriteFoods();
			if(!foodlist.isEmpty())
			{
				for(FavorateFood food : foodlist)
				{
					if(food.getFoodname() == "짜장면")
					{
						foodlist.remove(food);
						break;g
					}
				}
			}
		
			FavorateFood tang = FavorateFood.builder().Foodname("탕수육").member(member).build();
			foodlist.add(tang);
			em.flush();
			em.clear();
			tx.commit();

		}

		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			em.close();
		}
	}
	
	

}
