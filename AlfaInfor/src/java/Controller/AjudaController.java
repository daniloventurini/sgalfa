/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.Serializable;
import java.net.URL;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.help.HelpBroker;
import javax.help.HelpSet;

/**
 *
 * @author DANILO
 */
@ManagedBean//indica que aki eh um controlador
@SessionScoped//faz com que as visões possão enchergar esse controlador
public class AjudaController implements Serializable {

    private HelpSet hs;
    private HelpBroker hb;
    private URL hsURL;

    /**Este método abre o visualizador do help set e mostra
     *o ID do help set
     */
    public void openHelp() {
        // Identifica a localização do arquivo .hs
        String pathToHS = "/appwithhelp/docs/appwithhelp-hs.xml";
        //Cria a URL para a localização do help set
        try {
            hsURL = getClass().getResource(pathToHS);
            hs = new HelpSet(null, hsURL);
        } catch (Exception ee) {
            System.out.println("HelpSet " + ee.getMessage());
            System.out.println("Help Set " + pathToHS + " não encontrado");
            return;
        }

        // Cria um objeto HelpBroker para manipular o help set
        hb = hs.createHelpBroker();
        // Mostra o help set
        hb.setDisplayed(true);
    }
}
