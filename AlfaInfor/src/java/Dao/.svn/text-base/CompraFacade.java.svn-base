/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Compra;
import Model.Grupo;
import Model.Produto;
import Model.Venda;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class CompraFacade extends AbstractFacade<Compra> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CompraFacade() {
        super(Compra.class);
    }

     @Override
    public Compra find(Object id) {
        //return super.find(id);
        Compra c = em.find(Compra.class, id);
        c.getItensCompra().size();
        c.getContasaPagar().size();
        return c;
    }

}
