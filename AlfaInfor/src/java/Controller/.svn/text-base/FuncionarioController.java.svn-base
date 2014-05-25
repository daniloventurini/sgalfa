/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Dao.CargoFacade;
import Dao.CidadeFacade;
import Dao.DaoGenerico;
import Dao.FuncionarioFacade;
import Model.Cargo;
import Model.Cidade;
import Model.Funcionario;
import Model.Grupo;
import Model.Telefone;
import Model.TipoTelefone;
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
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author José Alexandre
 */
@ManagedBean
@SessionScoped
public class FuncionarioController implements Serializable {

    private Funcionario funcionario;
    private DataModel listaFuncionarios;
    private DataModel listaTelefones;
    private String estado;
    @EJB
    private FuncionarioFacade dao;
    @EJB
    private CargoFacade cargodao;
    @EJB
    private CidadeFacade cidadedao;
    private Telefone telefone;

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DataModel getListaTelefones() {
        return listaTelefones;
    }

    public void setListaTelefones(DataModel listaTelefones) {
        this.listaTelefones = listaTelefones;
    }

    public CidadeFacade getCidadedao() {
        return cidadedao;
    }

    public void setCidadedao(CidadeFacade cidadedao) {
        this.cidadedao = cidadedao;
    }
    private Converter converterCargo;
    private Converter converterCidade;

    public Converter getConverterCidade() {
        if (converterCidade == null) {
            converterCidade = new ConverterGenerico(cidadedao);
        }
        return converterCidade;
    }

    public void setConverterCidade(Converter converterCidade) {
        this.converterCidade = converterCidade;
    }

    public Converter getConverterCargo() {
        if (converterCargo == null) {
            converterCargo = new ConverterGenerico(cargodao);
        }
        return converterCargo;
    }

    public void setConverterCargo(Converter converterCargo) {
        this.converterCargo = converterCargo;
    }

    public CargoFacade getCargodao() {
        return cargodao;
    }

    public void setCargodao(CargoFacade cargodao) {
        this.cargodao = cargodao;
    }

    public FuncionarioFacade getDao() {
        return dao;
    }

    public void setDao(FuncionarioFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarFuncionarios() {
        List<Funcionario> lista = getDao().findAll();
        listaFuncionarios = new ListDataModel(lista);
        return listaFuncionarios;
    }

    public DataModel getListarTelefones() {
        List<Telefone> telefones = getFuncionario().getTelefone();
        listaTelefones = new ListDataModel(telefones);
        return listaTelefones;

    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void prepararAdicionarFuncionario() {
        funcionario = new Funcionario();

        funcionario.setDataadmissao(new Date());
        telefone = new Telefone();
        estado = null;


    }

    public void prepararAlterarFuncionario(ActionEvent e) {
        Funcionario p = (Funcionario) (e.getComponent().getAttributes().get("funcionario"));
        funcionario = dao.find(p.getId());
        telefone = new Telefone();
        estado = funcionario.getCidade().getEstado();


    }

    public void excluirFuncionario() {



        try {
            dao.remove(funcionario);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }



    }

    public void salvarFuncionario() {

        try {
            dao.save2(funcionario);
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaFuncionario.jsf");
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "FUNCIONÁRIO CADASTRADO/ALTERADO COM SUCESSO", "FUNCIONÁRIO CADASTRADO/ALTERADO COM SUCESSO"));
        } catch (Exception e) {
            if (e.getCause().getMessage().contains("cpf")) {
                FacesContext.getCurrentInstance().addMessage("cpf", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro", "CPF já Cadastrado. Tente Novamente!"));
                //lança exception cpf
            }
            //lança outra exception qualquer
            FacesContext.getCurrentInstance().addMessage("cpf", new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF já Cadastrado", "CPF já Cadastrado!"));

        }


    }

    public List<SelectItem> getCargos() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Cargo object : cargodao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<SelectItem> getCidades() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Cidade object : cidadedao.findByEstado(estado)) {
            toReturn.add(new SelectItem(object, object.getNomeCidade()));
        }
        return toReturn;
    }

    public List<SelectItem> getTiposTelefones() {
        List<SelectItem> lista = new LinkedList<SelectItem>();
        for (TipoTelefone tt : TipoTelefone.values()) {
            lista.add(new SelectItem(tt, tt.toString()));
        }
        return lista;
    }

    public void addTelefone() {
        funcionario.getTelefone().add(telefone);
        telefone = new Telefone();
    }

    public void selecionaTelefone(ActionEvent actionEvent) {
        telefone = (Telefone) (actionEvent.getComponent().getAttributes().get("telefone"));

    }

    public void removeTelefone() {
        funcionario.getTelefone().remove(telefone);
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Funcionários");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
    }

    public void imprimeRelatorio() throws IOException, SQLException {


        ArrayList<Funcionario> lista = new ArrayList<Funcionario>();
        lista = (ArrayList<Funcionario>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/funcionario.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"funcionario.pdf\"");
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
