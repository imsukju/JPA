package com.sjjpa10;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.sjjpa10.entity.Member;
import com.sjjpa10.entity.Team;
import com.sjjpa10.entity.UserDTO;
import com.sjjpa10.utilities.JpaBooks;


public class Main {

	static final int Team_NUMBERS = 10;
	static final int MEMBER_NUMBERS = 100;

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();
			List<Long> membersIds = JpaBooks.initMemberTeamSampleData(emf, Team_NUMBERS, MEMBER_NUMBERS);
//			queryMemberOfTypedQuery();
//			queryColumnOfTypedQuery();
//			queryParameterBounding(membersIds);
//			testJPACritieria(membersIds);
//			UseDTO();
//			getSingleRelatopnalShipEntity();
//			testInnerJoin();
			testLeftOuterJoin();

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

	public static void queryMemberOfTypedQuery() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);

			List<Member> members = query.getResultList();
			
			for (Member m : members)
			{
				System.out.printf("member id: %d, name:%s \n", m.getId(),m.getName());
			}
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
	
	public static void queryColumnOfTypedQuery()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Query query = em.createQuery("select m.name, m.age from Member m");
			
			//엘리먼트가 오브젝트 배열 : 이 엘리먼트 오브젝트 배열의 엘리먼트 갯수는 2개(m.name m.age)
			List<Object[]> resultList = query.getResultList();

			for(Object[] result : resultList)
			{
				String name = (String) result[0];
				Integer age = (Integer)result[1];
				
				System.out.printf("member name: %s, age:%d \n",name,age);
			}		
			em.flush();
			em.clear();
			tx.commit();

		}

		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		} finally
		{
			em.close();
		}
	}
		
		
		public static void queryParameterBounding(List<Long> memberids)
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				Long memberId = JpaBooks.generateRandomId(memberids);
				Member foundMember = em.find(Member.class, memberId);
				em.clear();
				
				String userNameParm = foundMember.getName();
				System.out.println("name = "+ userNameParm);
				List<Member> members = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", userNameParm).getResultList();
				
				for(Member m : members)
				{
					System.out.println("found name = " + m.getName());
				}
				
				em.flush();
				em.clear();
				tx.commit();

			}

			catch (Exception e) 
			{
				tx.rollback();
				e.printStackTrace();

				// TODO: handle exception
			} finally {
				em.close();
			}
		}

	
		public static void testJPACritieria(List<Long> memberids)
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				Long memberId = JpaBooks.generateRandomId(memberids);
				Member foundMember = em.find(Member.class, memberId);
				
				String name = foundMember.getName();
				System.out.println("name  = " + name);
				CriteriaBuilder cb = em.getCriteriaBuilder();
				 CriteriaQuery<Member> cq = cb.createQuery(Member.class);
				 Root<Member> member = cq.from(Member.class);
				 cq.select(member).where(cb.equal(member.get("name"), name));
				 
				 List<Member> members = em.createQuery(cq).getResultList();
				 for(Member m: members)
				 {
					 
					 System.out.printf("member name: %s" , m.getName());
				 }
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
		
		public static void UseDTO()
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				TypedQuery<UserDTO> query = em.createQuery("SELECT new com.sjjpa10.entity.UserDTO(m.name, m.age) FROM Member m", UserDTO.class);
				List<UserDTO> resultList = query.getResultList();
				for(UserDTO d : resultList)
				{
					System.out.println("name = " + d.getName());
					System.out.println("age = " + d.getAge());
				}

				tx.commit();
				em.clear();
			}

			catch (Exception e) {
				tx.rollback();
				e.printStackTrace();

				// TODO: handle exception
			} finally {
				em.close();
			}
		}
		
		public static void getSingleRelatopnalShipEntity()
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				
				TypedQuery<Team> query = em.createQuery("SELECT m.team FROM Member m", Team.class);
				List<Team> teamList = query.getResultList();
				for(Team t: teamList)
				{
					System.out.println("Team name = " + t.getName());
				}

				tx.commit();
				em.clear();
			}

			catch (Exception e) {
				tx.rollback();
				e.printStackTrace();

				// TODO: handle exception
			} finally {
				em.close();
			}
		}
		
		
		public static void testInnerJoin()
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				

				List<Team> teamList = em.createQuery("SELECT distinct t  FROM Member m join m.team t", Team.class).getResultList();
				for(Team t: teamList)
				{
					System.out.println("Team name = " + t.getName());
				}

				
				tx.commit();
				em.clear();
			}

			catch (Exception e) {
				tx.rollback();
				e.printStackTrace();

				// TODO: handle exception
			} finally {
				em.close();
			}
		}
		
		public static void testLeftOuterJoin()
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				

				List<Member> teamList = em.createQuery("SELECT m FROM Member m left outer join m.team t", Member.class).getResultList();
				for(Member t: teamList)
				{
					System.out.println("member name = " + t.getName());
				}

				
				tx.commit();
				em.clear();
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
