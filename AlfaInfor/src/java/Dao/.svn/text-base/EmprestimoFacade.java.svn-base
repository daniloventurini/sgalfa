/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;




import Model.Emprestimo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jaime
 */
@Stateless
public class EmprestimoFacade extends AbstractFacade<Emprestimo> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public EmprestimoFacade() {
        super(Emprestimo.class);
    }


  

}
