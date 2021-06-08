package com.tnicacio.agendarest.model.dao.impl;

import com.tnicacio.agendarest.model.dao.CompromissoDAO;
import com.tnicacio.agendarest.model.em.EM;
import com.tnicacio.agendarest.model.entities.Compromisso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author tnica
 */
public class CompromissoDaoEM implements CompromissoDAO {

    private EntityManager em;

    public CompromissoDaoEM(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Compromisso> findByLocalId(Long localId) {
        List<Compromisso> compromissos = new ArrayList<>();
        if (localId == null || localId <= 0) {
            return compromissos;
        }

        try {
            em = EM.getEntityManager();

            compromissos = em.createQuery(
                    "SELECT c FROM Compromisso c "
                    + "JOIN c.local loc "
                    + "WHERE loc.id = :localId", Compromisso.class)
                    .setParameter("localId", localId)
                    .getResultList();

            System.out.println("Busca pelos Compromissos com Local " + localId + " finalizada");
        } catch (Exception e) {
            System.out.println("Erro na busca pelos Compromissos com Local " + localId);
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return compromissos;
    }

    @Override
    public List<Compromisso> findByContatoId(Long contatoId) {

        List<Compromisso> compromissos = new ArrayList<>();
        if (contatoId == null || contatoId <= 0) {
            return compromissos;
        }

        try {
            em = EM.getEntityManager();

            compromissos = em.createQuery(
                    "SELECT c FROM Compromisso c "
                    + "JOIN c.participantes part "
                    + "WHERE part.contato.id = :contatoId", Compromisso.class)
                    .setParameter("contatoId", contatoId)
                    .getResultList();

            System.out.println("Busca pelos Compromissos do Contato " + contatoId + " finalizada");
        } catch (Exception e) {
            System.out.println("Erro na busca pelos Compromissos do Contato " + contatoId);
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return compromissos;
    }

    @Override
    public void insert(Compromisso obj) {
        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            System.out.println("Inserindo novo compromisso");
            
            em.persist(obj);
            em.getTransaction().commit();
            System.out.println("Compromisso inserido com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na inserção do Compromisso. Realizado rollback...");
            }
        } finally {
            EM.close();
        }
    }

    @Override
    public void update(Compromisso obj, Long id) {
        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            obj.setId(id);
            System.out.println("Atualizando Compromisso " + obj.getId());
            em.merge(obj);
            em.getTransaction().commit();
            System.out.println("Compromisso " + obj.getId() + " atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na atualização do Compromisso " 
                        + obj.getId() + ". Realizado rollback...");
            }
        } finally {
            EM.close();
        }
    }

    @Override
    public void delete(Long id) {
        Compromisso compromisso = findById(id);
        if (compromisso == null) {
            System.out.println("Compromisso não encontrado");
            return;
        }

        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();

            if (!em.isOpen()) {
                em = EM.getEntityManager();
                em.getTransaction().begin();
            }

            if (!em.contains(compromisso)) {
                compromisso = em.merge(compromisso);
            }

            System.out.println("Excluindo Compromisso de id " + id);
            em.remove(compromisso);
            em.getTransaction().commit();
            System.out.println("Compromisso excluído com sucesso!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na exclusão do Compromisso. Realizado rollback...");
            }
        } finally {
            EM.close();
        }
    }

    @Override
    public Compromisso findById(Long id) {
        Compromisso compromisso = null;
        try {
            em = EM.getEntityManager();
            compromisso = em.find(Compromisso.class, id);
            System.out.println("Busca pelo Compromisso " + id + " finalizada");

        } catch (Exception e) {
            System.out.println("Erro na busca pelo Compromisso " + id);
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return compromisso;
    }

    @Override
    public List<Compromisso> findAll() {
        List<Compromisso> compromissos = new ArrayList<>();
        try {
            em = EM.getEntityManager();
            compromissos = em.createQuery(
                    "SELECT c FROM Compromisso c "
                    + "ORDER BY c.data desc, c.hora desc", Compromisso.class)
                    .getResultList();

            System.out.println("Busca pelos Compromissos finalizada");
        } catch (Exception e) {
            System.out.println("Erro na busca pelos Compromissos");
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return compromissos;
    }

}
