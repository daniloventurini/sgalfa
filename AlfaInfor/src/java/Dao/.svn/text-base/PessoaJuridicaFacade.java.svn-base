/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;


import Model.Matricula;
import Model.PessoaFisica;
import Model.PessoaJuridica;
import Model.Plano;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class PessoaJuridicaFacade extends AbstractFacade<PessoaJuridica> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PessoaJuridicaFacade() {
        super(PessoaJuridica.class);
    }


    @Override
    public PessoaJuridica find(Object id) {
        //return super.find(id);
        PessoaJuridica p = em.find(PessoaJuridica.class, id);

        p.getTelefone().size();
        return p;
    }



  

}
