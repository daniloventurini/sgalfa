/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico1;
import Dao.ContasReceberFacade;
import Dao.PessoaFacade;
import Model.ContasReceber;
import Model.Pessoa;
import Model.Usuario;
import Utils.Conexao;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@ManagedBean
@SessionScoped
public class ContasReceberController implements Serializable {

    private ContasReceber contasReceber;
    private DataModel listaContasRecebers;
    @EJB
    private ContasReceberFacade dao;
    @EJB
    private PessoaFacade pessoadao;
    private Converter converterPessoa;
    private Date dtinicio;
    private Date dtfim;
    private String filtro;
    private String status;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public ContasReceberFacade getDao() {
        return dao;
    }

    public void setDao(ContasReceberFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarContasRecebers() {
        List<ContasReceber> lista = getDao().findAll();
        listaContasRecebers = new ListDataModel(lista);
        return listaContasRecebers;
    }

    public ContasReceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(ContasReceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    public void prepararAdicionarContasReceber() {
        contasReceber = new ContasReceber();
        contasReceber.setDataConta(new Date());

    }

    public void prepararAlterarContasReceber(ActionEvent e) {
        setContasReceber((ContasReceber) e.getComponent().getAttributes().get("contasReceber"));


    }

    public void excluirContasReceber() {




        try {
            dao.remove(contasReceber);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }



    }

    public List<SelectItem> getPessoas() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Pessoa object : pessoadao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public void PegaUsuario() {
        //  pega o usuário logado
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                usuario.setLogin(((User) authentication.getPrincipal()).getUsername());
                contasReceber.setUsuario(usuario.getLogin());

            }
        }
    }

    public void salvarContasReceber() {

        PegaUsuario();
        dao.save(contasReceber);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "CONTA CADASTRADA/ALTERADA COM SUCESSO", "CONTA CADASTRADA/ALTERADA COM SUCESSO"));
    }

    public Date getDataAtual() {
        return new Date();
    }

    public void mudaStatus() {
        contasReceber.setStatus("Liquidado");
        PegaUsuario();
        dao.save(contasReceber);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "CONTA LIQUIDADA", "CONTA LIQUIDADA"));
    }

    public List<Pessoa> completaPessoa(String parte) {
        return pessoadao.listaFiltrando(parte, "nome");
    }

    //  ----------------------------------------RELATÓRIOS------------------------------------------------------------------
    public void imprimeRelatorio() throws IOException, SQLException {
        ArrayList<ContasReceber> lista = new ArrayList<ContasReceber>();
        lista = (ArrayList<ContasReceber>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pCliente", "%" + filtro + "%");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/contasReceber.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"contasReceber.pdf\"");
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

    //--------------------------teste de relatorio------------------------------------------------------
    public void imprimeRelatorioDataeCliente() throws IOException, SQLException {
        if (filtro == null) {
            if (dtinicio == null || dtfim == null) {
                System.out.println("------------------------------relatorio sem data");
                ArrayList<ContasReceber> lista = new ArrayList<ContasReceber>();
                lista = (ArrayList<ContasReceber>) getDao().findAll();
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
                Conexao c = new Conexao();
                HashMap parameters = new HashMap();
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.responseComplete();
                    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/contasReceberData.jasper"), parameters, c.getConnection());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.exportReport();
                    byte[] bytes = baos.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=\"contasReceberData.pdf\"");
                        response.setContentLength(bytes.length);
                        ServletOutputStream outputStream = response.getOutputStream();
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("------------------------------relatorio com data");
                ArrayList<ContasReceber> lista = new ArrayList<ContasReceber>();
                lista = (ArrayList<ContasReceber>) getDao().findAll();
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
                Conexao c = new Conexao();
                HashMap parameters = new HashMap();
                parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
                parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.responseComplete();
                    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/contasReceberData.jasper"), parameters, c.getConnection());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.exportReport();
                    byte[] bytes = baos.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=\"contasReceberData.pdf\"");
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
        } else {
            if (dtinicio == null || dtfim == null) {
                System.out.println("------------------------------relatorio sem data");
                ArrayList<ContasReceber> lista = new ArrayList<ContasReceber>();
                lista = (ArrayList<ContasReceber>) getDao().findAll();
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
                Conexao c = new Conexao();
                HashMap parameters = new HashMap();
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.responseComplete();
                    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/contasReceberData.jasper"), parameters, c.getConnection());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.exportReport();
                    byte[] bytes = baos.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=\"contasReceberData.pdf\"");
                        response.setContentLength(bytes.length);
                        ServletOutputStream outputStream = response.getOutputStream();
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("------------------------------relatorio com data");
                ArrayList<ContasReceber> lista = new ArrayList<ContasReceber>();
                lista = (ArrayList<ContasReceber>) getDao().findAll();
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
                Conexao c = new Conexao();
                HashMap parameters = new HashMap();
                parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
                parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
                parameters.put("pCliente", "%" + filtro + "%");
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.responseComplete();
                    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/contasReceberData.jasper"), parameters, c.getConnection());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.exportReport();
                    byte[] bytes = baos.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=\"contasReceberData.pdf\"");
                        response.setContentLength(bytes.length);
                        ServletOutputStream outputStream = response.getOutputStream();
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dtinicio = null;
                dtfim = null;
                filtro = null;
            }
        }
    }

    public void imprimeRelatorioClienteStatus() throws IOException, SQLException {
        ArrayList<ContasReceber> lista = new ArrayList<ContasReceber>();
        lista = (ArrayList<ContasReceber>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pCliente", "%" + filtro + "%");
        parameters.put("pStatus", "%" + status + "%");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/contasReceberClienteeStatus.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"contasReceberClienteeStatus.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filtro = null;
        status = null;
    }

    public void imprimeRelatorioClienteDebito() throws IOException, SQLException {
        ArrayList<ContasReceber> lista = new ArrayList<ContasReceber>();
        lista = (ArrayList<ContasReceber>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
        parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/clienteemDebito.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"clienteemDebito.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dtinicio = null;
        dtfim = null;
    }

    public void imprimeRelatorioCheques() throws IOException, SQLException {
        ArrayList<ContasReceber> lista = new ArrayList<ContasReceber>();
        lista = (ArrayList<ContasReceber>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
        parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/cheque.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"cheque.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dtinicio = null;
        dtfim = null;
    }
}
