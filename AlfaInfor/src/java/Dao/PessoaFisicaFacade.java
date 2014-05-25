/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;



import Model.PessoaFisica;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class PessoaFisicaFacade extends AbstractFacade<PessoaFisica> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PessoaFisicaFacade() {
        super(PessoaFisica.class);
    }

 @Override
    public PessoaFisica find(Object id) {
        //return super.find(id);
        PessoaFisica p = em.find(PessoaFisica.class, id);

        p.getTelefone().size();
        return p;
    }
  

}
