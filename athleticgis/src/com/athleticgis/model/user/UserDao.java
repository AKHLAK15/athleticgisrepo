package com.athleticgis.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.athleticgis.model.Dao;
import com.athleticgis.model.util.EntityManagerUtil;

public class UserDao implements Dao<User>, Serializable {
	private static final long serialVersionUID = -5745737809950101174L;
	EntityManager em = EntityManagerUtil.getEntityManagerFactory()
			.createEntityManager();
	
	// this is for development, remove in production
	public void initializeDB() {
		

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		User object0 = new User();
		User object1 = new User();

		UserRole admin = new UserRole();
		admin.setAuthority("ROLE_ADMIN");
		
		UserRole user = new UserRole();
		user.setAuthority("ROLE_USER");
		
		object0.setEnabled(1);
		object0.setUsername("admin");
		object0.setPassword("password");
		
		//admin.setId(object0.getId());
		//user.setId(object0.getId());
		admin.setUser(object0);
		user.setUser(object0);
		List<UserRole> roles0 = new ArrayList<UserRole>();
		roles0.add(admin);
		roles0.add(user);
		object0.setUserroles(roles0);
		
		object1.setEnabled(1);
		object1.setUsername("cs402@uwlax.edu");
		object1.setPassword("wildflowers");
		
		UserRole user1 = new UserRole();
		//user1.setId(object0.getId());
		user1.setUser(object1);
		List<UserRole> roles1 = new ArrayList<UserRole>();
		user1.setAuthority("ROLE_USER");
		roles1.add(user1);
		//roles0.add(user);
		object1.setUserroles(roles1);

		// IDs start as null
		// assertEquals((Long) null, object0.getUser_id());
		// assertEquals((Long) null, object1.getUser_id());

		em.persist(object0);
		em.persist(object1);
		em.persist(admin);
		em.persist(user);
		em.persist(user1);
		

		transaction.commit();
		
		
		
		//em.persist(admin);
		//em.persist(user);
		
		//user.setId(object1.getId());
		//em.persist(user);

		System.out.println("Object 0");
		System.out.println("Generated ID is: " + object0.getUser_id());
		
		System.out.println("Object 1");
		System.out.println("Generated ID is: " + object1.getUser_id());

		em.close();
	}

	@Override
	public void persist(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		User u = em.find(User.class, id);
		em.close();
		return u;
	}
}
