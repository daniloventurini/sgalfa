/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;


import Model.Matricula;
import Model.Pessoa;
import Model.PessoaFisica;
import Model.Plano;
import Model.Telefone;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class TelefoneFacade extends AbstractFacade<Telefone> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TelefoneFacade() {
        super(Telefone.class);
    }


  

}
