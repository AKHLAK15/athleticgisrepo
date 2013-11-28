package com.athleticgis.util.model;

//import java.io.Serializable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil //implements Serializable
{
	//private static final long serialVersionUID = 8475609181382197029L;

public static EntityManagerFactory getEntityManagerFactory()
   {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.athleticgis.jpa");
      return emf;
   }
}
