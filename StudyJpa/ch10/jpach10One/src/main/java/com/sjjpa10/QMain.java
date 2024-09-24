package com.sjjpa10;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sjjpa10.entity.Member;
import com.sjjpa10.entity.Team;
import com.sjjpa10.qentity.QMember;
import com.sjjpa10.qentity.QTeam;
import com.sjjpa10.utilities.JpaBooks;

import javax.persistence.*;
import java.util.List;


public class QMain {
    static final int Team_NUMBERS = 10;
    static final int MEMBER_NUMBERS = 500;

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");


    public static void main(String[] args)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {

            tx.begin();
            List<Long> membersIds = JpaBooks.initMemberTeamSampleData(emf, Team_NUMBERS, MEMBER_NUMBERS);

//            testPaginaAPIWithoutOffsetByQueryDsl();
//            testFectchJoinByJPQL();
//            testFectchJoinByqueryDsl();
            testCoolectionfetichJoin();
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

    public static void testQueryDSLInsert(EntityManager em) {
        System.out.println("+testQueryDSLInsert");

        Member member = new Member();
        member.setName("1stQueryDSLInsert");
        member.setAge(30);

        // QueryDSL은 Insert를 지원하지 않는다.
        // persist 메소드를 사용하면 된다.
        em.persist(member);

        System.out.println("-testQueryDSLInsert");

    }

    public static void testQueryDSLUpdate(EntityManager em, JPAQueryFactory queryFactory) {
        Member member = new Member();
        member.setName("1stQueryDSLUpdate");
        member.setAge(30);

        em.persist(member);
        em.flush();
        em.clear();

        String newName = "New Name";
        QMember qMember = QMember.member;
        long affectedRows = queryFactory
                .update(qMember)
                .set(qMember.name, newName)
                .where(qMember.id.eq(member.getId()))
                .execute();

        System.out.println("affected Rows : " + affectedRows);
    }

    public static void testQueryDSLDelete(EntityManager em, JPAQueryFactory queryFactory) {
        Member member = new Member();
        member.setName("sungwon");
        member.setAge(30);

        em.persist(member);
        em.flush();
        em.clear();

        QMember qMember = QMember.member;
        long affectedRows = queryFactory
                .delete(qMember)
                .where(qMember.id.eq(member.getId()))
                .execute();

        System.out.println("affected Rows : " + affectedRows);
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

        if (members != null)
        {
            if (!members.isEmpty())
            {
                // members.size() - 1는 members 리스트의 마지막 엘리먼트 인덱스 값
                return members.get(members.size() - 1).getId();
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public static void testPaginaAPIWithoutOffsetByQueryDsl() {
        Long queryedLastMemberid = getLastIdofMember();

        int pageSize = 20;

        // 총 페이지 수
        int totalPages = (MEMBER_NUMBERS + pageSize - 1) / pageSize; // 올림 계산

        Long lastMemberId = null;  // 첫 페이지의 페이징을 위한 코드...

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Page " + currentPage + ":");

            lastMemberId = getPagedMembers(lastMemberId, pageSize);
            
            
        }
        
        if (lastMemberId.equals(queryedLastMemberid))
        {
            System.out.println("같음 lastMemberId :" +  lastMemberId  + "queryedLastMemberid:" + queryedLastMemberid )  ;
        }
        else
        {
            System.out.println("다름 lastMemberId :" +  lastMemberId + "queryedLastMemberid:" + queryedLastMemberid )  ;
        }
    }

    public static void testFectchJoinByJPQL()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            List<Member> memberList = em.createQuery("SELECT m from  Member m join fetch m.team", Member.class).getResultList();

            for (Member m : memberList )
            {
                Team team = m.getTeam();
                System.out.println("Member name : " +  m.getName() + ", team id : " + team.getId() + ", name : " + team.getName());
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

    public static void testFectchJoinByqueryDsl()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        try {
            tx.begin();

            QMember member = QMember.member;
            QTeam team = QTeam.team;

            List<Member> members = queryFactory.selectFrom(member)
                            .join(member.team, team).fetchJoin()
                            .fetch();

            for (Member m : members )
            {
                Team t = m.getTeam();
                System.out.println("Member name : " +  m.getName() + ", team id : " + t.getId() + ", name : " + t.getName());
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


    public static void testCoolectionfetichJoin()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        try {
            tx.begin();

            QMember member = QMember.member;
            QTeam team = QTeam.team;

            String paramneme = "team :8";


            //One : Team
            //Many : Member
            //OntTOMany join : 중복된결과값이 발생함
//            List<Team> teams = em.createQuery("SELECT t from  Team t join fetch t.memberList where t.name = :name",
//                    Team.class).setParameter("name", paramneme).getResultList();
//
//            for (Team t : teams )
//            {
//
//                System.out.printf("\n Team id : %d, Team name %s: ", t.getId(), t.getName());
//                for (Member m : t.getMemberList() )
//                {
//                    System.out.println("Member id : " + m.getId() + " Team name : " + m.getTeam().getName());
//                }
//            }


            List<Team> teams =
                    em.createQuery("select distinct t from Team t join fetch t.memberList where t.name = :name",
                                    Team.class)
                            .setParameter("name", paramneme)
                            .getResultList();

            for (Team t : teams) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.printf("Team id:%d \n", t.getId());
                for (Member m : t.getMemberList()) {
                    System.out.printf("Member id:%d, Team name:%s \n", m.getId(), m.getTeam());
                }
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

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
}
