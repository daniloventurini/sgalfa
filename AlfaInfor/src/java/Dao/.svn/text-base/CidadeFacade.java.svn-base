/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Cidade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jaime
 */
@Stateless
public class CidadeFacade extends AbstractFacade<Cidade> {
    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CidadeFacade() {
        super(Cidade.class);
    }

    public List<Cidade> findByEstado(String estado){
    String jpql = "select c from Cidade c where c.estado = :estado";//seleciona todas as cidades que possuam o estado com aquele ID
    Query query = em.createQuery(jpql).setParameter("estado", estado);// aqui eu vo ta passando os paramentros pra aquela jpql? tipo to passando o c.estado que eh o parametro "estado"  e o meu estado que eh o objeto que vem do controller, ou num tem nada a ver
    List result = query.getResultList();
    return result;


    }
}
