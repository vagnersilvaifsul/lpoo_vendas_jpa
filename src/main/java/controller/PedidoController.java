package controller;

import dao.DAO;
import model.Cliente;
import model.Pedido;

import java.util.List;
import java.util.Scanner;

public class PedidoController {
	
	private static final Scanner input = new Scanner(System.in);
    private static final DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
    private static final DAO<Pedido> daoPedido = new DAO<>(Pedido.class);
	
    public static void main(String[] args) {
        int opcao;
        do{
            System.out.println("\n\n******** Pedidos ********");
            System.out.print(
                """
                    1. Check-out do pedido
                    2. Enviar Pedido
                    3. Excluir Pedido
                    4. Lista todos os pedidos inativos
                    5. Lista todos os pedidos ativos
                    6. Lista todos os pedidos por período
                    7. Listar pedidos de um cliente
                    Digite a opção (0 para sair):\s"""
            );
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1, 2, 3, 4, 5, 6 -> System.out.println("em desenvolvimento " + opcao);
                case 7 -> selectPedidosByIdCliente();
                default -> {
                    if (opcao != 0) System.out.println("Opção inválida.");
                }
            }
        }while (opcao != 0);
    }
    
    private static void selectPedidosByIdCliente() {
    	System.out.print("Digite o código do cliente: ");
        long id = input.nextLong();
        input.nextLine();
        Cliente cliente = daoCliente.selectById(id);
        if(cliente == null) {
        	System.out.println("Codigo inexistente.");
        }else {
        	System.out.println("\nCliente Pesquisado: " + cliente);
        	List<Pedido> pedidos = daoPedido.selectResultSetById(cliente.getId());
        	if(pedidos.isEmpty()) {
        		System.out.println("Não há pedidos para cliente " + cliente.getNome());
        	}else {
        		System.out.println("Lista de pedidos do cliente " + cliente.getNome());
        		System.out.println(pedidos);
        	}
        }
        
    }
}
