/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;


import java.io.Serializable;
import java.util.List;

/**
 *
 * @author José Alexandre
 */
public interface DaoGenerico {

  public void save(Object objeto);

  public void update(Object objeto);

    public void delete(Object objeto);

    public List list(Class clazz);

    public List listCriterio(Class clazz, String atributo, String criterio);

    public Object getById(Serializable id, Class clazz);

}
