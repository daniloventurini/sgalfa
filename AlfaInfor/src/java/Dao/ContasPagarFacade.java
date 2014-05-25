/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.ContasPagar;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ContasPagarFacade extends AbstractFacade<ContasPagar> {

    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ContasPagarFacade() {
        super(ContasPagar.class);
    }

    @Override//sobrescreve o metodo
    public ContasPagar find(Object id) {
        //return super.find(id);
        ContasPagar v = em.find(ContasPagar.class, id);
        v.getCheques().size(); //tah certo

        return v;
    }
}
