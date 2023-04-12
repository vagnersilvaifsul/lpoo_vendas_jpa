package controller;

import dao.DAO;
import model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemController {
	private static final Scanner input = new Scanner(System.in);
	private static final DAO<Item> daoItem = new DAO<>(Item.class);

	public static void main(String[] args) {

		int opcao = 0;
		do {
			System.out.print("\n\"-------  MENU cliente -------\"");
			System.out.print(
					"\n1. Listar os itens pelo código do pedido" + 
					"\nOpção (Zero p/sair): ");
			opcao = input.nextInt();
			input.nextLine();
			switch (opcao) {
			case 1:
				selectItensByPedido();
				break;
			default:
				if (opcao != 0)
					System.out.println("Opção inválida.");
			}
		} while (opcao != 0);
	}
	
	private static void selectItensByPedido() {
		System.out.print("\nDigite o número do pedido: ");
		int codigo = input.nextInt();
		input.nextLine();
		//TODO: implementar essa busca na DAO
		List<Item> itens = new ArrayList<>(); //= daoItem.selectById(codigo);
		if(itens.isEmpty()) {
			System.out.println("Não há itens nesse pedido.");
		} else {
			System.out.println("\nItens do pedido de código: " + codigo + "\n" + itens);
		}
		
	}

}
