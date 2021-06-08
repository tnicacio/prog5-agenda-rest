package com.tnicacio.agendarest.model.dao;

import com.tnicacio.agendarest.model.entities.Compromisso;
import java.util.List;

/**
 *
 * @author tnica
 */
public interface CompromissoDAO extends DaoGeneric<Compromisso>{
 
    List<Compromisso> findByLocalId(Long localId);
    List<Compromisso> findByContatoId(Long contatoId);
}

