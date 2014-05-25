/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Dao.GrupoFacade;
import Dao.ProdutoFacade;
import Model.Produto;
import Model.Grupo;
import Utils.Conexao;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 *
 */
@ManagedBean
@SessionScoped
public class ProdutoController implements Serializable {

    private Produto produto;
    private DataModel listaProdutos;
    @EJB
    private ProdutoFacade dao;
    private Grupo grupo;
    @EJB
    private GrupoFacade grupodao;
    private StreamedContent imagem;
    private byte[] foto;
    private String nomeImagem;
    private Converter grupoconverter;

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Converter getGrupoconverter() {
        if (grupoconverter == null) {
            grupoconverter = new ConverterGenerico(grupodao);
        }
        return grupoconverter;
    }

    public void setGrupoconverter(Converter grupoconverter) {
        this.grupoconverter = grupoconverter;
    }

    public GrupoFacade getGrupodao() {
        return grupodao;
    }

    public void setGrupodao(GrupoFacade grupodao) {
        this.grupodao = grupodao;
    }

    public DataModel getListarProdutos() {
        List<Produto> lista = getDao().findAll();
        listaProdutos = new ListDataModel(lista);
        return listaProdutos;
    }

    public ProdutoFacade getDao() {
        return dao;
    }

    public void setDao(ProdutoFacade dao) {
        this.dao = dao;
    }

    public StreamedContent getImagem() {

        return imagem;

    }

    public void setImagem(StreamedContent imagem) {
        this.imagem = imagem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void prepararAdicionarProduto(ActionEvent actionEvent) {
        produto = new Produto();
        produto.setDataCadastro(new Date());


    }

    public void prepararAlterarProduto(ActionEvent actionEvent) {

        setProduto((Produto) actionEvent.getComponent().getAttributes().get("produto"));
        montaImagem();
    }

    public void excluirProduto() {

        try {
            dao.remove(produto);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "EXCLUÍDO COM SUCESSO", "EXCLUÍDO COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui Dependências"));
        }
    }

    public void salvarProduto(ActionEvent actionEvent) {

        try {
            if (produto.getQnt() > 0) {
                nomeiaImagem();
                getDao().save2(produto);
                FacesContext.getCurrentInstance().getExternalContext().redirect("listaProduto.jsf");
                RequestContext requestContext = RequestContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "PRODUTO CADASTRADO/ALTERADO COM SUCESSO", "PRODUTO CADASTRADO/ALTERADO COM SUCESSO"));
            } else {
                FacesContext.getCurrentInstance().addMessage("form:qnt", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "A Quantidade de Produtos deve ser Maior que 0"));
            }
        } catch (Exception e) {
            System.out.println("erro tatata");
        }


    }

    public List<SelectItem> getGrupos() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Grupo object : getGrupodao().findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));
        }
        return toReturn;
    }

    public void handleFileUpload(FileUploadEvent event) {

        try {
            foto = event.getFile().getContents();
            System.out.println(event.getFile().getFileName());
            setNomeImagem(event.getFile().getFileName());
            imagem = new DefaultStreamedContent(event.getFile().getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void montaImagem() {

        String arquivo = "D:/COPIA DE SEGURANÇA/COPIA64/AlfaInfor/web/imagem/" + produto.getImagem();
//                System.out.println("------------------------------" + nomeImagem);
        File fos = new File(arquivo);
        try {
            imagem = new DefaultStreamedContent(new FileInputStream(fos));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void criaArquivo(byte[] bytes, String arquivo) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(arquivo);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nomeiaImagem() {
        String arquivo = "D:/COPIA DE SEGURANÇA/COPIA64/AlfaInfor/web/imagem/" + getNomeImagem();
        produto.setImagem(nomeImagem);
        criaArquivo(foto, arquivo);
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Produtos");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
    }

    public void imprimeRelatorio() throws IOException, SQLException {


        ArrayList<Produto> lista = new ArrayList<Produto>();
        lista = (ArrayList<Produto>) getDao().findAll();

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();

            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/produto.jasper"), parameters, c.getConnection());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType("application/pdf");

                response.setHeader("Content-disposition", "inline; filename=\"produto.pdf\"");

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

    public void imprimeProdutosemFalta() throws IOException, SQLException {


        ArrayList<Produto> lista = new ArrayList<Produto>();
        lista = (ArrayList<Produto>) getDao().findAll();

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();

            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/produtoEmFalta.jasper"), parameters, c.getConnection());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType("application/pdf");

                response.setHeader("Content-disposition", "inline; filename=\"produtoEmFalta.pdf\"");

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

//    public void imprimeRelatorioCodigo() throws IOException, SQLException {
//
//
//        ArrayList<Produto> lista = new ArrayList<Produto>();
//        lista = (ArrayList<Produto>) getDao().findAll();
//
//        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
//        Conexao c = new Conexao();
//        HashMap parameters = new HashMap();
//
//        try {
//
//            FacesContext facesContext = FacesContext.getCurrentInstance();
//
//            facesContext.responseComplete();
//
//            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/produtoCodigo.jasper"), parameters, c.getConnection());
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            JRPdfExporter exporter = new JRPdfExporter();
//
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//
//            exporter.exportReport();
//
//            byte[] bytes = baos.toByteArray();
//
//            if (bytes != null && bytes.length > 0) {
//
//                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//
//                response.setContentType("application/pdf");
//
//                response.setHeader("Content-disposition", "inline; filename=\"produtoCodigo.pdf\"");
//
//                response.setContentLength(bytes.length);
//
//                ServletOutputStream outputStream = response.getOutputStream();
//
//                outputStream.write(bytes, 0, bytes.length);
//
//                outputStream.flush();
//
//                outputStream.close();
//
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
}
