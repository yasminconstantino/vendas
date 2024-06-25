package control;

import java.util.*;

import dao.ClienteDAO;
import model.Cliente;

public class ClienteController {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        int op = 0;
        do {
            System.out.print("\n\"-------  MENU cliente -------\"");
            System.out.print(
                    "\n1 - Inserir novo cliente" +
                            "\n2 - Atualizar um cliente" +
                            "\n3 - Listar todos os clientes" +
                            "\n4 - Buscar cliente pelo código" +
                            "\n5 - Buscar clientes pelo nome"
                            + "\n6 - Buscar clientes pela situação" +
                            "\n0 - Voltar ao menu anterior");
            op = input.nextInt();
            input.nextLine();
            switch (op) {
                case 1:
                    inserir();
                    break;
                case 2:
                    atualizar();
                    break;
                case 3:
                    selectClientes();
                    break;
                case 4:
                    selectClientesById();
                    break;
                case 5:
                    selectClientesByNome();
                    break;
                case 6:
                    selectClientesBySituacao();
                    break;
                default:
                    if (op != 0)
                        System.out.println("\nOpção inválida.");
            }
        } while (op != 0);

    }

    // opção 1
    private static void inserir() {
        Cliente cliente = new Cliente();
        System.out.println("\n++++++ Cadastro de novo Cliente ++++++");
        System.out.print("\nDigite o nome do cliente: ");
        cliente.setNome(input.nextLine());
        System.out.print("\nDigite o sobrenome do cliente: ");
        cliente.setSobrenome(input.nextLine());
        cliente.setSituacao(true);

        if (ClienteDAO.insertCliente(cliente)) {
            System.out.println("\nCliente salvo com sucesso.");
        } else {
            System.out.println("\nHouve um erro ao salvar o cliente. Por favor, contate o administrador do sistema.");
        }
    }

    // opção 2
    private static void atualizar() {
        System.out.println("\n++++++ Alterar um Cliente ++++++");
        Cliente cliente = null;
        int op = 0;
        do {
            System.out.print("\nDigite o código do cliente \n(Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                op = 0;
            } else {
                cliente = ClienteDAO.selectClienteById(codigo);
                if (cliente == null) {
                    System.out.println("\nCódigo inválido.");
                } else {
                    System.out.println("\nNome: " + cliente.getNome());
                    System.out.print("\nAlterar? \n 0 - sim \n 1 - não ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.println("\nDigite o novo nome do cliente: ");
                        cliente.setNome(input.nextLine());
                    }
                    System.out.println("\nSobrenome: " + cliente.getSobrenome());
                    System.out.print("\nAlterar? \n 0 - sim \n 1 - não ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("\nDigite o novo sobrenome do cliente: ");
                        cliente.setSobrenome(input.next());
                    }
                    cliente.setSituacao(true);
                    if (ClienteDAO.updateCliente(cliente)) {
                        System.out.println("\ncliente salvo:" + cliente);
                    } else {
                        System.out.println("\nErro ao tentar salvar o cliente. Por favor, contate o adminstrador.");
                    }
                    op = 1;
                }

            }
        } while (op != 0);
    }

    // opção 3
    private static void selectClientes() {
        System.out.println("\nLista de clientes cadastrados no banco de dados:\n" + ClienteDAO.selectClientes());
    }

    // opção 4
    private static void selectClientesById() {
        System.out.print("\nDigite o código do cliente: ");
        Cliente cliente = ClienteDAO.selectClienteById(input.nextLong());
        input.nextLine();
        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("\nCódigo não localizado.");
        }
    }

    // opção 5
    private static void selectClientesByNome() {
        System.out.print("\nDigite o nome do cliente: ");
        String nome = input.next();
        System.out.println("\nChave de pesquisa: " + nome);
        List<Cliente> clientes = ClienteDAO.selectClientesByName(nome);
        if (clientes.isEmpty()) {
            System.out.println("\nNão há registros correspondentes para: " + nome);
        } else {
            System.out.println(clientes);
        }
    }

    // opção 6
    private static void selectClientesBySituacao() {
        System.out.print("\nEscolha uma das situações \n 0 - inativo \n 1 - ativo: ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Cliente> clientes;
        switch (situacao) {
            case 0:
                clientes = ClienteDAO.selectClientesBySituacao(false);
                System.out.println("\nClientes na situação INATIVO:\n " + clientes);
                break;
            case 1:
                clientes = ClienteDAO.selectClientesBySituacao(true);
                System.out.println("\nClientes na situação ATIVO:\n " + clientes);
                break;
        }
    }

}