/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;


import Model.ItensVenda;
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
public class ItensVendaFacade extends AbstractFacade<ItensVenda> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ItensVendaFacade() {
        super(ItensVenda.class);
    }
}
