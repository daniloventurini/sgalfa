/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Dao.DuvidaFacade;
import Model.Duvida;
import Model.RespostaDuvida;
import Utils.Conexao;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@ManagedBean
@SessionScoped
public class DuvidaController implements Serializable {

    private Duvida duvida;
    private DataModel listaDuvidas;
    private DataModel listaResposta;
    private RespostaDuvida resposta;
    private List<RespostaDuvida> respostas;
    @EJB
    private DuvidaFacade dao;

    public RespostaDuvida getResposta() {
        return resposta;
    }

    public void setResposta(RespostaDuvida resposta) {
        this.resposta = resposta;
    }

    public List<RespostaDuvida> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaDuvida> respostas) {
        this.respostas = respostas;
    }





    public DuvidaFacade getDao() {
        return dao;
    }

    public void setDao(DuvidaFacade dao) {
        this.dao = dao;
    }



    public DataModel getListarDuvidas() {
        List<Duvida> lista = getDao().findAll();
        listaDuvidas = new ListDataModel(lista);
        return listaDuvidas;
    }

    public DataModel getListarRespostas() {
        List<RespostaDuvida> lista = respostas;
        listaResposta = new ListDataModel(lista);
        return listaResposta;
    }

    public Duvida getDuvida() {
        return duvida;
    }

    public void setDuvida(Duvida duvida) {
        this.duvida = duvida;
    }

    public void prepararAdicionarDuvida() {
        duvida = new Duvida();
        duvida.setDataDuvida(new Date());
        resposta = new RespostaDuvida();
        respostas = new ArrayList<RespostaDuvida>();

    }

    public void prepararAlterarDuvida(ActionEvent e) {
        Duvida v = (Duvida) e.getComponent().getAttributes().get("duvida");
        duvida = dao.find(v.getId());
        resposta = new RespostaDuvida();
        Duvida d = dao.find(duvida.getId());
        respostas = d.getRespostaDuvida();

    }

    public void excluirDuvida() {



        try {
            dao.remove(duvida);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui dependências!"));
        }



    }

    public void salvarDuvida() {
        

        getDao().save(duvida);


    }

    public void  addResposta(){
    respostas.add(resposta);
    duvida.setRespostaDuvida(respostas);
    getDao().save(duvida);
    resposta = new RespostaDuvida();

    }

      public void selecionaResposta(ActionEvent e) {
        setResposta((RespostaDuvida) e.getComponent().getAttributes().get("resposta"));
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Duvidas");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
         p.setSpacingAfter(20);
        pdf.add(p);
    }

     public void imprimeRelatorio() throws IOException, SQLException {


        ArrayList<Duvida> lista = new ArrayList<Duvida>();
        lista = (ArrayList<Duvida>) getDao().findAll();

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();

            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/duvida.jasper"), parameters, c.getConnection());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType("application/pdf");

                response.setHeader("Content-disposition", "inline; filename=\"duvida.pdf\"");

                response.setContentLength(bytes.length);

                ServletOutputStream outputStream = response.getOutputStream();

                outputStream.write(bytes, 0, bytes.length);

                outputStream.flush();

                outputStream.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

}

}
}
