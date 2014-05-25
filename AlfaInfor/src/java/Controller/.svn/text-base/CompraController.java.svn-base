/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Converter.ConverterGenerico;
import Converter.ConverterGenerico1;
import Dao.FuncionarioFacade;
import Dao.ItensCompraFacade;
import Dao.PessoaFacade;
import Dao.ProdutoFacade;
import Dao.CompraFacade;
import Dao.ContasPagarFacade;
import Dao.ContasReceberFacade;
import Model.Cheque;
import Model.Funcionario;
import Model.ItensCompra;
import Model.Pessoa;
import Model.Produto;
import Model.Usuario;
import Model.Compra;
import Model.ContasPagar;
import Model.ContasReceber;
import Utils.Conexao;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
 *
 */
@ManagedBean
@SessionScoped
public class CompraController implements Serializable {

    private Compra compra;
    private ItensCompra itensCompra;
    private Produto produto;
    private Pessoa pessoa;
    private Funcionario funcionario;
    private DataModel listaCompras;
    private DataModel listaItens;
    private DataModel listaContas;
    private DataModel listarCheques;
    private Boolean travaCompra;
    private Cheque cheque;
    @EJB
    private CompraFacade dao;
    @EJB
    private ItensCompraFacade intensdao;
    @EJB
    private ProdutoFacade produtodao;
    @EJB
    private ContasPagarFacade contasdao;
    @EJB
    private FuncionarioFacade funcionariodao;
    @EJB
    private PessoaFacade pessoaadao;
    @EJB
    private ContasReceberFacade contasReceberDao;
    private BigDecimal desconto;
    private BigDecimal entrada;
    private Usuario usuario;
    private Date dtinicio;
    private Date dtfim;
    private ContasPagar contaCheque;
    int qntContasPagar, qntContasReceber, qntProdutos;
    private String filtro;
    private String filtrofun;
    

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getFiltrofun() {
        return filtrofun;
    }

    public void setFiltrofun(String filtrofun) {
        this.filtrofun = filtrofun;
    }

    public BigDecimal getEntrada() {
        return entrada;
    }

    public void setEntrada(BigDecimal entrada) {
        this.entrada = entrada;
    }

    public ContasPagar getContaCheque() {
        return contaCheque;
    }

    public void setContaCheque(ContasPagar contaCheque) {
        this.contaCheque = contaCheque;
    }

    public ContasReceberFacade getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberFacade contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public PessoaFacade getPessoaadao() {
        return pessoaadao;
    }

    public void setPessoaadao(PessoaFacade pessoaadao) {
        this.pessoaadao = pessoaadao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Cheque> getListarCheques() {
        return contaCheque.getCheques();

    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {

        this.desconto = desconto;
    }

    public FuncionarioFacade getFuncionariodao() {
        return funcionariodao;
    }

    public void setFuncionariodao(FuncionarioFacade funcionariodao) {
        this.funcionariodao = funcionariodao;



    }
    private Converter produtoConverter;
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
            pessoaConverter = new ConverterGenerico1(pessoaadao);
        }
        return pessoaConverter;
    }

    public void setPessoaConverter(Converter pessoaConverter) {
        this.pessoaConverter = pessoaConverter;
    }

    public Converter getProdutoConverter() {
        if (produtoConverter == null) {
            produtoConverter = new ConverterGenerico(produtodao);
        }
        return produtoConverter;
    }

    public void setProdutoConverter(Converter produtoConverter) {
        this.produtoConverter = produtoConverter;
    }

    public ContasPagarFacade getContasdao() {
        return contasdao;
    }

    public void setContasdao(ContasPagarFacade contasdao) {
        this.contasdao = contasdao;
    }

    public ProdutoFacade getProdutodao() {
        return produtodao;
    }

    public void setProdutodao(ProdutoFacade produtodao) {
        this.produtodao = produtodao;
    }

    public ItensCompraFacade getIntensdao() {
        return intensdao;
    }

    public void setIntensdao(ItensCompraFacade intensdao) {
        this.intensdao = intensdao;
    }

    public CompraFacade getDao() {
        return dao;
    }

    public void setDao(CompraFacade dao) {
        this.dao = dao;
    }
    private ContasPagar contasPagar;
    private String pagamento;

    public ContasPagar getContasPagar() {
        return contasPagar;
    }

    public void setContasPagar(ContasPagar contasPagar) {
        this.contasPagar = contasPagar;
    }

    public DataModel getListarCompras() {
        List<Compra> lista = getDao().findAll();
        listaCompras = new ListDataModel(lista);
        return listaCompras;
    }

    public DataModel getListarContas() {
        List<ContasPagar> lista = getCompra().getContasaPagar();
        listaContas = new ListDataModel(lista);
        return listaContas;
    }

    public DataModel getListarItens() {

        List<ItensCompra> lista = (List<ItensCompra>) getCompra().getItensCompra();
        listaItens = new ListDataModel(lista);
        return listaItens;
    }

    public Boolean getTravaCompra() {
        return travaCompra;
    }

    public void setTravaCompra(Boolean travaCompra) {
        this.travaCompra = travaCompra;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public ItensCompra getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(ItensCompra itensCompra) {
        this.itensCompra = itensCompra;
    }

    public void prepararAdicionar() {
        compra = new Compra();
        itensCompra = new ItensCompra();
        compra.setDesconto(BigDecimal.ZERO);
        compra.setEntrada(BigDecimal.ZERO);
        compra.setStatus("Ativo");
        desconto = new BigDecimal(BigInteger.ZERO);
        compra.setDataCompra(new Date());
        compra.setPagamento("vista");
        compra.setQntParcelas(1);
        contaCheque = new ContasPagar();
        contaCheque.setCheques(new ArrayList<Cheque>());
        cheque = new Cheque();
        contasPagar = new ContasPagar();

    }

    public void alterar(ActionEvent actionEvent) {
        cheque = new Cheque();
        Compra v = (Compra) actionEvent.getComponent().getAttributes().get("compra");
        compra = dao.find(v.getId());
        Pessoa p = compra.getPessoa();
        travaCampos();
        contaCheque = new ContasPagar();
        contaCheque.setCheques(new ArrayList<Cheque>());
        cheque = new Cheque();
        ContasPagar cr = getContasdao().find(compra.getContasaPagar().get(0).getId());//isso tah estranho
        itensCompra = new ItensCompra();
        pessoa = getPessoaadao().find(p.getId());
        Funcionario f = compra.getFuncionario();
        funcionario = getFuncionariodao().find(f.getId());
        contasPagar = new ContasPagar();



    }

    public void excluir() {
        try {
            dao.remove(compra);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possue Dependências"));
        }
    }

    public void PegaUsuario() {
        //pega o usuário logado
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                usuario.setLogin(((User) authentication.getPrincipal()).getUsername());
                compra.setUsuario(usuario.getLogin());
            }
        }

    }

    public void salvar() {
        //mesagem se a forma de pagamento for igual a cheque e o usuário não tiver adicionado cheque
        if (contaCheque.getCheques().isEmpty() && "Cheque".equals(compra.getPagamento())) {
            FacesContext.getCurrentInstance().addMessage("form:gridItens", new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Antes de Salvar Inclua os Cheques, ou escolha outra forma de pagamento"));
            return;

        } else if (compra.getPagamento().equals("Cheque")) {
            compra.getContasaPagar().set(compra.getContasaPagar().indexOf(contaCheque), contaCheque);//seta a conta que tem os cheques denovo na venda para salvar
            if (getSomaCheque().compareTo(compra.getValorTotal()) < 0) {
                RequestContext requestContext = RequestContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage("form:gridItens", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "O total do(s) Cheque(s) ainda é menor que o total Geral!"));
                return;
            }
        }
        PegaUsuario();
        //---------------------------------Mexi aki-----------------------------------------

        try {
            for (ItensCompra it : compra.getItensCompra()) {
                produto = it.getProduto();
                produto.setQnt(produto.getQnt() + it.getQuantidade());
                produtodao.save(produto);

            }
            dao.save(compra);
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaCompra.jsf");
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "COMPRA CADASTRADA/ALTERADA COM SUCESSO", "COMPRA CADASTRADA/ALTERADA COM SUCESSO"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("form:gridItens",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ops!", "Ocorreu um erro inesperado!"));

        }

    }

    public static Date addDias(Date date, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dias);

        return calendar.getTime();

    }

    public void addItens() {

        produto = itensCompra.getProduto();
        if (itensCompra != null) {
            System.out.print("aki tah");
            if (produto.getQntMinima() < itensCompra.getQuantidade()) {
                System.out.print("ta entrando aki");
                itensCompra.setProduto(produto);
                itensCompra.setCompra(compra);
                itensCompra.setValor(itensCompra.getProduto().getValor());
                itensCompra.setTotal();
                compra.getItensCompra().add(itensCompra);

                itensCompra = new ItensCompra();
                produto = new Produto();

            } else {
                //valida se a quantidade seleciona de produtos esta suficiente no estoque

                FacesContext.getCurrentInstance().addMessage("form:gridItens", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Quantidade comprada não corresponde a quantidade minima desejada: ", "A Quantidade comprada não corresponde a quantidade minima desejada:" + produto.getQntMinima() + " " + "é correto!"));

            }
        } else {
            // valida se a pessoa selecionou algum produto
            FacesContext.getCurrentInstance().addMessage("form:gridItens", new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!", "Selecione um Produto."));
        }

    }

    public void calcular() {
        getDoscontoCompra();
        getEntradaCompra();
        addConta();

    }

    public void selecionaIten(ActionEvent e) {
        setItensCompra((ItensCompra) e.getComponent().getAttributes().get("itens"));
    }

    public void excluirItens(ActionEvent event) {
        try {


            compra.getItensCompra().remove(itensCompra);



        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro na tentativa de exclusão", "O objeto possue Dependências"));
        }


    }

    public BigDecimal getTotalGeral() {
        BigDecimal totalGeral = new BigDecimal(0);
        for (Iterator<ItensCompra> it = compra.getItensCompra().iterator(); it.hasNext();) {
            ItensCompra itens = it.next();
            totalGeral = totalGeral.add(itens.getTotal());

        }
        return totalGeral;


    }

    public void getDoscontoCompra() {
        desconto = getTotalGeral().subtract(compra.getDesconto());
        compra.setValorTotal(desconto);
    }

    public void getEntradaCompra() {
        entrada = compra.getValorTotal().subtract(compra.getEntrada());
        compra.setValorTotal(entrada);
    }

    public void addConta() {
        int parcelar = compra.getQntParcelas();
        BigDecimal valorParcelado = new BigDecimal(BigInteger.ZERO);
        valorParcelado = compra.getValorTotal().divide(new BigDecimal(compra.getQntParcelas().toString()), BigDecimal.ROUND_UP);
        List<ContasPagar> lista = new ArrayList<ContasPagar>();
        Date datavencimento = compra.getDataCompra();
        if (compra.getPagamento().equals("prazo")) {
            for (int i = 1; i <= parcelar; i++) {
                datavencimento = addDias(datavencimento, 30);
                ContasPagar c = new ContasPagar();
                c.setNumeroParcelas(String.valueOf(i) + "/" + String.valueOf(compra.getQntParcelas()));
                c.setDataConta(compra.getDataCompra());
                c.setPessoa(compra.getPessoa());
                c.setDataPagamento(datavencimento);
                c.setValor(valorParcelado);
                c.setStatus("Ativo");
                PegaUsuario();
                c.setUsuario(compra.getUsuario());
                lista.add(c);
            }
            if (valorParcelado.multiply(new BigDecimal(parcelar)) != compra.getValorTotal()) {
                BigDecimal diferenca = valorParcelado.multiply(new BigDecimal(compra.getQntParcelas())).subtract(compra.getValorTotal());
                ContasPagar c = lista.get(lista.size() - 1);
                BigDecimal multiplicado = valorParcelado.multiply(new BigDecimal(parcelar));

                if (multiplicado.compareTo(compra.getValorTotal()) > 0) {
                    c.setValor(c.getValor().add(diferenca));


                } else if (multiplicado.compareTo(compra.getValorTotal()) < 0) {
                    c.setValor(c.getValor().subtract(diferenca));

                }

            }

        }
        if (compra.getPagamento().equals("vista")) {
            ContasPagar c = new ContasPagar();
            c.setNumeroParcelas(" 1/1 ");
            c.setDataConta(compra.getDataCompra());
            c.setPessoa(compra.getPessoa());
            c.setDataPagamento(datavencimento);
            c.setValor(valorParcelado);
            c.setStatus("Liquidado");
            PegaUsuario();
            c.setUsuario(compra.getUsuario());
            lista.add(c);
            compra.setQntParcelas(1);


        }
        if (compra.getPagamento().equals("Cheque")) {
            ContasPagar c = new ContasPagar();
            c.setNumeroParcelas(" 1/1 ");
            c.setDataConta(compra.getDataCompra());
            c.setPessoa(compra.getPessoa());
            c.setDataPagamento(datavencimento);
            c.setValor(valorParcelado);
            c.setStatus("Liquidado/Cheque");
            PegaUsuario();
            c.setUsuario(compra.getUsuario());
            lista.add(c);
            compra.setQntParcelas(1);


        }
        if (compra.getPagamento().equals("Boleto")) {
            for (int i = 1; i <= parcelar; i++) {
                datavencimento = addDias(datavencimento, 30);
                ContasPagar c = new ContasPagar();
                c.setNumeroParcelas(String.valueOf(i) + "/" + String.valueOf(compra.getQntParcelas()));
                c.setDataConta(compra.getDataCompra());
                c.setPessoa(compra.getPessoa());
                c.setDataPagamento(datavencimento);
                c.setValor(valorParcelado);
                c.setStatus("Ativo/Boleto");
                PegaUsuario();
                c.setUsuario(compra.getUsuario());
                lista.add(c);
            }


            if (valorParcelado.multiply(new BigDecimal(parcelar)) != compra.getValorTotal()) {
                BigDecimal diferenca = valorParcelado.multiply(new BigDecimal(compra.getQntParcelas())).subtract(compra.getValorTotal());
                ContasPagar c = lista.get(lista.size() - 1);
                BigDecimal multiplicado = valorParcelado.multiply(new BigDecimal(parcelar));

                if (multiplicado.compareTo(compra.getValorTotal()) > 0) {
                    c.setValor(c.getValor().add(diferenca));


                } else if (multiplicado.compareTo(compra.getValorTotal()) < 0) {
                    c.setValor(c.getValor().subtract(diferenca));

                }

            }
        }
        if (compra.getEntrada().compareTo(new BigDecimal(BigInteger.ZERO)) > 0) {
            ContasPagar c = new ContasPagar();
            c.setNumeroParcelas("Entrada");
            c.setDataConta(compra.getDataCompra());
            c.setPessoa(compra.getPessoa());
            c.setDataPagamento(compra.getDataCompra());
            c.setValor(compra.getEntrada());
            c.setStatus("Liquidado");
            PegaUsuario();
            c.setUsuario(compra.getUsuario());
            lista.add(c);
        }
        compra.setContasaPagar(lista);
    }

    public List<SelectItem> getPessoas() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();


        for (Pessoa object : pessoaadao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNome()));


        }
        return toReturn;


    }

    public List<SelectItem> getProdutos() {
        List<SelectItem> toReturn = new LinkedList<SelectItem>();


        for (Produto object : produtodao.findAll()) {
            toReturn.add(new SelectItem(object, object.getNomeProduto()));


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

    public void travaCampos() {
        travaCompra = false;


        if (compra.getPagamento().equals("vista")) {
            travaCompra = false;
            compra.setQntParcelas(1);



        } else if (compra.getPagamento().equals("prazo")) {
            travaCompra = true;



        } else if (compra.getPagamento().equals("Cheque")) {
            travaCompra = false;
            compra.setQntParcelas(1);



        } else if (compra.getPagamento().equals("Boleto")) {
            travaCompra = true;



        }


    }

    public List<Pessoa> completaPessoa(String parte) {
        return pessoaadao.listaFiltrando(parte, "nome");


    }

    public void adcionaCheque() {
        cheque.setContasPagar(contaCheque);
        contaCheque.getCheques().add(cheque);
        cheque = new Cheque();


    }

    public void setaConta(ActionEvent e) {
        //nesse método seta a conta qndo clica no botão cheque
        ContasPagar c = (ContasPagar) e.getComponent().getAttributes().get("contas");
        System.out.println("-------------------------------------------------");
        if (c.getId() != null) {
            contaCheque = contasdao.find(c.getId());
            System.out.println("ContaCheque1: " + contaCheque);
        } else {
            //quando é uma conta nova ainda não tem id, por isso o else para não busca o banco. tendo em vista que ainda não foi gravada
            contaCheque = c;
            System.out.println("ContaCheque: " + contaCheque);
            if (contaCheque.getCheques() == null) {
                contaCheque.setCheques(new ArrayList<Cheque>());
            }
        }

    }

    public BigDecimal getSomaCheque() {
        BigDecimal totalCheques = new BigDecimal(0);
        if (contaCheque.getCheques() != null) {
            for (Iterator<Cheque> it = contaCheque.getCheques().iterator(); it.hasNext();) {
                Cheque itens = it.next();
                totalCheques = totalCheques.add(itens.getValor());
            }
        }
        return totalCheques;
    }

    public void selecionaCheque(ActionEvent e) {
        setCheque((Cheque) e.getComponent().getAttributes().get("cheque"));
    }

    public void removecheque() {
        cheque.setContasPagar(contaCheque);
        contaCheque.getCheques().remove(cheque);



    }

    public void estornaCompra() {
        qntContasPagar = 0;
        qntContasReceber = 0;
        compra.setStatus("Estornada");
        validaEstorno();


        for (ItensCompra it : compra.getItensCompra()) {
            Produto p = new Produto();
            p = produtodao.find(it.getProduto().getId());
            p.setQnt(p.getQnt() - it.getQuantidade());
            produtodao.save(p);


        }
        //  addConta();
        getDao().save(compra);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage("tabela", new FacesMessage(FacesMessage.SEVERITY_INFO, "COMPRA ESTORNADA COM SUCESSO", "Compra estorna com Sucesso " + qntContasPagar + "Conta(s) a Pagar Alterada(s) " + qntContasReceber + "Conta(s) a Receber Gerada(s)"));




    }

    public void validaEstorno() {

        try {
            for (ContasPagar cp : compra.getContasaPagar()) {
                if (cp.getStatus().equals("Liquidado")) {
                    ContasReceber cr = new ContasReceber();
                    cr.setValor(cp.getValor());
                    cr.setDataConta(cp.getDataConta());
                    cr.setDataPagamento(cp.getDataPagamento());
                    cr.setNumeroParcelas(cp.getNumeroParcelas());
                    cr.setPessoa(cp.getPessoa());
                    cr.setStatus("Liquidado");
                    PegaUsuario();
                    cr.setUsuario(compra.getUsuario());
                    System.out.println("Ta entrando aki -------------------------------------------");
                    contasReceberDao.save(cr);
                    qntContasReceber++;

                }


                if (cp.getStatus().equals("Liquidado/Cheque")) {
                    ContasPagar conta = contasdao.find(cp.getId());
                    ContasReceber cr = new ContasReceber();
                    cr.setValor(conta.getValor());
                    cr.setDataConta(conta.getDataConta());
                    cr.setDataPagamento(conta.getDataPagamento());
                    cr.setNumeroParcelas(conta.getNumeroParcelas());
                    cr.setPessoa(conta.getPessoa());
                    cr.setCheques(conta.getCheques());
                    cr.setStatus("Liquidado/Cheque");
                    PegaUsuario();
                    cr.setUsuario(compra.getUsuario());
                    contasReceberDao.save(cr);
                    qntContasPagar++;

                }



                qntContasPagar++;
                cp.setStatus("Estornada");
                PegaUsuario();
                cp.setUsuario(compra.getUsuario());
                compra.getContasaPagar().set(compra.getContasaPagar().indexOf(cp), cp);//seta a posição do objeto na lista


            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Ocorreu um erro durante o Processo!"));

        }
    }

    //---------------------------------------RELATÓRIOS----------------------------------------------------------------------

    public void imprimeRelatorio() throws IOException, SQLException {
        ArrayList<Compra> lista = new ArrayList<Compra>();
        lista = (ArrayList<Compra>) getDao().findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Conexao c = new Conexao();
        HashMap parameters = new HashMap();
        parameters.put("pCliente", "%" + filtro + "%");
        parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
        parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/CompraCliente.jasper"), parameters, c.getConnection());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"CompraCliente.pdf\"");
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

    //------------------------------------FUNCIONARIO DATA ----------------------------------------------------------
    public void imprimeRelatorioFunciData() throws IOException, SQLException {
        if (filtrofun == null) {
            ArrayList<Compra> lista = new ArrayList<Compra>();
            lista = (ArrayList<Compra>) getDao().findAll();
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
            Conexao c = new Conexao();
            HashMap parameters = new HashMap();
            parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
            parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
            try {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.responseComplete();
                ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/CompraData.jasper"), parameters, c.getConnection());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                exporter.exportReport();
                byte[] bytes = baos.toByteArray();
                if (bytes != null && bytes.length > 0) {
                    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                    response.setContentType("application/pdf");
                    response.setHeader("Content-disposition", "inline; filename=\"CompraData.pdf\"");
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

            ArrayList<Compra> lista = new ArrayList<Compra>();
            lista = (ArrayList<Compra>) getDao().findAll();
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
            Conexao c = new Conexao();
            HashMap parameters = new HashMap();
            parameters.put("pFuncionario", "%" + filtrofun + "%");
            parameters.put("dt_inicio", new SimpleDateFormat("yyyy/MM/dd").format(dtinicio));
            parameters.put("dt_fim", new SimpleDateFormat("yyyy/MM/dd").format(dtfim));
            try {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.responseComplete();
                ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
                JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/CompraFuncionarioData.jasper"), parameters, c.getConnection());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                exporter.exportReport();
                byte[] bytes = baos.toByteArray();
                if (bytes != null && bytes.length > 0) {
                    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                    response.setContentType("application/pdf");
                    response.setHeader("Content-disposition", "inline; filename=\"CompraFuncionarioData.pdf\"");
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
}
