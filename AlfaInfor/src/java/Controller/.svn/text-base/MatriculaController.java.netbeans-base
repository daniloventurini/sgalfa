/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Converter.ConverterGenerico1;
import Dao.AlunoFacade;
import Dao.CursoFacade;
import Dao.CursosMatriculadosFacade;
import Dao.FuncionarioFacade;
import Dao.MatriculaFacade;
import Model.Aluno;
import Model.Curso;
import Model.CursosMatriculados;
import Model.Funcionario;
import Model.Laudo;
import Model.Pessoa;
import Model.Matricula;
import Utils.Conexao;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
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
 *
 */
@ManagedBean
@SessionScoped
public class MatriculaController implements Serializable {

    private Matricula matricula;
    private CursosMatriculados cursosmatriculados;
    private DataModel listaMatriculas;
    private DataModel listaCursos;
    @EJB
    private MatriculaFacade dao;
    @EJB
    private AlunoFacade daoAluno;
    @EJB
    private CursosMatriculadosFacade cursosdao;
    @EJB
    private CursoFacade cursodao;
    @EJB
    private FuncionarioFacade funcionariodao;
    private Date dtinicio;
    private Date dtfim;
    private String filtro;
    private String dia;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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

    public FuncionarioFacade getFuncionariodao() {
        return funcionariodao;
    }

    public void setFuncionariodao(FuncionarioFacade funcionariodao) {
        this.funcionariodao = funcionariodao;
    }

    public AlunoFacade getDaoAluno() {
        return daoAluno;
    }

    public void setDaoAluno(AlunoFacade daoAluno) {
        this.daoAluno = daoAluno;
    }

    public CursoFacade getCursodao() {
        return cursodao;
    }

    public void setCursodao(CursoFacade cursodao) {
        this.cursodao = cursodao;
    }

    public CursosMatriculadosFacade getCursosdao() {
        return cursosdao;
    }

    public void setCursosdao(CursosMatriculadosFacade cursosdao) {
        this.cursosdao = cursosdao;
    }

    public CursosMatriculados getCursosmatriculados() {
        return cursosmatriculados;
    }

    public void setCursosmatriculados(CursosMatriculados cursosmatriculados) {
        this.cursosmatriculados = cursosmatriculados;
    }
    private Converter alunoConverter;
    private Converter cursoConverter;
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

    public Converter getCursoConverter() {
        if (cursoConverter == null) {
            cursoConverter = new ConverterGenerico(cursodao);
        }
        return cursoConverter;
    }

    public void setCursoConverter(Converter cursoConverter) {
        this.cursoConverter = cursoConverter;
    }

    public Converter getAlunoConverter() {
        if (alunoConverter == null) {
            alunoConverter = new ConverterGenerico1(daoAluno);
        }
        return alunoConverter;
    }

    public void setAlunoConverter(Converter alunoConverter) {
        this.alunoConverter = alunoConverter;
    }

    public MatriculaFacade getDao() {
        return dao;
    }

    public void setDao(MatriculaFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarMatriculas() {
        List<Matricula> lista = getDao().findAll();
        listaMatriculas = new ListDataModel(lista);
        return listaMatriculas;
    }

    public DataModel getListarCursos() {

        List<CursosMatriculados> lista = (List<CursosMatriculados>) getMatricula().getCursosMatriculados();
        listaCursos = new ListDataModel(lista);
        return listaCursos;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public void prepararAdicionar() {
        matricula = new Matricula();
        cursosmatriculados = new CursosMatriculados();
        matricula.setDataMatricula(new Date());
        matricula.setStatus("Ativo");

    }

    public void alterar(ActionEvent actionEvent) {

        Matricula v = (Matricula) actionEvent.getComponent().getAttributes().get("matricula");
        matricula = dao.find(v.getId());
        cursosmatriculados = new CursosMatriculados();

    }

    public void excluir() {
        try {
            dao.remove(matricula);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }
    }

    public void salvar() {

        dao.save(matricula);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "MATRÍCULA CADASTRADO/ALTERADO COM SUCESSO", "MATRÍCULA CADASTRADO/ALTERADO COM SUCESSO"));

    }

    public static Date addDias(Date date, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dias);

        return calendar.getTime();

    }

    public void addItens() {
        cursosmatriculados.setMatricula(matricula);

        matricula.getCursosMatriculados().add(cursosmatriculados);

        cursosmatriculados = new CursosMatriculados();
    }

    public void selecionaCurso(ActionEvent e) {
        setCursosmatriculados((CursosMatriculados) e.getComponent().getAttributes().get("cursosmatriculados"));




    }

    public void excluirItens() {
        matricula.getCursosMatriculados().remove(cursosmatriculados);




    }

    public List<SelectItem> getAlunos() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Pessoa object : daoAluno.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<SelectItem> getCursos() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Curso object : cursodao.findAll()) {
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

    public List<Aluno> completaPessoa(String parte) {
        return daoAluno.listaFiltrando(parte, "nome");
    }

    //-----------------------------------------RELATÓRIO----------------------------------------------------
    public void imprimeRelatorioData() throws IOException, SQLException {
        ArrayList<Matricula> lista = new ArrayList<Matricula>();
        lista = (ArrayList<Matricula>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
        parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/matriculaData.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"matriculaData.pdf\"");
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
        ArrayList<Matricula> lista = new ArrayList<Matricula>();
        lista = (ArrayList<Matricula>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pCliente", "%" + filtro + "%");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/matriculaAluno.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"matriculaAluno.pdf\"");
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
        ArrayList<Matricula> lista = new ArrayList<Matricula>();
        lista = (ArrayList<Matricula>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/matriculaTotal.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"matriculaTotal.pdf\"");
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

    public void imprimeRelatorioHorario() throws IOException, SQLException {
        ArrayList<Matricula> lista = new ArrayList<Matricula>();
        lista = (ArrayList<Matricula>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pDia", "%" + dia + "%");
        parameters.put("pHora", "%" + filtro + "%");

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/matriculahorario.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"matriculaAluno.pdf\"");
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
