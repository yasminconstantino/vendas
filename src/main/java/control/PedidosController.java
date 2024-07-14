package control;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import dao.PedidoDAO;
import model.Cliente;
import model.Pedido;

public class PedidosController {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int op = 0;
        do {
            System.out.println("\n\n******** Pedidos ********");
            System.out.print(
                    		"\n1. Excluir Pedido" +
                            "\n2. Lista todos os pedidos inativos" +
                            "\n3. Lista todos os pedidos ativos" +
                            "\n4. Lista todos os pedidos por período" +
                            "\n5. Listar pedidos de um cliente" +
                            "\nDigite a opção (0 para sair): ");
            op = input.nextInt();
            input.nextLine();
            switch (op) {
                case 1:
                    excluir();
                    break;
                case 2:
                    listarPedidosInativos();
                    break;
                case 3:
                    listarPedidosAtivos();
                    break;
                case 4:
                    pedidosPorPeriodo();
                    break;
                case 5:
                    selectPedidosByIdCliente();
                    break;
                default:
                    if (op != 0)
                        System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    private static void checkout() {
        System.out.print("\nDigite o código do pedido: ");
        int id = input.nextInt();
        input.nextLine();
        Pedido pedido = PedidoDAO.selectPedidoById(id);
        if (pedido == null) {
            System.out.println("\nPedido não encontrado.");
        } else {
            // Assuming we are completing the order (marking it as completed)
            pedido.setSituacao(false); // Assuming false means completed
            pedido.setDataModificacao(LocalDate.now());
            if (PedidoDAO.updatePedido(pedido)) {
                System.out.println("\nPedido finalizado com sucesso!");
            } else {
                System.out.println("\nErro ao finalizar o pedido.");
            }
        }
    }

    private static void enviar() {
        System.out.print("\nDigite o código do pedido: ");
        int id = input.nextInt();
        input.nextLine();
        Pedido pedido = PedidoDAO.selectPedidoById(id);
        if (pedido == null) {
            System.out.println("\nPedido não encontrado.");
        } else {
            pedido.setEstado("\nEnviado");
            pedido.setDataModificacao(LocalDate.now());
            if (PedidoDAO.updatePedido(pedido)) {
                System.out.println("\nPedido enviado com sucesso!");
            } else {
                System.out.println("\nErro ao enviar o pedido.");
            }
        }
    }

    private static void excluir() {
        System.out.print("\nDigite o código do pedido: ");
        int id = input.nextInt();
        input.nextLine();
        if (PedidoDAO.deletePedido(id)) {
            System.out.println("\nPedido excluído com sucesso!");
        } else {
            System.out.println("\nErro ao excluir o pedido.");
        }
    }

    private static void listarPedidosInativos() {
        List<Pedido> pedidos = PedidoDAO.selectPedidosBySituacao(false);
        if (pedidos.isEmpty()) {
            System.out.println("\nNão há pedidos inativos.");
        } else {
            System.out.println("\nPedidos inativos:");
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
                System.out.println();
            }
        }
    }

    private static void listarPedidosAtivos() {
        List<Pedido> pedidos = PedidoDAO.selectPedidosBySituacao(true);
        if (pedidos.isEmpty()) {
            System.out.println("\nNão há pedidos ativos.");
        } else {
            System.out.println("\nPedidos ativos:");
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
                System.out.println();
            }
        }
    }

    private static void pedidosPorPeriodo() {
        System.out.print("\nDigite a data de início (YYYY-MM-DD): ");
        String dataInicioStr = input.nextLine();
        System.out.print("Digite a data de fim (YYYY-MM-DD): ");
        String dataFimStr = input.nextLine();
        LocalDate dataInicio = LocalDate.parse(dataInicioStr);
        LocalDate dataFim = LocalDate.parse(dataFimStr);
        List<Pedido> pedidos = PedidoDAO.selectPedidosByPeriodo(dataInicio, dataFim);
        if (pedidos.isEmpty()) {
            System.out.println("Não há pedidos nesse período.");
        } else {
            System.out.println("Pedidos no período de " + dataInicio + " a " + dataFim + ":");
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }

    private static void selectPedidosByIdCliente() {
        System.out.print("\nDigite o código do cliente: ");
        int id = input.nextInt();
        input.nextLine();
        Cliente cliente = ClienteDAO.selectClienteById(id);
        if (cliente == null) {
            System.out.println("\nCodigo inexistente.");
        } else {
            System.out.println("\nCliente Pesquisado: " + cliente);
            List<Pedido> pedidos = PedidoDAO.selectPedidosByIdCliente(cliente.getId());
            if (pedidos.isEmpty()) {
                System.out.println("\nNão há pedidos para cliente " + cliente.getNome());
            } else {
                System.out.println("\nLista de pedidos do cliente " + cliente.getNome());
                System.out.println(pedidos);
            }
        }

    }
}