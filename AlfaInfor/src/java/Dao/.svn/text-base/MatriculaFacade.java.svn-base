/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;


import Model.Matricula;
import Model.Plano;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class MatriculaFacade extends AbstractFacade<Matricula> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculaFacade() {
        super(Matricula.class);
    }


     @Override
    public Matricula find(Object id) {
        //return super.find(id);
        Matricula v = em.find(Matricula.class, id);
        v.getCursosMatriculados().size();
        return v;
    }

}
