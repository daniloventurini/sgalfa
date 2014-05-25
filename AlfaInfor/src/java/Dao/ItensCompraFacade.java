/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Grupo;
import Model.ItensCompra;
import Model.Pessoa;
import Model.Produto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class ItensCompraFacade extends AbstractFacade<ItensCompra> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ItensCompraFacade() {
        super(ItensCompra.class);
    }
}
