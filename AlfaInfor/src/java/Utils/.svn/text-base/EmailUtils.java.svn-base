/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;


import Model.Mensagem;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


/**
 *
 * @author DANILO
 */
public class EmailUtils {

    private static final String HOSTNAME = "smtp.gmail.com";
    private static final String USERNAME = "danilotr98";
    private static final String PASSWORD = "papai123";
    private static final String EMAILORIGEM = "danilotr98@gmail.com";

  public static Email conectaEmail() throws EmailException {
 Email email = new SimpleEmail();
 email.setHostName(HOSTNAME);
 email.setSmtpPort(587);
 email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
 email.setTLS(true);
 email.setFrom(EMAILORIGEM);
 return email;
 }
    public static void enviaEmail(Mensagem mensagem) throws EmailException {
        mensagem.setDestino("danilo_tr98@hotmail.com");
        Email email = (Email) new SimpleEmail();
        email = conectaEmail();
        email.setSubject(mensagem.getTitulo());
        email.setMsg(mensagem.getMensagem());
        email.addTo(mensagem.getDestino());
        String resposta = email.send();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail enviado com sucesso para: " + mensagem.getDestino(), "Informação"));
    }
}
