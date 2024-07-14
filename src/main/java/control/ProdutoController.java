package control;

import java.util.List;
import java.util.Scanner;

import dao.ProdutoDAO;
import model.Produto;

public class ProdutoController {

    private static final Scanner input = new Scanner(System.in);// assim pego do teclado e posso pegar de diversas
                                                                // maneiras, como arquivo

    public static void main(String[] args) {

        /*
         * System.out.println("Digite um numero interiro: ");// imprima e de uma nova
         * linha
         * int ni = input.nextInt();// le o dado e guarda na variavel
         * input.nextLine();
         * System.out.println(ni);
         * 
         * System.out.println("Digite um numero double: ");
         * double nd = input.nextDouble();
         * input.nextLine();
         * System.out.println(nd);
         * 
         * System.out.println("Digite uma frase");
         * String s = input.nextLine();
         * System.out.println(s);
         * 
         * System.out.println("end");
         */

        int op = 0;

        do {
            System.out.println("\n --- Menu dos Produtos ---");
            System.out.println("(para escolher uma opção digite um numero inteiro)");
            System.out.println(
                    "\n1 - Inserir novo produto" +
                            "\n2 - Atualizar produto" +
                            "\n3 - Listar os produtos" +
                            "\n4 - Buscar um produto pelo id" +
                            "\n5 - Buscar um produto pelo nome" +
                            "\n6 - Buscar um produto pela situação" +
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
                    selectProdutos();
                    break;
                case 4:
                    selectProdutoById();
                    break;
                case 5:
                    selectProdutoByName();
                    break;
                case 6:
                    selectProdutoBySituacao();
                    break;
                default:
                    if (op != 0) {
                        System.out.println("\nOpção invalida!");
                    }
            }
        } while (op != 0);
    }

    private static void inserir() {
        Produto prod = new Produto();
        System.out.println("\n --- Cadastrar novo produto: ---");
        System.out.println("\nDigite um nome para o produto: ");
        prod.setNome(input.nextLine());
        System.out.print("\nDigite uma descrição para o produto: ");
        prod.setDescricao(input.nextLine());
        System.out.print("\nDigite um valor para o produto: ");
        prod.setValor(input.nextDouble());
        input.nextLine(); // limpa o input
        System.out.print("\nDigite a quantidade em estoque: ");
        prod.setQuantidade(input.nextInt());
        input.nextLine(); // limpa o input
        prod.setSituacao(true);
        if (ProdutoDAO.insertProduto(prod)) {
            System.out.println("\nO produto foi salvo com sucesso! :)");
        } else {
            System.out.println("\nOcorreu um erro. Entre em contato com o suporte. :(");
        }
    }

    private static void atualizar() {
        System.out.println("\n --- Alterar um produto: ---");
        Produto prod = null;

        int op = 0;
        do {
            System.out.println("\nDigite o id do produto que desej alterar ou zero p/ sair: ");
            int cod = input.nextInt();
            input.nextLine();
            if (cod == 0) {
                op = 0;
            } else {
                prod = ProdutoDAO.selectProdutoById(cod);
                if (prod == null) {
                    System.out.println("\nId Invalido. :(");
                } else {
                    System.out.println("Nome: " + prod.getNome());
                    System.out.println("\n Deseja alterar? \n0- Sim \n1 - Não");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.println("\nDigite um novo nome para o produto: ");
                        prod.setNome(input.nextLine());
                    }
                    System.out.println("\nDescrição: " + prod.getDescricao());
                    System.out.println("\n Deseja alterar? \n0- Sim \n1 - Não");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("\nDigite a nova descrição do produto: ");
                        prod.setDescricao(input.next());
                    }
                    System.out.println("Valor: " + prod.getValor());
                    System.out.println("\n Deseja alterar? \n0- Sim \n1 - Não");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("\nDigite o novo valor do produto: ");
                        prod.setValor(input.nextDouble());
                        input.nextLine();
                    }
                    System.out.println("\nEstoque: " + prod.getQuantidade());
                    System.out.println("\n Deseja alterar? \n0- Sim \n1 - Não");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("\nDigite o novo estoque do produto: ");
                        prod.setQuantidade(input.nextInt());
                        input.nextLine();
                    }
                    prod.setSituacao(true);
                    if (ProdutoDAO.updateProduto(prod)) {
                        System.out.println("\nproduto salvo:" + prod + ":)");
                    } else {
                        System.out.println("\nErro ao tentar salvar o produto. Por favor, contate o suporte. :(");
                    }
                    op = 1;
                }

            }
        } while (op != 0);
    }

    private static void selectProdutos() {
        System.out.println("\n Lista com os produtos cadastrados no BD: " + ProdutoDAO.selectProdutos());
    }

    private static void selectProdutoById() {
        System.out.println("\n Digite o id do produto: ");
        Produto prod = ProdutoDAO.selectProdutoById(input.nextInt());// o input deve sempre ser seguido de next e o
                                                                      // tipo do dado
        input.nextLine();
        if (prod != null) {
            System.out.println(prod);
        } else {
            System.out.println("\n Id não localizado");
        }
    }

    private static void selectProdutoByName() {
        System.out.println("\n Digite o nome do produto: ");
        String nome = input.next();
        System.out.println("\nChave de pesquisa: " + nome);
        List<Produto> produtos = ProdutoDAO.selectProdutoByName(nome);
        if (produtos.isEmpty()) {
            System.out.println("\nNão há registros correspondentes para: " + nome);
        } else {
            System.out.println(produtos);
        }
    }

    private static void selectProdutoBySituacao() {
        System.out.println("\nEscolha a situação desejada: \n 0 - inativo \n 1 - ativo");
        int situa = input.nextInt();
        input.nextLine();
        List<Produto> produtos;
        switch (situa) {
            case 0:
                produtos = ProdutoDAO.selectProdutoBySituacao(false);
                System.out.println("\n Produtos INATIVOS: \n" + produtos);
                break;
            case 1:
                produtos = ProdutoDAO.selectProdutoBySituacao(true);
                System.out.println("\n Produtos ATIVOS: \n" + produtos);
                break;
        }
    }

}
