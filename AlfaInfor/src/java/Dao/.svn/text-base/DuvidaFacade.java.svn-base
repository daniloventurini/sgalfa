/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Duvida;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class DuvidaFacade extends AbstractFacade<Duvida> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public DuvidaFacade() {
        super(Duvida.class);
    }

       @Override
    public Duvida find(Object id) {
        //return super.find(id);
        Duvida v = em.find(Duvida.class, id);
        v.getRespostaDuvida().size(); //tah certo

        return v;
    }
}
