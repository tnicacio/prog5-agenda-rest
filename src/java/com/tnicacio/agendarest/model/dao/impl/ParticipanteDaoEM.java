package com.tnicacio.agendarest.model.dao.impl;

import com.tnicacio.agendarest.model.dao.ParticipanteDAO;
import com.tnicacio.agendarest.model.em.EM;
import com.tnicacio.agendarest.model.entities.Participante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author tnica
 */
public class ParticipanteDaoEM implements ParticipanteDAO{
    
    private EntityManager em;
    
    public ParticipanteDaoEM(EntityManager em){
        this.em = em;
    }

    @Override
    public void insert(Participante obj) {
        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            System.out.println(obj.getContato().getNome() + " está sendo inserido como Participante");
            em.persist(obj);
            em.getTransaction().commit();
            System.out.println("Participante inserido com sucesso!");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na inserção do Participante. Realizado rollback...");
            }
        } finally {
            EM.close();
        }
    }

    @Override
    public void update(Participante obj, Long id) {
        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            obj.setId(id);
            System.out.println("Atualizando Participante " + id);
            em.merge(obj);
            em.getTransaction().commit();
            System.out.println("Participante atualizado com sucesso!");

        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na atualização do Participante. Realizado rollback...");
            }
        } finally {
            EM.close();
        }
   }

    @Override
    public void delete(Long id) {
        Participante participante = findById(id);
        if (participante == null){
            System.out.println("Participante não encontrado");
            return;
        }

        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            
            if (!em.isOpen()) {
                em = EM.getEntityManager();
                em.getTransaction().begin();
            }
            
            if (!em.contains(participante)) {
                participante = em.merge(participante);
            }
            
            System.out.println("Excluindo Participante de id " + id);
            em.remove(participante);
            em.getTransaction().commit();
            System.out.println("Participante excluído com sucesso!");

        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na exclusão do Participante. Realizado rollback...");
            }
        } finally {
            EM.close();
        }   
    }

    @Override
    public Participante findById(Long id) {
        Participante participante = null;
        try {
            em = EM.getEntityManager();
            participante = em.find(Participante.class, id);
            System.out.println("Busca pelo Participante " + id + " finalizada");

        } catch(Exception e) {
            System.out.println("Erro na busca pelo Participante " + id);
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return participante;   
   }

    @Override
    public List<Participante> findAll() {
        List<Participante> participantes = new ArrayList<>();
        try {
            em = EM.getEntityManager();         
            participantes = em.createQuery(
                "SELECT p FROM Participante p "
                + "ORDER BY p.contato.nome", Participante.class)
                .getResultList();
            
            System.out.println("Busca pelos Participantes finalizada");
        } catch(Exception e) {
            System.out.println("Erro na busca pelos Participantes");
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return participantes;      
    }
    
}

