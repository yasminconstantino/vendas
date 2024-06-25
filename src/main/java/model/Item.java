package model;

//import java.math.BigDecimal;

public class Item {
    // atributos
    private Long idItemPedido;
    private int quantidade;
    private Double totalItem;
    private Boolean situacao;

    // cria uma composição com a classe produto;
    private Produto produto;

    // construtores
    public Item(Long idItemPedido, int quantidade, Double totalItem, Boolean situacao, Produto produto) {
        this.idItemPedido = idItemPedido;
        this.quantidade = quantidade;
        this.totalItem = totalItem;
        this.situacao = situacao;
        this.produto = produto;
    }

    public Item(Produto produto) {
        this.produto = produto;
    }

    public Item() {
    }

    // gets and setters
    public Long getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    // string
    @Override
    public String toString() {
        return "\nItemPedido{" +
                "idItemPedido=" + idItemPedido +
                ", quantidade=" + quantidade +
                ", totalItem=" + totalItem +
                ", situacao=" + situacao +
                ", produto=" + produto +
                '}';
    }
}