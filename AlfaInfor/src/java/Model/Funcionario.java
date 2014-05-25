/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import javax.persistence.Temporal;


@Entity
public class Funcionario extends PessoaFisica implements Serializable {
    private static final long serialVersionUID = 1L;
  
   
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataadmissao;
    @ManyToOne
    private Cargo cargo;

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

   

   





    public Date getDataadmissao() {
        return dataadmissao;
    }

    public void setDataadmissao(Date dataadmissao) {
        this.dataadmissao = dataadmissao;
    }


    public Funcionario() {
    }



 

    

  

}
