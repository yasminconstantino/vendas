package model;

public class Produto {
    // atributos
    private long idProduto;
    private String nome;
    private String descricao;
    private double valor;
    private Boolean situacao;
    private int quantidade;

    // construtores
    public Produto(long idProduto, String nome, String descricao, double valor, Boolean situacao, int quantidade) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.situacao = situacao;
        this.quantidade = quantidade;
    }

    public Produto() {
    }

    // acessores gets and setters
    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // string
    @Override
    public String toString() {
        return "\nProduto{" +
                "idProduto=" + idProduto +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", situacao=" + situacao +
                ", quantidade=" + quantidade +
                '}';
    }
}