package model;

import java.util.*;
import java.time.*;

public class Pedido {

    // atributos
    private Long id;
    private String formaPagamento;
    private LocalDate dataCriacao;
    private String estado;
    private LocalDate dataModificacao;
    private Double totalPedido;
    private Boolean situacao;

    // ligações entre classes
    Cliente cliente;
    // composição com item pedido
    List<Item> itens = new ArrayList<>();

    // construtores
    public Pedido(Long id, String formaPagamento, LocalDate dataCriacao, String estado,
            LocalDate dataModificacao, Double totalPedido, Boolean situacao, Cliente cliente,
            List<Item> itens) {
        this.id = id;
        this.formaPagamento = formaPagamento;
        this.dataCriacao = dataCriacao;
        this.estado = estado;
        this.dataModificacao = dataModificacao;
        this.totalPedido = totalPedido;
        this.situacao = situacao;
        this.cliente = cliente;
        this.itens = itens;
    }

    public Pedido(List<Item> itens) {
        this.itens = itens;
    }

    // construtores
    public Pedido(Long id, String formaPagamento, LocalDate dataCriacao, String estado,
            LocalDate dataModificacao, Double totalPedido, Boolean situacao) {
        this.id = id;
        this.formaPagamento = formaPagamento;
        this.dataCriacao = dataCriacao;
        this.estado = estado;
        this.dataModificacao = dataModificacao;
        this.totalPedido = totalPedido;
        this.situacao = situacao;
        /*
         * this.cliente = cliente;
         * this.itens = itens;
         */
    }

    public Pedido() {
    }

    // gets and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "\nPedido{" +
                "id=" + id +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", estado='" + estado + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataModificacao=" + dataModificacao +
                ", totalPedido=" + totalPedido +
                ", situacao=" + situacao +
                ", cliente=" + cliente +
                ", itens=" + itens +
                '}';
    }

}