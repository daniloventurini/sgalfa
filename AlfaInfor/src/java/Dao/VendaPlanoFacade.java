/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Grupo;
import Model.Pessoa;
import Model.Produto;
import Model.VendaPlano;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class VendaPlanoFacade extends AbstractFacade<VendaPlano> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public VendaPlanoFacade() {
        super(VendaPlano.class);
    }
}
