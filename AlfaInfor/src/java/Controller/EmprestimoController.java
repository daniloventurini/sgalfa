/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Converter.ConverterGenerico1;
import Dao.EmprestimoFacade;
import Dao.EquipamentoFacade;
import Dao.FuncionarioFacade;
import Dao.PessoaFacade;
import Model.Emprestimo;
import Model.Equipamento;
import Model.Funcionario;
import Model.Pessoa;
import Model.PessoaFisica;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class EmprestimoController implements Serializable {

    private Emprestimo emprestimo;
    private DataModel listaEmprestimos;
    private Equipamento equipamento;
    @EJB
    private EmprestimoFacade dao;
    @EJB
    private PessoaFacade pessoadao;
    @EJB
    private FuncionarioFacade funcionariodao;
    @EJB
    private EquipamentoFacade equipamentodao;
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

    public EquipamentoFacade getEquipamentodao() {
        return equipamentodao;
    }

    public void setEquipamentodao(EquipamentoFacade equipamentodao) {
        this.equipamentodao = equipamentodao;
    }

    public FuncionarioFacade getFuncionariodao() {
        return funcionariodao;
    }

    public void setFuncionariodao(FuncionarioFacade funcionariodao) {
        this.funcionariodao = funcionariodao;
    }
    private Converter pessoaConverter;
    private Converter funcionarioConverter;
    private Converter equipamentoConverter;

    public Converter getEquipamentoConverter() {
        if (equipamentoConverter == null) {
            equipamentoConverter = new ConverterGenerico(equipamentodao);
        }
        return equipamentoConverter;
    }

    public void setEquipamentoConverter(Converter equipamentoConverter) {
        this.equipamentoConverter = equipamentoConverter;
    }

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

    public PessoaFacade getPessoadao() {
        return pessoadao;
    }

    public void setPessoadao(PessoaFacade pessoadao) {
        this.pessoadao = pessoadao;
    }

    public EmprestimoFacade getDao() {
        return dao;
    }

    public void setDao(EmprestimoFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarEmprestimos() {
        List<Emprestimo> lista = getDao().findAll();
        listaEmprestimos = new ListDataModel(lista);
        return listaEmprestimos;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public void prepararAdicionarEmprestimo() {
        emprestimo = new Emprestimo();
        emprestimo.setDataempre(new Date());

    }

    public void prepararAlterarEmprestimo(ActionEvent e) {
        setEmprestimo((Emprestimo) e.getComponent().getAttributes().get("emprestimo"));

    }

    public void excluirEmprestimo() {



        try {
            dao.remove(emprestimo);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }



    }

    public void salvarEmprestimo() {
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                usuario.setLogin(((User) authentication.getPrincipal()).getUsername());
                emprestimo.setUsuario(usuario.getLogin());
            }
        }




        if (emprestimo.getStatus().equals("Ativo")) {
            if (emprestimo.getEquipamento().getStatus().equals("Liberado")) {

                try {
                    dao.save(emprestimo);
                    equipamento = emprestimo.getEquipamento();
                    equipamento.setStatus("Emprestado");
                    equipamentodao.save(equipamento);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("listaEmprestimo.jsf");
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EMPRÉSTIMO CADASTRADO/ALTERADO COM SUCESSO", "EMPRÉSTIMO CADASTRADO/ALTERADO COM SUCESSO"));
                } catch (IOException ex) {
                    Logger.getLogger(EmprestimoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("estado", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Equipamento já emprestado", "Equipamento já emprestado"));
            }
        } else if (emprestimo.getStatus().equals("Finalizado")) {

            try {

                dao.save(emprestimo);
                equipamento = emprestimo.getEquipamento();
                equipamento.setStatus("Liberado");
                equipamentodao.save(equipamento);
                FacesContext.getCurrentInstance().getExternalContext().redirect("listaEmprestimo.jsf");
            } catch (IOException ex) {
                Logger.getLogger(EmprestimoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }






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

    public List<SelectItem> getEquipamentos() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Equipamento object : equipamentodao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<Pessoa> completaPessoa(String parte) {//alto Complete
        return pessoadao.listaFiltrando(parte, "nome");
    }

    //------------------------------RELATÓRIO-------------------------------------------------------------------------
    public void imprimeRelatorioData() throws IOException, SQLException {
        ArrayList<Emprestimo> lista = new ArrayList<Emprestimo>();
        lista = (ArrayList<Emprestimo>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
        parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/emprestimoData.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"emprestimoData.pdf\"");
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
        ArrayList<Emprestimo> lista = new ArrayList<Emprestimo>();
        lista = (ArrayList<Emprestimo>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pCliente", "%" + filtro + "%");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/emprestimoCliente.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"emprestimoCliente.pdf\"");
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
        ArrayList<Emprestimo> lista = new ArrayList<Emprestimo>();
        lista = (ArrayList<Emprestimo>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/emprestimoTotal.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"emprestimoTotal.pdf\"");
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
