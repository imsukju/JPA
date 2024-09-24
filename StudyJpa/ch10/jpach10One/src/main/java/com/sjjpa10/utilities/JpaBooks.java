package com.sjjpa10.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

import com.sjjpa10.entity.*;


public class JpaBooks {

	private static final char HANGUL_START = '\uAC00';
	private static final char HANGUL_END = '\uD7A3';

static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	static final Long minAge = 15L;
	static final Long maxAge = 50L;
	static final Address[] addresses = {
			new Address("012 Eldo St", "Incheon", "12345"),
			new Address("345 Elm St", "Gwangju", "67890"),
			new Address("678 Elm St", "Daegu", "12345"),
			new Address("901 Elm St", "Busan", "67890"),
			new Address("234 Elm St", "Seoul", "12345"),
			new Address("567 Elm St", "Daegu", "67890"),
			new Address("890 Elm St", "Ulsan", "12345"),
			new Address("123 Elm St", "Suwon", "67890"),
			new Address("456 Elm St", "Daegu", "12345"),
			new Address("789 Elm St", "Andong", "67890")
	};

	public static List<Long> initMemberTeamSampleData(EntityManagerFactory emf, int teamNumbers, int memberNumbers)
	{
		EntityManager em = emf.createEntityManager();
		List<Long> teamids = new ArrayList<>();
		List<Long> memberIds = new ArrayList<>();
		EntityTransaction tx = em.getTransaction();
		try
		{
			tx.begin();

			for (int i=0; i<teamNumbers; i++)
			{
				Team team = new Team();
				team.setName("team :"+i);
				em.persist(team);
				teamids.add(team.getId());
			}
			Long minIdValue = Collections.min(teamids);// teamids 엘리먼트중에 최소값을 가진 애를 찾아주는 static 메소드
			Long MaxIdValue = Collections.max(teamids);// 가장 큰값을 가진 엘리먼트를 찾아줌
			
			
			for (int i=0; i<memberNumbers; i++)
			{
				Member member = new Member();
				member.setName("mem :"+i);

				Long randomAge = generateRandomNumber(minAge, maxAge);
				member.setAge(randomAge.intValue());
				Address address = JpaBooks.addresses[generateRandomNumber(0L, 9L).intValue()];
				member.setAddress(new Address(address.getStreet(), address.getCity(), address.getZipcod()));
				Long targetTeamId = generateRandomNumber(minIdValue, MaxIdValue);
				
				Team team = em.find(Team.class, targetTeamId);
				team.addMember(member);
				
				
				
				em.persist(member);
				memberIds.add(member.getId());

			}
			em.flush();
			em.clear();
			tx.commit();
		}
		catch (Exception e) 
		{
			tx.rollback();
			e.printStackTrace();
		}
		finally 
		{
			em.close();
		}
		
		return memberIds;
	}
	
	public static Long generateRandomNumber(Long min, Long max)
	{
		Random random = new Random();
		return random.nextLong(max -min +1) +min; //바운드값 min을 더하는 이유는 id값이 0이라는 값이 없으므로
 	}
	
	public static Long generateRandomId(List<Long> ids)
	{
		Long minIdValue = Collections.min(ids);// teamids 엘리먼트중에 최소값을 가진 애를 찾아주는 static 메소드
		Long MaxIdValue = Collections.max(ids);
		return generateRandomNumber(minIdValue, MaxIdValue);
	}

	public static List<Long> initPostCommentSampleDate(EntityManagerFactory emf, int postnum, Long postStringMaxsiz,int commentnum, Long CommetStringSize)
	{
		EntityManager em = emf.createEntityManager();
		List<Long> ids = new ArrayList<>();
		EntityTransaction tx = em.getTransaction();
		try
		{
			tx.begin();

			for(int i=0; i<postnum; i++)
			{
				Post post = new Post();
				post.setTitle("New Post : " + i);
				post.setText(generateRamdomString(postStringMaxsiz));

				int rcommentsize = generateRandomNumber(0L,(long) commentnum).intValue();

				for(int j =0; j<rcommentsize; j++)
				{
					Comment comment = new Comment();
					comment.setText(getRandomHangul(CommetStringSize));
					post.addComment(comment);

				}
				em.persist(post);
				ids.add(post.getId());

			}


			em.flush();
			em.clear();
			tx.commit();
		}
		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();
		}
		finally
		{
			em.close();
		}

		return ids;
	}

	public static String generateRamdomString(Long max)
	{
		Long length = generateRandomNumber(1L,max);

		StringBuffer ramdombuilder = new StringBuffer();

		Random ran = new Random();

		for(int i = 0; i<length; i++)

		{
			int index = ran.nextInt(characters.length());
			ramdombuilder.append(characters.charAt(index));

		}
		return ramdombuilder.toString();
	}

	public static String getRandomHangul(Long max) {
		Long length = generateRandomNumber(1L,max);

		StringBuffer ramdombuilder = new StringBuffer();

		Random random = new Random();

		for(int i = 0; i<length; i++)

		{
			int index = random.nextInt(characters.length());
			ramdombuilder.append(characters.charAt(index));

		}
		return ramdombuilder.toString();
	}

}
