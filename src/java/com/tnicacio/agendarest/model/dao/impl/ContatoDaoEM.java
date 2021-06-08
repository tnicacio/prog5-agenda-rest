package com.tnicacio.agendarest.model.dao.impl;

import com.tnicacio.agendarest.model.dao.ContatoDAO;
import com.tnicacio.agendarest.model.em.EM;
import com.tnicacio.agendarest.model.entities.Contato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author tnica
 */
public class ContatoDaoEM implements ContatoDAO {
    
    private EntityManager em;
    
    public ContatoDaoEM(EntityManager em){
        this.em = em;
    }

    @Override
    public List<Contato> findByNome(String nome) {
        List<Contato> contatos = new ArrayList<>();

        try {
            String nomeToUpperCase = "%%";
            if (nome != null && !"".equals(nome.trim())) {
                nomeToUpperCase = "%" + nome.toUpperCase() + "%";
            }
            
            em = EM.getEntityManager();
            contatos = em.createQuery(
                "SELECT c FROM Contato c "
                + "WHERE upper(c.nome) LIKE upper(:nome) "
                + "ORDER BY c.nome", Contato.class)
                .setParameter("nome", nomeToUpperCase)
                .getResultList();
            
            System.out.println("Busca pelo Contato com descrição '" + nome + "' finalizada");
        } catch(Exception e) {
            System.out.println("Erro na busca pelos Contatos com descrição '" + nome + "'");
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return contatos;
    }

    @Override
    public void insert(Contato obj) {
        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            System.out.println("Inserindo Contato " + obj.getNome());
            em.persist(obj);
            em.getTransaction().commit();
            System.out.println("Contato inserido com sucesso!");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na inserção do Contato. Realizado rollback...");
            }
        } finally {
            EM.close();
        }
    }

    @Override
    public void update(Contato obj, Long id) {
        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            obj.setId(id);
            System.out.println("Atualizando Contato " + id);
            em.merge(obj);
            em.getTransaction().commit();
            System.out.println("Contato atualizado com sucesso!");

        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na atualização do Contato. Realizado rollback...");
            }
        } finally {
            EM.close();
        }
    }

    @Override
    public void delete(Long id) {
        Contato contato = findById(id);
        if (contato == null){
            System.out.println("Contato não encontrado");
            return;
        }

        try {
            em = EM.getEntityManager();
            em.getTransaction().begin();
            
            if (!em.isOpen()) {
                em = EM.getEntityManager();
                em.getTransaction().begin();
            }
            
            if (!em.contains(contato)) {
                contato = em.merge(contato);
            }
            
            System.out.println("Excluindo Contato " + id);
            em.remove(contato);
            em.getTransaction().commit();
            System.out.println("Contato excluído com sucesso!");

        } catch(Exception e) {
            System.out.println(e.getMessage());
            if (EM.isActiveTransaction()) {
                em.getTransaction().rollback();
                System.out.println("Erro na exclusão do Contato. Realizado rollback...");
            }
        } finally {
            EM.close();
        }   
    }

    @Override
    public Contato findById(Long id) {
        Contato contato = null;
        try {
            em = EM.getEntityManager();
            contato = em.find(Contato.class, id);
            System.out.println("Busca pelo Contato " + id + " finalizada");

        } catch(Exception e) {
            System.out.println("Erro na busca pelo Contato " + id);
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return contato;   
    }

    @Override
    public List<Contato> findAll() {
        List<Contato> contatos = new ArrayList<>();
        try {
            em = EM.getEntityManager();         
            contatos = em.createQuery(
                "SELECT c FROM Contato c "
                + "ORDER BY c.nome", Contato.class)
                .getResultList();
            
            System.out.println("Busca pelos Contatos finalizada");
        } catch(Exception e) {
            System.out.println("Erro na busca pelos Contatos");
            System.out.println(e.getMessage());
        } finally {
            EM.close();
        }
        return contatos;      
    }
    
}
