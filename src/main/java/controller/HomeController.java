package control;

import java.util.Scanner;

public class HomeController {
	
	private static final Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		
		int opcao = 0;
		do {
			System.out.print("\n-------  Home -------");
			System.out.print(
				"""

					1. Vender
					2. Manter Produtos
					3. Manter Clientes
					4. Manter Itens
					5. Manter Pedidos
					Opção (Zero p/sair):\s""");
			opcao = input.nextInt();
			input.nextLine();
			switch(opcao) {
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
					System.out.println("Em implementação.");
					break;
				case 5:
					PedidoController.main(null);
					break;	
				default:
					if(opcao != 0) System.out.println("Opção inválida.");
			}
		} while(opcao != 0) ;
		System.out.println("\n\n!!!!!!!! Fim da aplicação !!!!!!!!");
		input.close(); //libera o recurso
	}

}//fim classe
