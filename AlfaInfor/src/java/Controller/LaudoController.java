/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Converter.ConverterGenerico1;
import Dao.LaudoFacade;
import Dao.FuncionarioFacade;
import Dao.PessoaFacade;
import Model.Emprestimo;
import Model.Laudo;
import Model.Funcionario;
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

/**
 *
 * @author José Alexandre
 */
@ManagedBean
@SessionScoped
public class LaudoController implements Serializable {

    private Laudo laudo;
    private DataModel listaLaudos;
    @EJB
    private LaudoFacade dao;
    @EJB
    private PessoaFacade pessoadao;
    @EJB
    private FuncionarioFacade funcionariodao;
    private Usuario usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PessoaFacade getPessoadao() {
        return pessoadao;
    }

    public void setPessoadao(PessoaFacade pessoadao) {
        this.pessoadao = pessoadao;
    }

    public FuncionarioFacade getFuncionariodao() {
        return funcionariodao;
    }

    public void setFuncionariodao(FuncionarioFacade funcionariodao) {
        this.funcionariodao = funcionariodao;
    }
    private Converter pessoaConverter;
    private Converter funcionarioConverter;

    public Converter getFuncionarioConverter() {
        if (funcionarioConverter == null) {
            funcionarioConverter = new ConverterGenerico(funcionariodao);
        }
        return funcionarioConverter;
    }

    public void setFuncionarioConverter(Converter funcionarioConverter) {
        this.funcionarioConverter = funcionarioConverter;
    }

    public Converter getPessoaConverter() {
        if (pessoaConverter == null) {
            pessoaConverter = new ConverterGenerico1(pessoadao);
        }
        return pessoaConverter;
    }

    public void setPessoaConverter(Converter pessoaConverter) {
        this.pessoaConverter = pessoaConverter;
    }

    public LaudoFacade getDao() {
        return dao;
    }

    public void setDao(LaudoFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarLaudos() {
        List<Laudo> lista = getDao().findAll();
        listaLaudos = new ListDataModel(lista);
        return listaLaudos;
    }

    public Laudo getLaudo() {
        return laudo;
    }

    public void setLaudo(Laudo laudo) {
        this.laudo = laudo;
    }

    public void prepararAdicionarLaudo() {
        laudo = new Laudo();
        laudo.setDataLaudo(new Date());

    }

    public void prepararAlterarLaudo(ActionEvent e) {
        setLaudo((Laudo) e.getComponent().getAttributes().get("laudo"));

    }

    public void excluirLaudo() {



        try {
            dao.remove(laudo);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }



    }

    public void salvarLaudo() {
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                usuario.setLogin(((User) authentication.getPrincipal()).getUsername());
                laudo.setUsuario(usuario.getLogin());
            }
        }


        dao.save(laudo);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "LAUDO CADASTRADO/ALTERADO COM SUCESSO", "LAUDO CADASTRADO/ALTERADO COM SUCESSO"));


    }

    public List<SelectItem> getPessoas() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Pessoa object : pessoadao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<SelectItem> getFuncionarios() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Funcionario object : funcionariodao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<Pessoa> completaPessoa(String parte) {
        return pessoadao.listaFiltrando(parte, "nome");
    }

    //------------------------------RELATÓRIOS----------------------------------------------------------
    public void imprimeRelatorioData() throws IOException, SQLException {
        ArrayList<Laudo> lista = new ArrayList<Laudo>();
        lista = (ArrayList<Laudo>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
        parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/laudoData.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"laudoData.pdf\"");
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

    public void imprimeRelatorio() throws IOException, SQLException {
        ArrayList<Laudo> lista = new ArrayList<Laudo>();
        lista = (ArrayList<Laudo>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pCliente", "%" + filtro + "%");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/laudoCliente.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"laudoCliente.pdf\"");
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
        ArrayList<Laudo> lista = new ArrayList<Laudo>();
        lista = (ArrayList<Laudo>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/laudoTotal.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"laudoTotal.pdf\"");
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
