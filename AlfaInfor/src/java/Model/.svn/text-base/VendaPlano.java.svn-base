/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author DANILO
 */
@Entity
public class VendaPlano implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Pessoa pessoa;
    @ManyToOne
    private Plano plano;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataVenda;
    @Column
    private BigDecimal vdesconto;
    @Column
    private BigDecimal vcomdesconto;
    @Column
    private String Obs;

    public String getObs() {
        return Obs;
    }

    public void setObs(String Obs) {
        this.Obs = Obs;
    }



    public BigDecimal getVcomdesconto() {
        return vcomdesconto;
    }

    public void setVcomdesconto(BigDecimal vcomdesconto) {
         BigDecimal t = this.plano.getValor().subtract((this.vdesconto));
        this.vcomdesconto = t;
    }

    public BigDecimal getVdesconto() {
        return vdesconto;
    }

    public void setVdesconto(BigDecimal vdesconto) {
        this.vdesconto = vdesconto;
    }


 


   
   

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

 
  



    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public VendaPlano() {
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
        if (!(object instanceof VendaPlano)) {
            return false;
        }
        VendaPlano other = (VendaPlano) object;
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
