/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class PessoaFisica extends Pessoa implements Serializable {
     private static final long serialVersionUID = 1L;
   
  
    @Column(length = 20, unique=true)
    private String cpf;
    @Column(length = 20)
    private String rg;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datanascimento;
    @Column(length = 20)
    private String apelido;
    @Column(length = 20)
    private String localtrabalho;
    @Column(length = 200)
    private String referencia;
    @Column
    private Integer idade;
    @Column(length = 20)
    private String sexo;

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

  

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public Integer getIdade() {
         return idade;

    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

 
    public String getLocaltrabalho() {
        return localtrabalho;
    }

    public void setLocaltrabalho(String localtrabalho) {
        this.localtrabalho = localtrabalho;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String getCpfCnpj() {
       return cpf;
    }






}
