package com.athleticgis.util.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static EntityManagerFactory emf;
	static {
		emf = Persistence.createEntityManagerFactory("com.athleticgis.jpa");
	}
	
	public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
