/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;



import Model.Aluno;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class AlunoFacade extends AbstractFacade<Aluno> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public AlunoFacade() {
        super(Aluno.class);
    }

 @Override
    public Aluno find(Object id) {
        //return super.find(id);
        Aluno p = em.find(Aluno.class, id);

        p.getTelefone().size();
        return p;
    }
  

}
