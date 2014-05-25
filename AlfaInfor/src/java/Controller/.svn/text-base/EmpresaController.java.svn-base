/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Dao.CidadeFacade;
import Dao.EmpresaFacade;
import Model.Cidade;
import Model.Grupo;
import Model.Empresa;
import Model.Pessoa;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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

/**
 *
 * @author José Alexandre
 */
@ManagedBean
@SessionScoped
public class EmpresaController implements Serializable {

    private Empresa empresa;
    private DataModel listaEmpresas;
    @EJB
    private EmpresaFacade dao;
    @EJB
    private CidadeFacade cidadedao;
    private Converter converterCidade;

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

    public EmpresaFacade getDao() {
        return dao;
    }

    public void setDao(EmpresaFacade dao) {
        this.dao = dao;
    }
    private Pessoa pessoa;
    private Grupo grupo;
    private Cidade cidade;

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public DataModel getListaEmpresas() {
        return listaEmpresas;
    }

    public void setListaEmpresas(DataModel listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public DataModel getListarEmpresas() {

        List<Empresa> lista = getDao().findAll();
        listaEmpresas = new ListDataModel(lista);
        return listaEmpresas;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void prepararAdicionarEmpresa() {
        empresa = new Empresa();

    }

    public void prepararAlterarEmpresa(ActionEvent e) {
        List<Empresa> es = dao.findAll();
        Empresa p = es.get(es.size()-1);
        empresa = dao.find(p.getId());


    }

    public void excluirEmpresa() {



        try {
            dao.remove(empresa);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possui dependências"));
        }



    }

    public void salvarEmpresa() {

        try {
            dao.save2(empresa);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/AlfaInfor/index.jsf");

        } catch (Exception e) {
            if (e.getCause().getMessage().contains("cnpj")) {
                FacesContext.getCurrentInstance().addMessage("cnpj", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro", "CNPJ já Cadastrado. Tente Novamente!"));
                //lança exception cpf
            }
            //lança outra exception qualquer
            FacesContext.getCurrentInstance().addMessage("cnpj", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro", "CNPJ já Cadastrado. Tente Novamente!"));

        }


    }

    public List<SelectItem> getCidades() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();
        for (Cidade object : (List<Cidade>) cidadedao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNomeCidade()));
        }
        return toReturn;
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "css" + File.separator + "imagens" + File.separator + "dados.png";

        pdf.add(Image.getInstance(logo));
        Paragraph p = new Paragraph("Relatório de Empresa");
        p.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        pdf.add(p);
    }
}
