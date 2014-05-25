/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Dao.CidadeFacade;
import Dao.PessoaJuridicaFacade;
import Model.Cidade;
import Model.PessoaJuridica;
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
import javax.swing.text.html.parser.Element;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class PessoaJuridicaController implements Serializable {

    private PessoaJuridica pessoaJuridica;
    private DataModel listaPessoaJuridicas;
    private DataModel listaTelefones;
    private String estado;
    private Telefone telefone;
    @EJB
    private PessoaJuridicaFacade dao;
    @EJB
    private CidadeFacade cidadedao;
    private Converter converterCidade;

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public DataModel getListaTelefones() {
        return listaTelefones;
    }

    public void setListaTelefones(DataModel listaTelefones) {
        this.listaTelefones = listaTelefones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Converter getConverterCidade() {
        if (converterCidade == null) {
            converterCidade = new ConverterGenerico(cidadedao);
        }
        return converterCidade;
    }

    public void setConverterCidade(Converter converterCidade) {
        this.converterCidade = converterCidade;
    }

    public CidadeFacade getCidadedao() {
        return cidadedao;
    }

    public void setCidadedao(CidadeFacade cidadedao) {
        this.cidadedao = cidadedao;
    }

    public PessoaJuridicaFacade getDao() {
        return dao;
    }

    public void setDao(PessoaJuridicaFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarPessoaJuridicas() {
        List<PessoaJuridica> lista = getDao().findAll();
        listaPessoaJuridicas = new ListDataModel(lista);
        return listaPessoaJuridicas;
    }

    public DataModel getListarTelefones() {
        List<Telefone> telefones = getPessoaJuridica().getTelefone();
        listaTelefones = new ListDataModel(telefones);
        return listaTelefones;

    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public void prepararAdicionarPessoaJuridica() {
        pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setDataCadastro(new Date());
        telefone = new Telefone();
        estado = null;

    }

    public void prepararAlterarPessoaJuridica(ActionEvent e) {
        PessoaJuridica p = (PessoaJuridica) (e.getComponent().getAttributes().get("pessoaJuridica"));
        pessoaJuridica = dao.find(p.getId());
        telefone = new Telefone();
        estado = pessoaJuridica.getCidade().getEstado();


    }

    public void excluirPessoaJuridica() {



        try {
            dao.remove(pessoaJuridica);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }


    }

    public void salvarPessoaJuridica() {

        try {
            dao.save2(pessoaJuridica);
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaPessoaJ.jsf");
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "PESSOA CADASTRADA/ALTERADA COM SUCESSO", "PESSOA CADASTRADA/ALTERADA COM SUCESSO"));
        } catch (Exception e) {
            if (e.getCause().getMessage().contains("cnpj")) {
                FacesContext.getCurrentInstance().addMessage("cnpj", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro", "CNPJ já Cadastrado. Tente Novamente!"));
                //lança exception cpf
            }
            //lança outra exception qualquer
            FacesContext.getCurrentInstance().addMessage("cnpj", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro", "CNPJ ja Cadastrado!"));

        }



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
        pessoaJuridica.getTelefone().add(telefone);
        telefone = new Telefone();
    }

    public void selecionaTelefone(ActionEvent actionEvent) {
        telefone = (Telefone) (actionEvent.getComponent().getAttributes().get("telefone"));

    }

    public void removeTelefone() {
        pessoaJuridica.getTelefone().remove(telefone);
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";
        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Pessoas Jurídicas");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
    }

    public void imprimeRelatorio() throws IOException, SQLException {


        ArrayList<PessoaJuridica> lista = new ArrayList<PessoaJuridica>();
        lista = (ArrayList<PessoaJuridica>) getDao().findAll();

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();

            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/pessoaj.jasper"), parameters, c.getConnection());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType("application/pdf");

                response.setHeader("Content-disposition", "inline; filename=\"pessoaj.pdf\"");

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
