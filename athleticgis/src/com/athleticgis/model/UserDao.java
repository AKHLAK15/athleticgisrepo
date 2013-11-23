/**
 * 
 */
package com.athleticgis.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.athleticgis.model.util.EntityManagerUtil;

/**
 * @author matthew
 *
 */
public class UserDao  {
	private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
	
	public void persist(Users u) {
		EntityTransaction transaction = em.getTransaction();
	      transaction.begin();

	      Users object0 = new Users();
	      //Users object1 = new Users();
	      
	      object0.setEnabled(1);
	      object0.setUser_id(7L);
	      object0.setUsername("matt");
	      object0.setPassword("password");

	      // IDs start as null
	     // assertEquals((Long) null, object0.getUser_id());
	     // assertEquals((Long) null, object1.getUser_id());

	      em.persist(object0);
	      //em.persist(object1);

	      transaction.commit();

	      System.out.println("Object 0");
	      System.out.println("Generated ID is: " + object0.getUser_id());
		
		
		em.close();
	}
}
