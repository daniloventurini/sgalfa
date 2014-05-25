/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;


import Model.Plano;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class PlanoFacade extends AbstractFacade<Plano> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanoFacade() {
        super(Plano.class);
    }
}
