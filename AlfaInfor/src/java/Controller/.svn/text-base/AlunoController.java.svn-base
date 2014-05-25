/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Dao.CidadeFacade;
import Dao.AlunoFacade;
import Model.Cidade;
import Model.Aluno;
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
import org.primefaces.event.DateSelectEvent;


@ManagedBean//indica que aki eh um controlador
@SessionScoped//faz com que as visões possão enchergar esse controlador
public class AlunoController implements Serializable {

    private Aluno aluno;
    private DataModel listaAlunos;//lista de aluno
    private DataModel listaTelefones;//lista de telefone
    private String estado;
    @EJB
    private AlunoFacade dao;//alunoFacade
    @EJB
    private CidadeFacade cidadedao;//cidade Facade
    private Telefone telefone;
    private Converter converterCidade;
    private Boolean trava = true;

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

    public AlunoFacade getDao() {
        return dao;
    }

    public void setDao(AlunoFacade dao) {
        this.dao = dao;
    }

    public Boolean getTrava() {
        return trava;
    }

    public void setTrava(Boolean trava) {
        this.trava = trava;
    }

    public DataModel getListarAlunos() {//cria uma lista de alunos
        List<Aluno> lista = getDao().findAll();
        listaAlunos = new ListDataModel(lista);
        return listaAlunos;
    }

    public DataModel getListarTelefones() {//cria uma lista de telefones
        List<Telefone> telefones = getAluno().getTelefone();
        listaTelefones = new ListDataModel(telefones);
        return listaTelefones;

    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void prepararAdicionarAluno() {//ADD ALUNO
        aluno = new Aluno();//INSTÂNCIA O ALUNO
        aluno.setDataCadastro(new Date());//ATRIBUI A DATA A DATA DO DIA CORRENTE
        telefone = new Telefone();//INSTÂNCIA O TELEFONE
        estado = null;


    }

    public void prepararAlterarAluno(ActionEvent e) {//ALTERAR ALUNO
        Aluno p = (Aluno) (e.getComponent().getAttributes().get("aluno"));//RECEBE O ATRIBUTO QUE VEM DA TELA
        aluno = dao.find(p.getId());//alguem esta sobrescrevendo o método no facade
        telefone = new Telefone();
        travaCampos();
        estado = aluno.getCidade().getEstado();//PEGA A O ESTADO DAQUELE ALUNO
    }

    public void excluirAluno() {//EXCLUIR ALUNO



        try {
            dao.remove(aluno);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {//MENSAGEM SE TIVER ALGUMA DEPENDÊNCIA
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }


    }

    public void salvarAluno() {//SALVAR ALUNO
        if (aluno.getIdade() <= 14 && (aluno.getNomeRespo() == null || aluno.getNomeRespo().equals(""))) {
            FacesContext.getCurrentInstance().addMessage("idade", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Para menores de 14 anos é obrigatório os dados do Responsável", "Para menores de 14 anos é obrigatório os dados do Responsável"));

        } else {
            try {
                dao.save2(aluno);
                FacesContext.getCurrentInstance().getExternalContext().redirect("listaAluno.jsf");//REDIRECIONA PARA O LISTA ALUNO
                RequestContext requestContext = RequestContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "ALUNO CADASTRADO/ALTERADO COM SUCESSO", "ALUNO CADASTRADO/ALTERADO COM SUCESSO"));
                System.out.println("teste mensagem");
            } catch (Exception e) {//MENSAGEM SE O CPF É REPETIDO
                if (e.getCause().getMessage().contains("cpfalu")) {
                    FacesContext.getCurrentInstance().addMessage("cpfalu", new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF já Cadastrado. Tente Novamente!", "CPF já Cadastrado. Tente Novamente!"));
                    //lança exception cpf
                }
                //lança outra exception qualquer
                FacesContext.getCurrentInstance().addMessage("cpfalu", new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF já Cadastrado!", "CPF já Cadastrado!"));
            }

        }
    }



    public List<SelectItem> getCidades() {//LISTA DE CIDADE
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Cidade object : cidadedao.findByEstado(estado)) {//NO FACADE TEM UM METODO FIND BY ESTADO
            toReturn.add(new SelectItem(object, object.getNomeCidade()));
        }
        return toReturn;
    }

    public List<SelectItem> getTiposTelefones() {//LISTA OS TIPOS DE TELEFONE
        List<SelectItem> lista = new LinkedList<SelectItem>();
        for (TipoTelefone tt : TipoTelefone.values()) {
            lista.add(new SelectItem(tt, tt.toString()));
        }
        return lista;
    }

    public void addTelefone() {//ADD O TELEFONE
        aluno.getTelefone().add(telefone);
//        System.out.println("--------------------------" + telefone.getNumero());
        telefone = new Telefone();
    }

    public void selecionaTelefone(ActionEvent actionEvent) {//ALTERA O TELEFONE
        telefone = (Telefone) (actionEvent.getComponent().getAttributes().get("telefone"));

    }

    public void removeTelefone() {//REMOVE O TELEFONE
        aluno.getTelefone().remove(telefone);
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {//PDF PRÉ FORMATADO COM DATAEXPORTER
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Alunos");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
    }

    public void travaCampos() {//TRAVA CAMPOS
        trava = true;
        if (aluno.getIdade() <= 14) {
            trava = false;//mostra
        } else {

            trava = true;//não mostra
        }



    }

        public void setaIdade(DateSelectEvent e) {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        Calendar c = Calendar.getInstance();
        c.setTime(e.getDate());
        int anoNasc = c.get(Calendar.YEAR);
        aluno.setIdade(anoAtual - anoNasc);
    }

    public void imprimeRelatorio() throws IOException, SQLException {


        ArrayList<Aluno> lista = new ArrayList<Aluno>();
        lista = (ArrayList<Aluno>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/aluno.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"aluno.pdf\"");
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
