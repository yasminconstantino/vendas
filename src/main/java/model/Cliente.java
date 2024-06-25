package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    //atributos
    private Long id;
    private String nome;
    private String sobrenome;
    private Boolean situacao;
    private List <Pedido> pedidos = new ArrayList<>();

    //construtores
    public Cliente(String nome, String sobrenome, Long id, Boolean situacao, List<Pedido> pedidos) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.id = id;
        this.situacao = situacao;
        this.pedidos = pedidos;
    }

    public Cliente(Long id, String nome, String sobrenome, Boolean situacao) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.situacao = situacao;
    }
    
    public Cliente( String nome, String sobrenome, Boolean situacao) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.situacao = situacao;
    }

    public Cliente() {
    }

    //acessores
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    // to String
    @Override
    public String toString() {
        return "\nCliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", situacao=" + situacao +
                ", pedidos=" + pedidos +
                '}';
    }
}