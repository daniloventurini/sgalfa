/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author DANILO
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class PessoaJuridica extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Column(length=100)
    private String razaosocial;
    @Column(length=20, unique=true)
    private String cnpj;
    @Column(length=50)
    private String inscricao;
    @Column(length=15)
    private String fax;
    @Column(length=15)
    private String telefonepj;
    @Column(length=50)
    private String email;
    @Column(length=50)
    private String nomeProprietario;

    public String getTelefonepj() {
        return telefonepj;
    }

    public void setTelefonepj(String telefonepj) {
        this.telefonepj = telefonepj;
    }






    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public PessoaJuridica() {
    }

    @Override
    public String getCpfCnpj() {
        return cnpj;
    }





}
