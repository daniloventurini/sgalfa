/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.ContasPagar;
import Model.ContasReceber;
import Model.Grupo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ContasReceberFacade extends AbstractFacade<ContasReceber> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ContasReceberFacade() {
        super(ContasReceber.class);
    }

       @Override
    public ContasReceber find(Object id) {
        //return super.find(id);
        ContasReceber v = em.find(ContasReceber.class, id);
        v.getCheques().size(); //tah certo

        return v;
    }
}
