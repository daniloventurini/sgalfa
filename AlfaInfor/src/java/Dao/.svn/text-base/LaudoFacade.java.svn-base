/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Cidade;
import Model.Laudo;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class LaudoFacade extends AbstractFacade<Laudo> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public LaudoFacade() {
        super(Laudo.class);
    }
}
