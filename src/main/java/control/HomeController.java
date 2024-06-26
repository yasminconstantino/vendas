package control;

import java.util.*;

public class HomeController {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        int op = 0;

        do {
            System.out.println("\n --- Nimsay Store ---");
            System.out.println("\n --- Home ---");
            System.out.println("(para escolher uma opção digite um numero inteiro)");
            System.out.println(
                    "\n 1 - Vender" +
                            "\n 2 - Manter produto" +
                            "\n 3 - Manter Clientes" +
                            "\n 4 - Mnater Itens" +
                            "\n 5 - Manter Pedidos" +
                            "\n 0 - Sair");
            op = input.nextInt();
            input.nextLine();

            switch (op) {
                case 1:
                    VendasController.main(null);
                    break;
                case 2:
                    ProdutoController.main(null);
                    break;
                case 3:
                    ClienteController.main(null);
                    break;
                case 4:
                    ItemController.main(null);
                    break;
                case 5:
                    PedidosController.main(null);
                default:
                    if (op != 0) {
                        System.out.println("\nOpção invalida!");
                    }
            }
        } while (op != 0);
        System.out.println("\n --- Fim da aplicação! --- ");
        input.close(); // ira liberar o recurso
    }

}
