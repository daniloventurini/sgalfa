/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.CursosMatriculados;
import Model.Grupo;
import Model.Produto;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class CursosMatriculadosFacade extends AbstractFacade<CursosMatriculados> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CursosMatriculadosFacade() {
        super(CursosMatriculados.class);
    }
}
