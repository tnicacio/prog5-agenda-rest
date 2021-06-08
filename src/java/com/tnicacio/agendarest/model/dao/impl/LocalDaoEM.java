package com.tnicacio.agendarest.model.dao.impl;

import com.tnicacio.agendarest.model.dao.LocalDAO;
import com.tnicacio.agendarest.model.em.EM;
import com.tnicacio.agendarest.model.entities.Local;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author tnica
 */
public class LocalDaoEM implements LocalDAO{
    
    private EntityManager em;
    
    public LocalDaoEM(EntityManager em){
        this.em = em;
    }

    @Override
    public void insert(Local obj) {
        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            System.out.println("Inserindo Local");
            em.persist(obj);
            em.getTransaction().commit();
            System.out.println("Local inserido com sucesso!");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na inserção do Local. Realizado rollback...");
            }
        } finally {
            EM.close();
        }
    }

    @Override
    public void update(Local obj, Long id) {
        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            obj.setId(id);
            System.out.println("Atualizando Local " + id);
            em.merge(obj);
            em.getTransaction().commit();
            System.out.println("Local atualizado com sucesso!");

        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na atualização do Local. Realizado rollback...");
            }
        } finally {
            EM.close();
        }
   }

    @Override
    public void delete(Long id) {
        Local local = findById(id);
        if (local == null){
            System.out.println("Local não encontrado");
            return;
        }

        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            
            if (!em.isOpen()) {
                em = EM.getEntityManager();
                em.getTransaction().begin();
            }
            
            if (!em.contains(local)) {
                local = em.merge(local);
            }
            
            System.out.println("Excluindo Local de id " + id);
            em.remove(local);
            em.getTransaction().commit();
            System.out.println("Local excluído com sucesso!");

        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na exclusão do Local. Realizado rollback...");
            }
        } finally {
            EM.close();
        }   
    }

    @Override
    public Local findById(Long id) {
        Local local = null;
        try {
            em = EM.getEntityManager();
            local = em.find(Local.class, id);
            System.out.println("Busca pelo Local " + id + " finalizada");

        } catch(Exception e) {
            System.out.println("Erro na busca pelo Local " + id);
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return local;   
   }

    @Override
    public List<Local> findAll() {
        List<Local> locais = new ArrayList<>();
        try {
            em = EM.getEntityManager();         
            locais = em.createQuery(
                "SELECT l FROM Local l ", Local.class)
                .getResultList();
            
            System.out.println("Busca pelos Locais finalizada");
        } catch(Exception e) {
            System.out.println("Erro na busca pelos Locais");
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return locais;      
    }
    
}
