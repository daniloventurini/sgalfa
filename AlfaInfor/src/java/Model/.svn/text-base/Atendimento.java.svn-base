/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
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
public class Atendimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAtendimento;
    @Column
    private String horaServico;
    @Column
    private Double valorServico;
    @Column
    private Integer numeroNota;
    @Column(length = 50)
    private String status;
    @Column(length = 300)
    private String obs;
    @ManyToOne
    private Pessoa pessoa;
    @ManyToOne
    private Funcionario funcionario;
    @Column(length = 400)
    private String obsfunc;
    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }









    public String getObsfunc() {
        return obsfunc;
    }

    public void setObsfunc(String obsfunc) {
        this.obsfunc = obsfunc;
    }

    public Date getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(Date dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getHoraServico() {
        return horaServico;
    }

    public void setHoraServico(String horaServico) {
        this.horaServico = horaServico;
    }

    public Integer getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Integer numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorServico() {
        return valorServico;
    }

    public void setValorServico(Double valorServico) {
        this.valorServico = valorServico;
    }

    public Atendimento() {
    }

    public Atendimento(Long id, Date dataAtendimento, String horaServico, Double valorServico, Double valorVenda, Integer numeroNota, String status, String obs, Pessoa pessoa, Funcionario funcionario) {
        this.id = id;
        this.dataAtendimento = dataAtendimento;
        this.horaServico = horaServico;
        this.valorServico = valorServico;

        this.numeroNota = numeroNota;
        this.status = status;
        this.obs = obs;
        this.pessoa = pessoa;
        this.funcionario = funcionario;
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
        if (!(object instanceof Atendimento)) {
            return false;
        }
        Atendimento other = (Atendimento) object;
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
