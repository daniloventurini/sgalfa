/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Converter.ConverterGenerico1;
import Dao.AtendimentoFacade;
import Dao.FuncionarioFacade;
import Dao.PessoaFacade;
import Model.Atendimento;
import Model.Funcionario;
import Model.Pessoa;
import Model.Usuario;
import Model.Venda;
import Utils.Conexao;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
public class AtendimentoController implements Serializable {

    private Atendimento atendimento;
    private DataModel listaAtendimentos;
    private Boolean trava;
    @EJB
    private AtendimentoFacade dao;
    @EJB
    private PessoaFacade pessoadao;
    @EJB
    private FuncionarioFacade funcionariodao;
    private Usuario usuario;
    private Date dtinicio;
    private Date dtfim;
    private String filtro;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public AtendimentoFacade getDao() {
        return dao;
    }

    public void setDao(AtendimentoFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarAtendimentos() {//LISTA OS ATENDIMENTOS
        List<Atendimento> lista = getDao().findAll();
        listaAtendimentos = new ListDataModel(lista);
        return listaAtendimentos;
    }
    
      public DataModel getListarAtendimentosMobile() {//LISTA OS ATENDIMENTOS
        List<Atendimento> lista = getDao().findByAtendimento();
        listaAtendimentos = new ListDataModel(lista);
        return listaAtendimentos;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public void prepararAdicionarAtendimento() {//ADD ATENDIMENTOS
        atendimento = new Atendimento();
        atendimento.setDataAtendimento(new Date());
    }

    public void prepararAlterarAtendimento(ActionEvent e) {//ALTERAR ATENDIMENTOS
        setAtendimento((Atendimento) e.getComponent().getAttributes().get("atendimento"));

    }

    public void excluirAtendimento() {



        try {
            dao.remove(atendimento);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }



    }

    public void salvarAtendimento() {

        usuario = new Usuario();//PEGA O USUARIO QUE ESTA LOGADO
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                usuario.setLogin(((User) authentication.getPrincipal()).getUsername());
                atendimento.setUsuario(usuario.getLogin());
            }
        }


        atendimento.setHoraServico(String.valueOf(gethoraServico()));


        dao.save(atendimento);
        //lança mensagem de sucesso
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "ATENDIMENTO CADASTRADO/ALTERADO COM SUCESSO", "ATENDIMENTO CADASTRADO/ALTERADO COM SUCESSO"));
    }

    public List<SelectItem> getPessoas() {//LISTA DE PESSOAS
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Pessoa object : pessoadao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<SelectItem> getStatusAtdm() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();

        toReturn.add(new SelectItem("", "Selecione"));
        toReturn.add(new SelectItem("Ativo", "Ativo"));
        toReturn.add(new SelectItem("Fechada", "Fechada"));
        toReturn.add(new SelectItem("Urgente", "Urgente"));
        toReturn.add(new SelectItem("Aberta", "Aberta"));

        return toReturn;
    }

    public List<SelectItem> getFuncionarios() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Funcionario object : funcionariodao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<SelectItem> getFuncionarios2() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        toReturn.add(new SelectItem("", "Selecione"));
        for (Funcionario object : funcionariodao.findAll()) {
            toReturn.add(new SelectItem(object.getNome(), object.getNome()));
        }
        return toReturn;
    }

    

    public String gethoraServico() {//PEGA A HORA ATUAU

        // cria um StringBuilder
        StringBuilder sb = new StringBuilder();

        // cria um GregorianCalendar que vai conter a hora atual
        GregorianCalendar d = new GregorianCalendar();

        // anexa do StringBuilder os dados da hora
        sb.append(d.get(GregorianCalendar.HOUR_OF_DAY));
        sb.append(":");
        sb.append(d.get(GregorianCalendar.MINUTE));


        // retorna a String do StringBuilder
        return sb.toString();
    }

    public Boolean getTrava() {
        return trava;
    }

    public void setTrava(Boolean trava) {
        this.trava = trava;
    }

    public void travaCampos() {//TRAVA CAMPOS
        trava = false;
        if (atendimento.getStatus().equals("Fechada")) {
            trava = true;

        } else {
            trava = false;

        }




    }

    public List<Pessoa> completaPessoa(String parte) {//ALTO COMPLETE
        return pessoadao.listaFiltrando(parte, "nome");
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Atendimentos");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
    }


//------------------------------------------------Relatórios-----------------------------------------------------------------
      public void imprimeRelatorioDataeFuncionario() throws IOException, SQLException {
        if (filtro == null) {
            if (dtinicio == null || dtfim == null) {
                System.out.println("------------------------------relatorio sem data");
                ArrayList<Atendimento> lista = new ArrayList<Atendimento>();
                lista = (ArrayList<Atendimento>) getDao().findAll();
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
                Conexao c = new Conexao();
                HashMap parameters = new HashMap();
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.responseComplete();
                    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/atendimentoData.jasper"), parameters, c.getConnection());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.exportReport();
                    byte[] bytes = baos.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=\"atendimentoData.pdf\"");
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
                ArrayList<Atendimento> lista = new ArrayList<Atendimento>();
                lista = (ArrayList<Atendimento>) getDao().findAll();
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
                Conexao c = new Conexao();
                HashMap parameters = new HashMap();
                parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
                parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.responseComplete();
                    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/atendimentoData.jasper"), parameters, c.getConnection());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.exportReport();
                    byte[] bytes = baos.toByteArray();
                   if (bytes != null && bytes.length > 0) {
                       HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=\"atendimentoData.pdf\"");
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
                ArrayList<Atendimento> lista = new ArrayList<Atendimento>();
                lista = (ArrayList<Atendimento>) getDao().findAll();
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
                Conexao c = new Conexao();
                HashMap parameters = new HashMap();
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.responseComplete();
                    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/atendimentoData.jasper"), parameters, c.getConnection());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.exportReport();
                    byte[] bytes = baos.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=\"atendimentoData.pdf\"");
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
                ArrayList<Atendimento> lista = new ArrayList<Atendimento>();
                lista = (ArrayList<Atendimento>) getDao().findAll();
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
                Conexao c = new Conexao();
                HashMap parameters = new HashMap();
                parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
                parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
                parameters.put("pFuncionario", "%" + filtro + "%");
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.responseComplete();
                    ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/atendimentoData.jasper"), parameters, c.getConnection());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.exportReport();
                    byte[] bytes = baos.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=\"atendimentoData.pdf\"");
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
        filtro = null;
        dtinicio=null;
        dtfim=null;

    }

    public void imprimeRelatorioClienteStatus() throws IOException, SQLException {
        ArrayList<Atendimento> lista = new ArrayList<Atendimento>();
        lista = (ArrayList<Atendimento>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pCliente", "%" + filtro + "%");
        parameters.put("pStatus", "%" + status + "%");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/atendimentoCliente.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"atendimentoCliente.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        status=null;
        filtro=null;
    }

        public void imprimeRelatorioFuncionarioStatus() throws IOException, SQLException {
        ArrayList<Atendimento> lista = new ArrayList<Atendimento>();
        lista = (ArrayList<Atendimento>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pFuncionario", "%" + filtro + "%");
        parameters.put("pStatus", "%" + status + "%");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/atendimentoFuncionarioStatus.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"atendimentoFuncionarioStatus.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        filtro=null;
        status=null;
    }
}
