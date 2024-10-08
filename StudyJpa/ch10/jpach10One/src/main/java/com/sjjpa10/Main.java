package com.sjjpa10;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sjjpa10.entity.Member;
import com.sjjpa10.entity.Team;
import com.sjjpa10.entity.UserDTO;
import com.sjjpa10.qentity.QMember;
import com.sjjpa10.qentity.QTeam;
import com.sjjpa10.utilities.JpaBooks;
import com.querydsl.core.Tuple;

public class Main {

	static final int Team_NUMBERS = 10;
	static final int MEMBER_NUMBERS = 500;




	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();


//			List<Long> membersIds = JpaBooks.initMemberTeamSampleData(emf, Team_NUMBERS, MEMBER_NUMBERS);
//			queryMemberOfTypedQuery();
//			queryColumnOfTypedQuery();
//			queryParameterBounding(membersIds);
//			testJPACritieria(membersIds);
//			UseDTO();
//			getSingleRelatopnalShipEntity();
//			testInnerJoin();
//			testLeftOuterJoin();
//			testCrossJoin();
//			testAggregateFunction();
//			testGroupbyHavingOrderby();
//			setSubQueryOne();
//			setSubQueryTwo();
	//		testQuerDsLX(em);
//			testPaginAPIByJPQL();
//			testQueryPagingDsLX();
//			testPaginaAPIWithoutOffsetByQueryDsl();

		}

		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();


			// TODO: handle exception
		}
		finally
		{
			em.close();
			emf.close();
		}

	}

	public static void testQuerDsLX(EntityManager em)
	{
		QTeam team = QTeam.team;
		QMember member = QMember.member;

		//QueryDSL에서 정의한 클래스;
		// 파이썬에서 tuple을 지원함: List임. 그러나 불변객체()
		List<Tuple> result = new JPAQuery<>(em).select(team.name, member.age.avg()).from(team).join(team.memberList, member)
				.groupBy(team.name).having(member.age.avg().goe(30)).orderBy(member.age.avg().desc()).fetch();

		for(com.querydsl.core.Tuple tuple : result)
		{
			String teamName = tuple.get(team.name);
			Double avgAge = tuple.get(member.age.avg());

			System.out.println("Team: " + teamName + ", Age: " + avgAge);
		}

	}

	public static void testQueryPagingDsLX()
	{
		EntityManager em = emf.createEntityManager();
		QTeam team = QTeam.team;
		QMember member = QMember.member;
		EntityTransaction tx = em.getTransaction();

		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);



		try
		{
			tx.begin();

			//QueryDSL에서 정의한 클래스;
			// 파이썬에서 tuple을 지원함: List임. 그러나 불변객체()
			List<Member> memberList = jpaQueryFactory
					.selectFrom(member)
					.orderBy(member.id.asc())
					.offset(0)
					.limit(20)
					.fetch();
			for(Member m : memberList)
			{

				System.out.println("Member: " + m.toString());
			}
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



	public static Long getLastIdofMember()
	{
		EntityManager em = emf.createEntityManager();
		QTeam team = QTeam.team;
		QMember member = QMember.member;
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
		Long lastMemberid  = -1l;


		try
		{
			tx.begin();
			lastMemberid = jpaQueryFactory
					.select(member.id)
					.from(member)
					.orderBy(member.id.desc())
					.limit(1)
					.fetchOne();
		}

		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		} finally {
			em.close();
		}
		return lastMemberid;
	}

	public static Long getPagedMembers(Long lastMemberId, int limit) {
		EntityManager em = emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction();
		List<Member> members = null;

		try {
			tx.begin();

			QMember member = QMember.member;

			if (lastMemberId == null) {
				//페이지 처음
				members = queryFactory
						.selectFrom(member)
						.orderBy(member.id.asc())
						.limit(limit)
						.fetch();
			} else {
				// 그이후
				members = queryFactory
						.selectFrom(member)
						.where(member.id.gt(lastMemberId))
						.orderBy(member.id.asc())
						.limit(limit)
						.fetch();
			}

			for (Member m : members) {
				System.out.println(m);
			}

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		if (members != null) {
			if (!members.isEmpty()) {
				// members.size() - 1는 members 리스트의 마지막 엘리먼트 인덱스 값
				return members.get(members.size() - 1).getId();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static void testPaginaAPIWithoutOffsetByQueryDsl() {
		int pageSize = 20;

		// 총 페이지 수
		int totalPages = (MEMBER_NUMBERS + pageSize - 1) / pageSize; // 올림 계산

		Long lastMemberId = null;  // 첫 페이지의 페이징을 위한 코드...

		for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
			System.out.println("Page " + currentPage + ":");

			lastMemberId = getPagedMembers(lastMemberId, pageSize);
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

		public static void testPaginAPIByJPQL()
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			try
			{
				tx.begin();
				TypedQuery<Member> query = em.createQuery("select m from Member m ORDER BY m.name DESC ", Member.class).setFirstResult(10).setMaxResults(20);

				List<Member> memberlist = query.getResultList();
				for(Member m : memberlist)
				{
					System.out.println("member = " + m.getId());
				}

				tx.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				tx.rollback();
			}
			finally
			{
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
		
		@SuppressWarnings("unchecked")
		public static void testLeftOuterJoin()
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				

				List<Object []> teamList = em.createQuery("SELECT m, t FROM Member m left outer join m.team t").getResultList();
		
				
				for(Object [] l : teamList)
				{
					Member m = (Member) l[0];
					Team t = (Team) l[1];
					System.out.printf("Member name %s ,team name %s : ", m.getName(), t.getName());
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
		
		public static void testCrossJoin()
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				
				String query = "select m, t from Member m, Team t";
				
				@SuppressWarnings("unchecked")
				List<Object []> teamList = em.createQuery(query).getResultList();
		
				
				for(Object [] l : teamList)
				{
					Member m = (Member) l[0];
					Team t = (Team) l[1];

					System.out.printf("Member %s ,team %s : ", m, t);
				}
				
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
		
		public static void testAggregateFunction()
		{
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			try 
			{
				tx.begin();
				
				TypedQuery<Long> sumQuer = em.createQuery("SELECT sum(m.age) FROM Member m", Long.class);
				Long totalAge = sumQuer.getSingleResult();
				System.out.println("Sum :" + totalAge);
				
				Double averageAge = em.createQuery("select avg(m.age) from Member m", Double.class).getSingleResult();
				System.out.println("Avg pf Age " + averageAge);
				
				
				Integer MaxValue = em.createQuery("select max(m.age) from Member m", Integer.class).getSingleResult();
				System.out.println("Max of Age " + MaxValue);
				
				
				Integer MinValue = em.createQuery("select min(m.age) from Member m", Integer.class).getSingleResult();
				System.out.println("Min of Age " + MinValue);

				tx.commit();

			}

			catch (Exception e) {
				tx.rollback();
				e.printStackTrace();

				// TODO: handle exception
			}
			finally
			{
				em.close();
			}
		}
		
	//평균 나이가 25세 이상 팀들을 쿼리
	public static void testGroupbyHavingOrderby()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try 
		{
			tx.begin();
			
			String query = "SELECT t.name, AVG(m.age) " + "From Team t JOIN t.memberList m " // 팀에 속한 멤버들에서 부터 시작 
						+ "GROUP BY t.name " // 팀 이름별로 그룹핑
						+ "HAVING AVG(m.age) > 25 " // 평균 나이가 25세 이상인 
						+ "ORDER BY AVG(m.age) DESC";
			List<Object[] > objsList = em.createQuery(query).getResultList();

			for(Object [] l : objsList)
			{
				String m = (String) l[0];
				Double t = (Double) l[1];

				System.out.printf("Team %s, Average Age %f \n : ",m, t);
			}

			tx.commit();

		}

		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		}
		finally
		{
			em.close();
		}
	}
	
	// 각 Member의 name과 해당 Member가 속한 Team의 총 멤버 수를 구하는 쿼리
	public static void setSubQueryOne()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try 
		{
			tx.begin();
			String query = "SELECT m.name, "
						+ "(SELECT COUNT(subM) From Member subM WHERE subM.team = m.team) AS teamMemberCount "
						+ "From Member m " ;
			List<Object[] > objsList = em.createQuery(query).getResultList();

			for(Object [] l : objsList)
			{
				String m = (String) l[0];
				Long t = (Long) l[1];

				System.out.printf("Member %s, count %d \n : ",m, t);
			}

			tx.commit();

		}

		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		}
		finally
		{
			em.close();
		}
	}
	
	// 팀의 가장 나이가 많은 멤버를 찾는 쿼리
	public static void setSubQueryTwo()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try 
		{
			tx.begin();
			String query = "SELECT m From Member m "
						+ "Where m.age = (SELECT MAX(subM.age) From Member subM WHERE subM.team = m.team) ";
						
			List<Member> objsList = em.createQuery(query, Member.class).getResultList();

			for(Member l : objsList)
			{
				String m = l.getName();
				String t = l.getTeam().getName();
				Integer max = l.getAge();
				
				System.out.printf("Team : %s, Member %s, Max %d \n ", t,m,max);
			}

			tx.commit();

		}

		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		}
		finally
		{
			em.close();
		}
	}
	
	

	//JPQL은 FROM 절에서 서브 쿼리를 허용하지 않기때문에 네이티브 조인 sql로 풀어야한다
 	public static void testtt()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try 
		{
			tx.begin();
			
			String query = "SELECT m.name, subQuery.teamName " 
						+ "From Member m "
						+ "JOIN (SELECT t.TEAM_ID AS team_id, t.name AS teamName "
						+ "FROM Teams t "
						+ "JOIN Member m On t.TEAM_ID = m.TEAM_ID "
						+ "GROUP BY t.TEAM_ID, t.name "
						+ "HAVING AVG(m.age) > 30) AS subQuery "
						+ "ON m.TEAM_ID = subQuery.team_id";
			
			
			Query nativequery = em.createNativeQuery(query); 

			List<Object []> objsList = nativequery.getResultList();
			
			for(Object [] l : objsList)
			{
				String m = (String) l[0];
				String t = (String) l[1];

				System.out.printf("Team %s, Average Age %s \n : ",m, t);
			}

			tx.commit();

		}

		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		}
		finally
		{
			em.close();
		}
	}


	

}
