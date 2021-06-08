package com.tnicacio.agendarest.model.dao;

import com.tnicacio.agendarest.model.dao.impl.CompromissoDaoEM;
import com.tnicacio.agendarest.model.dao.impl.ContatoDaoEM;
import com.tnicacio.agendarest.model.dao.impl.LocalDaoEM;
import com.tnicacio.agendarest.model.dao.impl.ParticipanteDaoEM;
import com.tnicacio.agendarest.model.em.EM;

/**
 *
 * @author tnica
 */
public class DaoFactory {
    
    public static ContatoDAO createContatoDao(){
        return new ContatoDaoEM(EM.getEntityManager());
    }

    public static ParticipanteDAO createParticipanteDao(){
        return new ParticipanteDaoEM(EM.getEntityManager());
    }

    public static LocalDAO createLocalDao(){
        return new LocalDaoEM(EM.getEntityManager());
    }

    public static CompromissoDAO createCompromissoDao(){
        return new CompromissoDaoEM(EM.getEntityManager());
    }
}
