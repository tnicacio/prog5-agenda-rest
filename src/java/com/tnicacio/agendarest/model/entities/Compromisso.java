package com.tnicacio.agendarest.model.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tnica
 */
@Entity
@Table(name = "tb_compromisso")
public class Compromisso  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Expose
    private Date data;
    
    @Temporal(javax.persistence.TemporalType.TIME)
    @Expose
    private Date hora;
    
    @Temporal(javax.persistence.TemporalType.TIME)
    @Expose
    private Date duracao;
    
    @ManyToOne(cascade = { CascadeType.REMOVE, CascadeType.MERGE })
    @NotNull(message = "Local não pode ser nulo")
    @JoinColumn(name = "local_id", nullable = false)
    @Expose
    private Local local;
    
    @OneToMany(mappedBy = "compromisso", cascade = { CascadeType.REMOVE, CascadeType.MERGE }, orphanRemoval = true)
    private List<Participante> participantes = new ArrayList<>();
    
    public Compromisso(){}

    public Compromisso(Long id, Date data, Time hora, Time duracao, Local local) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.duracao = duracao;
        this.local = local;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getDuracao() {
        return duracao;
    }

    public void setDuracao(Date duracao) {
        this.duracao = duracao;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
    
    public List<Participante> getParticipantes() {
        return participantes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Compromisso other = (Compromisso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
