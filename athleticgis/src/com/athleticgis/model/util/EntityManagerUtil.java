package com.athleticgis.model.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil
{
   public static EntityManagerFactory getEntityManagerFactory()
   {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.athleticgis.jpa");
      return emf;
   }
}
