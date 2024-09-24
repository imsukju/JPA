package com.sjjpa10;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sjjpa10.entity.Comment;
import com.sjjpa10.entity.Member;
import com.sjjpa10.entity.Post;
import com.sjjpa10.qentity.QMember;
import com.sjjpa10.qentity.QPost;
import com.sjjpa10.qentity.QTeam;
import com.sjjpa10.utilities.JpaBooks;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

public class PostMain
{
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");
    static final int POST_NUMBERS = 50;
    static final int COMMENT_NUMBERS = 15;

    static final long POST_STRING_MAX_SIZE = 1500L;

    static final long COMMENT_STRING_MAX_SIZE = 300L;

    public static void main(String[] args)
    {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {

            tx.begin();
            List<Long> postids = JpaBooks.initPostCommentSampleDate(emf,POST_NUMBERS, POST_STRING_MAX_SIZE, COMMENT_NUMBERS, COMMENT_STRING_MAX_SIZE);

//            postQueryPagingDsLX();
//            postQueryPagingWithoutJPaQueryOffset();
            viewCommentOfSomePost();

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


    public static void postQueryPagingDsLX()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        QPost post = QPost.post;

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);



        try
        {
            tx.begin();

            //QueryDSL에서 정의한 클래스;
            // 파이썬에서 tuple을 지원함: List임. 그러나 불변객체()
            List<Post> postList = jpaQueryFactory
                    .selectFrom(post)
                    .orderBy(post.id.asc())
                    .offset(0)
                    .limit(20)
                    .fetch();
            for(Post m : postList)
            {

                System.out.println("Post name: " + m.getTitle());
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

    //offset 은 성능이슈로 인하여 지우고 코딩하였습니다
    public static void postQueryPagingWithoutJPaQueryOffset()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        QPost post = QPost.post;

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);



        try
        {
            tx.begin();

            TypedQuery<Post> query = em.createQuery("select p from Post p  ORDER BY p.id DESC ", Post.class).setFirstResult(10).setMaxResults(20);

            List<Post> postList = query.getResultList();
            for(Post p : postList)
            {
                System.out.printf("\nPost name: %S" , p.getTitle());
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

    // 특정 포스트의 댓글을 불러오는 메소드
    public static void viewCommentOfSomePost()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        QPost post = QPost.post;

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);



        try
        {
            tx.begin();

            Random r = new Random();
            int postnum = r.nextInt(POST_NUMBERS+1)+1;



            List<Post> postList = jpaQueryFactory.selectFrom(post).fetch();

            Post selectedPost = postList.get(postnum);
            List<Comment> comments = selectedPost.getComments();

            for(Comment c : comments)
            {

                System.out.println("Post name : " + selectedPost.getTitle() + " Comment: " + c.getText());

            }
            System.out.println("Comment Size : " + comments.size());


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
