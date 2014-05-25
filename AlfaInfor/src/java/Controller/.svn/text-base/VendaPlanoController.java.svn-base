/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Converter.ConverterGenerico1;
import Dao.PessoaFacade;
import Dao.PlanoFacade;
import Dao.VendaPlanoFacade;
import Model.Grupo;
import Model.VendaPlano;
import Model.Pessoa;
import Model.Plano;
import Utils.Conexao;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class VendaPlanoController implements Serializable {

    private VendaPlano vendaPlano;
    private DataModel listaVendaPlanos;
    private Pessoa pessoa;
    private Grupo grupo;
    @EJB
    private VendaPlanoFacade dao;
    @EJB
    private PessoaFacade pessoadao;
    @EJB
    private PlanoFacade planodao;
    private Date dtinicio;
    private Date dtfim;
    private String filtro;

    public Date getDtfim() {
        return dtfim;
    }

    public void setDtfim(Date dtfim) {
        this.dtfim = dtfim;
    }

    public Date getDtinicio() {
        return dtinicio;
    }

    public void setDtinicio(Date dtinicio) {
        this.dtinicio = dtinicio;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public PlanoFacade getPlanodao() {
        return planodao;
    }

    public void setPlanodao(PlanoFacade planodao) {
        this.planodao = planodao;
    }
    private Converter converterPessoa;
    private Converter converterPlano;

    public Converter getConverterPlano() {
        if (converterPlano == null) {
            converterPlano = new ConverterGenerico(planodao);
        }
        return converterPlano;
    }

    public void setConverterPlano(Converter converterPlano) {
        this.converterPlano = converterPlano;
    }

    public Converter getConverterPessoa() {
        if (converterPessoa == null) {
            converterPessoa = new ConverterGenerico1(pessoadao);
        }
        return converterPessoa;
    }

    public void setConverterPessoa(Converter converterPessoa) {
        this.converterPessoa = converterPessoa;
    }

    public PessoaFacade getPessoadao() {
        return pessoadao;
    }

    public void setPessoadao(PessoaFacade pessoadao) {
        this.pessoadao = pessoadao;
    }

    public VendaPlanoFacade getDao() {
        return dao;
    }

    public void setDao(VendaPlanoFacade dao) {
        this.dao = dao;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public DataModel getListaVendaPlanos() {
        return listaVendaPlanos;
    }

    public void setListaVendaPlanos(DataModel listaVendaPlanos) {
        this.listaVendaPlanos = listaVendaPlanos;
    }

    public DataModel getListarVendaPlanos() {
        List<VendaPlano> lista = getDao().findAll();
        listaVendaPlanos = new ListDataModel(lista);
        return listaVendaPlanos;
    }

    public VendaPlano getVendaPlano() {
        return vendaPlano;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setVendaPlano(VendaPlano vendaPlano) {
        this.vendaPlano = vendaPlano;
    }

    public void prepararAdicionarVendaPlano() {

        vendaPlano = new VendaPlano();

        vendaPlano.setVdesconto(BigDecimal.ZERO);
        vendaPlano.setDataVenda(new Date());

    }

    public void prepararAlterarVendaPlano(ActionEvent e) {
        setVendaPlano((VendaPlano) e.getComponent().getAttributes().get("vendaPlano"));

    }

    public void excluirVendaPlano() {



        try {
            dao.remove(vendaPlano);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui dependências"));
        }



    }

    public void salvarVendaPlano() {


        dao.save(vendaPlano);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "VENDA DE PLANO CADASTRADA/ALTERADA COM SUCESSO", "VENDA PLANO CADASTRADA/ALTERADA COM SUCESSO"));


    }

    public static Date addDias(Date date, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dias);

        return calendar.getTime();

    }

    public List<SelectItem> getPessoas() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Pessoa object : pessoadao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<SelectItem> getPlano() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Plano object : planodao.findAll()) {
            toReturn.add(new SelectItem(object, object.getDescricao()));
        }
        return toReturn;
    }

    public void calcular() {

        vendaPlano.setVcomdesconto(BigDecimal.ZERO);

    }

    public List<Pessoa> completaPessoa(String parte) {//alto Complete
        return pessoadao.listaFiltrando(parte, "nome");
    }

    //---------------------------------------------RELATÓRIOS-----------------------------------------------
    public void imprimeRelatorio() throws IOException, SQLException {
        ArrayList<VendaPlano> lista = new ArrayList<VendaPlano>();
        lista = (ArrayList<VendaPlano>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pCliente", "%" + filtro + "%");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/vendaPlanoCliente2.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"vendaPlanoCliente2.pdf\"");
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

    public void imprimeRelatorioData() throws IOException, SQLException {
        ArrayList<VendaPlano> lista = new ArrayList<VendaPlano>();
        lista = (ArrayList<VendaPlano>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
        parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/vendaPlanoData.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"vendaPlanoData.pdf\"");
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

    public void imprimeRelatorioTotal() throws IOException, SQLException {
        ArrayList<VendaPlano> lista = new ArrayList<VendaPlano>();
        lista = (ArrayList<VendaPlano>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/vendaPlanoTotal.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"vendaPlanoTotal.pdf\"");
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
