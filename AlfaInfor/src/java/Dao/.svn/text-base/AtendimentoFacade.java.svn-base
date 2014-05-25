/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Atendimento;
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
public class AtendimentoFacade extends AbstractFacade<Atendimento> {

    @PersistenceContext(unitName = "TesteJsfPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public AtendimentoFacade() {
        super(Atendimento.class);
    }

  public List<Atendimento> findByAtendimento(){
    String jpql = "select c from Atendimento c where c.status = :Ativo order by c.funcionario.nome";//seleciona toda cidades que possuam o estado com aquele ID
    Query query = em.createQuery(jpql).setParameter("Ativo", "Ativo");// aqui eu vo ta passando os paramentros pra aquela jpql? tipo to passando o c.estado que eh o parametro "estado"  e o meu estado que eh o objeto que vem do controller, ou num tem nada a ver
    List result = query.getResultList();
    return result;
    }
    }
