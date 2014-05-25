/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Converter;

import Dao.AbstractFacade;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class ConverterGenerico1 implements Converter, Serializable{

    private AbstractFacade dao;

    public ConverterGenerico1(AbstractFacade dao) {
        this.dao = dao;
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {

         try {
           return dao.find(Long.parseLong(value));
        } catch (Exception ex) {
            //Evita mensagens durante a digitaçao parcial.
            //logger.log(Level.SEVERE, "Problema ao instanciar a chave", ex);
        }
        return null;




    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
       
            if (value != null) {
            return String.valueOf(value);
        } else {
            return null;
        }
    }


}
