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
public class Aluno extends PessoaFisica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(length=100)
    private String nomeRespo;
    @Column(length=50)
    private String grauParentesco;
    @Column(length=20)
    private String rgRespo;
    @Column(length=17)
    private String cpfRespo;
    @Column(length=70)
    private String profissao;
    @Column(length=13)
    private String telefoneRespo;
    @Column(length=13)
    private String celularRespo;

    public String getCelularRespo() {
        return celularRespo;
    }

    public void setCelularRespo(String celularRespo) {
        this.celularRespo = celularRespo;
    }

    public String getCpfRespo() {
        return cpfRespo;
    }

    public void setCpfRespo(String cpfRespo) {
        this.cpfRespo = cpfRespo;
    }

    public String getGrauParentesco() {
        return grauParentesco;
    }

    public void setGrauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    public String getNomeRespo() {
        return nomeRespo;
    }

    public void setNomeRespo(String nomeRespo) {
        this.nomeRespo = nomeRespo;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getRgRespo() {
        return rgRespo;
    }

    public void setRgRespo(String rgRespo) {
        this.rgRespo = rgRespo;
    }

    public String getTelefoneRespo() {
        return telefoneRespo;
    }

    public void setTelefoneRespo(String telefoneRespo) {
        this.telefoneRespo = telefoneRespo;
    }






}
