package com.tnicacio.agendarest.model.em;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author tnica
 */
public class EM {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    public static EntityManager getEntityManager() {
        try {
          if (em == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory("AgendaRestPU");
            em = emf.createEntityManager();
          }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return em;
    }
    
    
    public static void closeEntityManager() {
        try {
            if (em != null && em.isOpen()){
                em.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void closeEntityManagerFactory() {
        try {
            if (emf != null && emf.isOpen()){
                emf.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void close() {
        closeEntityManager();
        closeEntityManagerFactory();
    }
    
    public static boolean isActiveTransaction(){
        return em != null && em.getTransaction() != null && em.getTransaction().isActive();
    }
    
}