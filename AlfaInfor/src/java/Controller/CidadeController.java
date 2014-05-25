/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import Dao.CidadeFacade;
import Model.Cidade;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;


@ManagedBean
@SessionScoped
public class CidadeController implements Serializable{

    private Cidade cidade;
    private DataModel listaCidades;
    @EJB
   private CidadeFacade dao;

    public CidadeFacade getDao() {
        return dao;
    }

    public void setDao(CidadeFacade dao) {
        this.dao = dao;
    }


    public DataModel getListarCidades() {
         List<Cidade> lista = getDao().findAll();
        listaCidades = new ListDataModel(lista);
        return listaCidades;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public void prepararAdicionarCidade(){
        cidade = new Cidade();
       
    }

    public void prepararAlterarCidade(ActionEvent e){
          setCidade( (Cidade) e.getComponent().getAttributes().get("cidade"));
     
    }

    public void  excluirCidade(){

        
        
           try{
         dao.remove(cidade);
          RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

     }catch(Exception e){
     FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro na tentativa de exclusão", "A Cidade possui dependências"));
     }
       

    }

    public void salvarCidade(){

        dao.save(cidade);
        RequestContext requestContext = RequestContext.getCurrentInstance();
       FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "CIDADE CADASTRADA/ALTERADA COM SUCESSO", "CIDADE CADASTRADA/ALTERADA COM SUCESSO"));
      
        
    }


    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
   Document pdf = (Document) document;
    pdf.open();
    pdf.setPageSize(PageSize.A4);

    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

    pdf.add(Image.getInstance(logo));
     Paragraph p = new Paragraph("Relatório de Cidades");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
}



}
