/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico1;
import Dao.FuncionarioFacade;
import Dao.UsuarioFacade;
import Model.Funcionario;
import Model.Usuario;
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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import sun.misc.BASE64Encoder;

@ManagedBean
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario usuario;
    private DataModel listaUsuarios;
    private String senhaCri;
    @EJB
    private UsuarioFacade dao;
    @EJB
    private FuncionarioFacade funcionariodao;

    public String getSenhaCri() {
        return senhaCri;
    }

    public void setSenhaCri(String senhaCri) {
        this.senhaCri = senhaCri;
    }

    public FuncionarioFacade getFuncionariodao() {
        return funcionariodao;
    }

    public void setFuncionariodao(FuncionarioFacade funcionariodao) {
        this.funcionariodao = funcionariodao;
    }
    private Converter converterFuncionario;

    public Converter getConverterFuncionario() {
        if (converterFuncionario == null) {
            converterFuncionario = new ConverterGenerico1(funcionariodao);
        }
        return converterFuncionario;
    }

    public void setConverterFuncionario(Converter converterFuncionario) {
        this.converterFuncionario = converterFuncionario;
    }

    public UsuarioFacade getDao() {
        return dao;
    }

    public void setDao(UsuarioFacade dao) {
        this.dao = dao;
    }

    public DataModel getListarUsuarios() {
        List<Usuario> lista = getDao().findAll();
        listaUsuarios = new ListDataModel(lista);
        return listaUsuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void prepararAdicionarUsuario() {
        usuario = new Usuario();

    }

    public void prepararAlterarUsuario(ActionEvent e) {
        setUsuario((Usuario) e.getComponent().getAttributes().get("usuario"));

    }

    public void excluirUsuario() {



        try {
            dao.remove(usuario);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }


    }

    public void salvarUsuario() {
        try {
            senhaCri = criptografaSenha(usuario.getSenha());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        usuario.setSenha(senhaCri);
        dao.save(usuario);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaUsuario.jsf");
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "USUÁRIO CADASTRADO/ALTERADO COM SUCESSO", "USUÁRIO CADASTRADO/ALTERADO COM SUCESSO"));

        } catch (IOException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                usuario.setLogin(((User) authentication.getPrincipal()).getUsername());
            }
        }

    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Usuários");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
    }

    public UsuarioController() {//pega o usuario logado
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                usuario.setLogin(((User) authentication.getPrincipal()).getUsername());
            }
        }
    }

    public List<SelectItem> getFuncionarios() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Funcionario object : funcionariodao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public List<Funcionario> completaFuncionario(String parte) {//autoComplete
        return funcionariodao.listaFiltrando(parte, "nome");
    }

    public void imprimeRelatorio() throws IOException, SQLException {


        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        lista = (ArrayList<Usuario>) getDao().findAll();

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();

            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/usuario.jasper"), parameters, c.getConnection());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType("application/pdf");

                response.setHeader("Content-disposition", "inline; filename=\"usuario.pdf\"");

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
    //-------------------------------BACKUP---------------------------------------------------------------
    private static String VERSION = "4.0.3";
    private static String SEPARATOR = File.separator;
    private static String MYSQL_PATH = "C:" + SEPARATOR + "backup" + SEPARATOR + "bin" + SEPARATOR;
    private static String PRESENTATION =
            "=====================================================\n"
            + "Backup do Banco de Dados superjsf - Versao" + VERSION + "\n"
            + "Autor: Danilo Venturini em 24/08/2011";
    private static String DATABASES = "superjsf";
    private List<String> dbList = new ArrayList<String>();

    public void Backup() {
        String command = MYSQL_PATH + "mysqldump.exe";
        String[] databases = DATABASES.split("");

        for (int i = 0; i < databases.length; i++) {
            dbList.add(databases[i]);
        }

        System.out.println("Iniciando Backups...\n\n");

        int i = 1;

        long time1, time2, time;

        time1 = System.currentTimeMillis();

        for (String dbName : dbList) {
            ProcessBuilder pb = new ProcessBuilder(
                    command, "--user=root", "--password=root", "superjsf", "--result-file=" + "C:" + SEPARATOR + "backup" + SEPARATOR + "superjsf" + ".sql");
      
            try {
                System.out.println("Backup do banco de dados(" + i + "):" + dbName + "...");

                pb.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

            i++;
        }

        time2 = System.currentTimeMillis();

        time = time2 - time1;
        //--------------------------------------------Mensagem-------------------------------------------------------
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("backupmessage:backup", new FacesMessage(FacesMessage.SEVERITY_INFO, "BACKUP REALIZADO COM SUCESSO", "BACKUP REALIZADO COM SUCESSO"));
    }
//-----------------------------------------------------------Criptografia de Senha-----------------------------------------------

    public String criptografaSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        String s = hash.toString(16);
        if (s.length() % 2 != 0) {
            s = "0" + s;
        }
        return s;
    }
}











