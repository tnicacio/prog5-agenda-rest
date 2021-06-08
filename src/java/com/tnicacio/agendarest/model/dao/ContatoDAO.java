package com.tnicacio.agendarest.model.dao;

import com.tnicacio.agendarest.model.entities.Contato;
import java.util.List;

/**
 *
 * @author tnica
 */
public interface ContatoDAO extends DaoGeneric<Contato>{
    
    List<Contato> findByNome(String nome);

}

