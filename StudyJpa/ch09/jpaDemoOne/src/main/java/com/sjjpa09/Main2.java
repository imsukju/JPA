package com.sjjpa09;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.*;

import com.sjjpa09.entity.Address;
import com.sjjpa09.entity.Member;
import com.sjjpa09.utilities.JpaBooks;
import com.sjjpa09.utilities.JpaBooks2;

public class Main2 {

	static final int Team_NUMBERS = 10;
	static final int MEMBER_NUMBERS = 10;

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();
			List<Long> membersIds = JpaBooks2.initMemberTeamSampleData(emf, Team_NUMBERS, MEMBER_NUMBERS);

//			Thread.sleep(100);

			Long memberId = insertFavoriteFood(membersIds);

//			Thread.sleep(100);

			searchFavoriteFood(memberId);

//			Thread.sleep(100);

			updateFavoriteFood(memberId);

//			Thread.sleep(100);

			insertAddressAndAddressList(membersIds);

			Long rid = updateAddress(membersIds);
			realupdateAddress(rid);
//			updateAddressList(memberId);

			
			updateAddressListCustom(membersIds);
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
			Member member = em.find(Member.class, JpaBooks2.generateRandomId(memberIds));
			id = member.getId();
			member.getFavoriteFood().add("짬뽕");
			member.getFavoriteFood().add("떡볶이");
			member.getFavoriteFood().add("바닐라아이스라떼");
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

	public static void searchFavoriteFood(Long memberId) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Member member = em.find(Member.class, memberId);

			member.getFavoriteFood().add("짬뽕");
			member.getFavoriteFood().add("떡볶이");
			member.getFavoriteFood().add("바닐라아이스라떼");
			em.flush();
			em.clear();
			tx.commit();

			System.out.println(
					"================================================================searchFavoriteFood================================================================");
			Member mem = em.find(Member.class, memberId);
			for (String str : mem.getFavoriteFood()) {
				System.out.println("멤버:" + mem.getName() + " id: " + memberId + " Food : " + str);
			}
		}

		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		} finally {
			em.close();
		}

	}

	public static void updateFavoriteFood(Long memberId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();
			Member member = em.find(Member.class, memberId);

			member.getFavoriteFood().add("짬뽕");
			member.getFavoriteFood().add("떡볶이");
			member.getFavoriteFood().add("바닐라아이스라떼");

			System.out.println(
					"================================================================updateFavoriteFood================================================================");
			Member mem = em.find(Member.class, memberId);
			mem.getFavoriteFood().remove("바닐라아이스라떼");
			mem.getFavoriteFood().add("아이스 아메리카노");
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

	public static void insertAddressAndAddressList(List<Long> memberIds) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Member member = em.find(Member.class, JpaBooks2.generateRandomId(memberIds));

			member.setAddress(new Address("123 Main street", "Daegu", "12345"));
			member.getAddressList().add(new Address("456 Main street", "Daegu", "67890"));
			member.getAddressList().add(new Address("789 Main street", "Daegu", "54321"));

			em.flush();
			em.clear();

			Member foundMember = em.find(Member.class, member.getId());

			for (Address address : foundMember.getAddressList()) {
				System.out.printf("Strret : %s , city :$s, zopCode %s \n", address.getStreet(), address.getCity(),
						address.getZipcod());
			}
			LocalDateTime ldt = LocalDateTime.now();

			System.out.println("commit complete time: " + ldt.toString());

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

	public static Long updateAddress(List<Long> memberIds) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Member member = em.find(Member.class, JpaBooks.generateRandomId(memberIds));
			member.setAddress(new Address("123 rodeo str", "busan", "456789"));
			em.flush();
			em.clear();

			tx.commit();
			return member.getId();
		}

		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		} finally {
			em.close();
		}
		return null;
	}

	public static void realupdateAddress(Long memberId) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Member member = em.find(Member.class, memberId);
			em.flush();
			em.clear();
			
			Member foundMember = em.find(Member.class, member.getId());
			
			

			
			
			
			

			Address addr = foundMember.getAddress();

			addr.setStreet("ddd");
			addr.setZipcod("111111");
			addr.setCity("Daegu");

			foundMember.setAddress(new Address(addr.getStreet(), "Daegu", addr.getZipcod()));
//			em.flush();
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
	
	
	public static void updateAddressList(Long memberId)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();

			Member member = em.find(Member.class, memberId);
			em.flush();
			em.clear();
			
			Member foundMember = em.find(Member.class, member.getId());
			
			//////////////////////
			List<Address> addrList = foundMember.getAddressList();
			int targetRemovalElementInde = -1;
			
			for (int i = 0; i < addrList.size(); i++)
			{
				Address addr = addrList.get(i);
				if (addr.equals(new Address("456 Main street", "Daegu", "67890")))
				{
					targetRemovalElementInde = i;
					break;
				}
			}
			
			if (targetRemovalElementInde != -1)
			{
				addrList.remove(targetRemovalElementInde);
			}
			
			foundMember.getAddressList().add(new Address("347 jongro street", "Seoul", "23221"));
			
			
			//////////////////////
			
			em.flush();
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
	
	
	public static void updateAddressListCustom(List<Long> memberIds)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		

		
		try {
			tx.begin();
			int targetRemovalElementInde = -1;
			Long searchingid = 0L;
			for(Long a : memberIds)
			{
				Member member = em.find(Member.class, a);
				List<Address> addrList = member.getAddressList();
				
				if(addrList.size() > 0)
				{
					for (int i = 0; i < addrList.size(); i++)
					{
						Address addr = addrList.get(i);
						if (addr.equals(new Address("456 Main street", "Daegu", "67890")))
						{
							targetRemovalElementInde = i;
							searchingid = member.getId();
							break;
						}
					}
					
					if (targetRemovalElementInde != -1)
					{
						addrList.remove(targetRemovalElementInde);
					}
					
				}
			}
			
			


			
			//////////////////////
			Member foundmember = em.find(Member.class, searchingid);
			foundmember.getAddressList().add(new Address("347 jongro street", "Seoul", "23221"));
	
			//////////////////////
			
			em.flush();
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
