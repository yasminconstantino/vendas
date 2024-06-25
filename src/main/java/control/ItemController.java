package control;

import java.util.*;

import model.Item;
import dao.ItemDAO;

public class ItemController {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int op = 0;

        do {
            System.out.print("\n ---  Menu dos clientes --- ");
            System.out.print(
                    "\n1 - Listar os itens pelo código do pedido" +
                            "\n 0 - Voltar ao menu anterior");
            op = input.nextInt();
            input.nextLine();

            switch (op) {
                case 1:
                    selectItemByPedido();
                    break;

                default:
                    System.out.println("\nOpção Invalida!");
                    break;
            }
        } while (op != 0);
    }

    private static void selectItemByPedido() {
        System.out.print("\nDigite o codigo do pedido: ");
        int cod = input.nextInt();
        input.nextLine();
        List<Item> itens = ItemDAO.selectItemByPedido(cod);
        if (itens.isEmpty()) {
            System.out.println("\nNão há itens nesse pedido.");
        } else {
            System.out.println("\nItens do pedido de codigo: " + cod + "\n" + itens);
        }
    }
}
