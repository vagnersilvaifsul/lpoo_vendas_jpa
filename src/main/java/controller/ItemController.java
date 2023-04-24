package controller;

import dao.DAO;
import model.Item;

import java.util.List;
import java.util.Scanner;

public class ItemController {
	private static final Scanner input = new Scanner(System.in);
	private static final DAO<Item> daoItem = new DAO<>(Item.class);

	public static void main(String[] args) {

		int opcao;
		do {
			System.out.print("\n\"-------  MENU Item -------\"");
			System.out.print(
				"""

					1. Listar os itens pelo código do pedido
					2. Excluir um item do pedido
					3. Ativar um item
					Opção (Zero p/sair):\s""");
			opcao = input.nextInt();
			input.nextLine();
			switch (opcao) {
				case 1 -> selectItensByPedido();
				case 2 -> excluir();
				case 3 -> ativar();
				default -> {
					if (opcao != 0)
						System.out.println("Opção inválida.");
				}
			}
		} while (opcao != 0);
	}
	
	private static void selectItensByPedido() {
		System.out.print("\nDigite o número do pedido: ");
		long codigo = input.nextLong();
		input.nextLine();
		List<Item> itens = daoItem.selectResultSetById(codigo);
		if(itens.isEmpty()) {
			System.out.println("Não há itens nesse pedido.");
		} else {
			System.out.println("\nItens do pedido de código: " + codigo + "\n" + itens);
		}
	}

	private static void excluir(){
		System.out.println("\n++++++ Excluir um item de um pedido ++++++");
		Item item;
		int opcao = 0;
		do {
			System.out.print("\nDigite o código do item (Zero p/sair): ");
			long codigo = input.nextLong();
			input.nextLine();
			if (codigo == 0) {
				opcao = 0;
			} else if(codigo > 0){
				item = daoItem.selectById(codigo);
				if (item == null) {
					System.out.println("Código inválido.");
				} else {
					System.out.println(item);
					System.out.print("Excluir este item? (0-sim/1-não) ");
					if (input.nextInt() == 0) {
						input.nextLine();
						System.out.print("Tem certeza disso? (0-sim/1-não) ");
						item.setSituacao(false);
						input.nextLine();
						if (daoItem.begin()
							.update(item)
							.commit()) {
							System.out.println("Cliente excluído com sucesso:" + item);
						} else {
							System.out.println("Erro ao tentar excluir o cliente. Por favor, contate o adminstrador.");
						}
					}

				}
			} else {
				System.out.println("Digite um código válido!!!");
			}
		} while (opcao != 0);
	}

	private static void ativar(){
		System.out.println("\n++++++ Ativar um item de um pedido ++++++");
		Item item;
		int opcao = 0;
		do {
			System.out.print("\nDigite o código do item (Zero p/sair): ");
			long codigo = input.nextLong();
			input.nextLine();
			if (codigo == 0) {
				opcao = 0;
			} else if(codigo > 0){
				item = daoItem.selectById(codigo);
				if (item == null) {
					System.out.println("Código inválido.");
				} else {
					System.out.println(item);
					System.out.print("Ativar este item? (0-sim/1-não) ");
					if (input.nextInt() == 0) {
						input.nextLine();
						System.out.print("Tem certeza disso? (0-sim/1-não) ");
						item.setSituacao(true);
						input.nextLine();
						if (daoItem.begin()
							.update(item)
							.commit()) {
							System.out.println("Item ativado com sucesso:" + item);
						} else {
							System.out.println("Erro ao tentar ativar o item. Por favor, contate o adminstrador.");
						}
					}

				}
			} else {
				System.out.println("Digite um código válido!!!");
			}
		} while (opcao != 0);
	}

}
