/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

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
public class VendaFacade extends AbstractFacade<Venda> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public VendaFacade() {
        super(Venda.class);
    }

     @Override
    public Venda find(Object id) {
        //return super.find(id);
        Venda v = em.find(Venda.class, id);
        v.getItensVenda().size();
        v.getContasaReceber().size();
        return v;
    }

}
