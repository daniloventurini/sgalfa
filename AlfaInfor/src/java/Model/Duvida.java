/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author DANILO
 */
@Entity
public class Duvida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length=50)
    private String autor;
    @Column(length=300)
    private String descricao;
    @Column(length=14)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDuvida;
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OneToMany(cascade=javax.persistence.CascadeType.ALL)
    private List<RespostaDuvida> respostaDuvida = new ArrayList<RespostaDuvida>();

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }



    public List<RespostaDuvida> getRespostaDuvida() {
        return respostaDuvida;
    }

    public void setRespostaDuvida(List<RespostaDuvida> respostaDuvida) {
        this.respostaDuvida = respostaDuvida;
    }



    public Date getDataDuvida() {
        return dataDuvida;
    }

    public void setDataDuvida(Date dataDuvida) {
        this.dataDuvida = dataDuvida;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Duvida)) {
            return false;
        }
        Duvida other = (Duvida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

}
