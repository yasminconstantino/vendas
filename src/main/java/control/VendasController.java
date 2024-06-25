package control;

import dao.ClienteDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import model.Cliente;
import model.Item;
import model.Pedido;
import model.Produto;

import java.text.*;
import java.util.*;

public class VendasController {
    private static final Scanner input = new Scanner(System.in);
    private static double totalPedido;

    public static void main(String[] args) {
        int op;
        Cliente cliente;
        Produto produto;

        List<Item> itens = new ArrayList<>();
        do {
            op = 0;
            System.out.println("\n --- Vendas --- ");
            System.out.print("Digite o id do cliente: ");
            long codCliente = input.nextLong();
            input.nextLine();
            cliente = ClienteDAO.selectClienteById(codCliente);
            if (cliente == null) {
                System.out.println("Id inválido, nenhum cliente encontrado!");
                op = 1;
            } else {
                System.out.println("Cliente selecionado: " + cliente);
                int sair = 2;
                do {
                    System.out.print("Digite o código do produto: ");
                    long codProd = input.nextLong();
                    input.nextLine();
                    produto = ProdutoDAO.selectProdutoById(codProd);
                    if (produto == null) {
                        System.out.println("Código inválido, nenhum produto encontrado!");
                        sair = 1;
                    } else {
                        System.out.println("Produto selecionado:" + produto);
                        System.out.print("Digite a quantidade: ");
                        int quant = input.nextInt();
                        input.nextLine();
                        if (quant > produto.getQuantidade()) {
                            System.out.println("Quantidade inválida. Não existem unidades suficientes em estoque!");
                            sair = 1;
                        } else {
                            Item item = new Item(produto);
                            item.setQuantidade(quant);
                            item.setTotalItem(quant * produto.getValor());
                            item.setSituacao(true);
                            itens.add(item);
                            System.out.println("Produto adionado ao carrinho.");
                            baixarEstoque(item);
                            System.out.print("\nDeseja vender outro produto? \n 1 - sim \n 2 - não ");
                            sair = input.nextInt();
                            input.nextLine();
                        }
                    }
                } while (sair != 2);
                if (!itens.isEmpty()) {
                    System.out.println("\n --- Seu carrinho --- ");
                    totalPedido = 0.0;
                    itens.forEach(i -> {
                        String nome = i.getProduto().getNome();
                        String precoUnity = NumberFormat.getCurrencyInstance().format(i.getProduto().getValor());
                        int MAX = 20;
                        if (nome.length() <= MAX) {
                            for (int j = nome.length(); j < MAX; j++) {
                                nome += "";
                            }
                        }
                        if (precoUnity.length() <= MAX) {
                            for (int j = precoUnity.length(); j < MAX; j++) {
                                precoUnity += " ";
                            }
                        }
                        System.out.println(
                                "\tProduto: " + nome +
                                        "\tValor unidade = " + precoUnity +
                                        "\t\tQuantidade = " + i.getQuantidade() +
                                        "\t\tTotalItem = " + (NumberFormat.getCurrencyInstance()
                                                .format(i.getQuantidade() * i.getProduto().getValor())));
                        totalPedido += i.getQuantidade() * i.getProduto().getValor();
                    });
                    System.out.println(
                            " --- \n" + "TOTAL DO PEDIDO = " + NumberFormat.getCurrencyInstance().format(totalPedido));
                    System.out.print("Fechar o pedido? \n 1 - sim \n 2 - não");
                    op = input.nextInt();
                    input.nextLine();
                    if (op == 1) {
                        Pedido pedido = new Pedido(itens);
                        pedido.setFormaPagamento("a vista");
                        pedido.setEstado("aberto");
                        pedido.setCliente(cliente);
                        pedido.setTotalPedido(totalPedido);
                        if (PedidoDAO.insertPedido(pedido)) {
                            System.out.println("Pedido salvo.");
                        } else {
                            System.out.println("Não foi possível salvar o pedido. Por favor, contate o suporte.");
                        }
                    } else if (op == 2) {
                        System.out.print("Ops! Tem certeza? Você perderá esse pedido. \n 1 - sim \n 2 - não ");
                        op = input.nextInt();
                        input.nextLine();
                        if (op == 1) {
                            System.out.println("Pedido cancelado.");
                            itens.forEach((i) -> {
                                voltarEstoque(i);
                            });
                        }
                    }
                    op = 0;
                }
            }
        } while (op != 0);
    }

    private static void baixarEstoque(Item item) {
        Produto produto = item.getProduto();
        produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
        ProdutoDAO.updateProduto(produto);
    }

    private static void voltarEstoque(Item item) {
        Produto produto = item.getProduto();
        produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
        ProdutoDAO.updateProduto(produto);
    }
}
