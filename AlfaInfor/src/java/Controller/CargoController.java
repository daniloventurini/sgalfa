/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.CargoFacade;
import Model.Cargo;
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
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class CargoController implements Serializable {

    private Cargo Cargo;
    private DataModel listaCargos;
    @EJB
    private CargoFacade dao;

    public CargoFacade getDao() {
        return dao;
    }

    public void setDao(CargoFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarCargos() {
        List<Cargo> lista = getDao().findAll();
        listaCargos = new ListDataModel(lista);
        return listaCargos;
    }

    public Cargo getCargo() {
        return Cargo;
    }

    public void setCargo(Cargo Cargo) {
        this.Cargo = Cargo;
    }

    public void prepararAdicionarCargo() {
        Cargo = new Cargo();

    }

    public void prepararAlterarCargo(ActionEvent e) {
        setCargo((Cargo) e.getComponent().getAttributes().get("Cargo"));

    }

    public void excluirCargo() {



        try {
            dao.remove(Cargo);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }



    }

    public void salvarCargo() {

        dao.save(Cargo);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "CARGO CADASTRADO/ALTERADO COM SUCESSO", "CARGO CADASTRADO/ALTERADO COM SUCESSO"));

    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Cargos");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
    }

    public void imprimeRelatorio() throws IOException, SQLException {


        ArrayList<Cargo> lista = new ArrayList<Cargo>();
        lista = (ArrayList<Cargo>) getDao().findAll();

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();

            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/cargo.jasper"), parameters, c.getConnection());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType("application/pdf");

                response.setHeader("Content-disposition", "inline; filename=\"cargo.pdf\"");

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
