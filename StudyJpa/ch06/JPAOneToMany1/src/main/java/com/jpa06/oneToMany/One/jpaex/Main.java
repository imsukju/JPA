package com.jpa06.oneToMany.One.jpaex;
import javax.persistence.*;

public class Main {

	
	public static void main(String...strings )
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook8");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		Member member = new Member();
		member.setName("kim");
		em.persist(member);
		Long memid = member.getId();
		
		Product product = new Product();
		product.setName("hwang");
		em.persist(product);
		Long proid = product.getId();
		
		member.getProducts().add(product);
		em.flush();
		em.clear();
		
		Product foundProduc = em.find(Product.class, product.getId());
		tx.commit();
	}

}
