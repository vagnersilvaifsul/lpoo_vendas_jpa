package controller;

import dao.DAO;
import model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteController {
	
	private static final Scanner input = new Scanner(System.in);
	private static final DAO<Cliente> daoCliente = new DAO<>(Cliente.class);

	public static void main(String[] args) {

		int opcao = 0;
		do {
			System.out.print("\n\"-------  MENU cliente -------\"");
			System.out.print(
				"""

					1. Inserir novo cliente
					2. Atualizar um cliente
					3. Listar todos os clientes
					4. Buscar cliente pelo código
					5. Buscar clientes pelo nome
					6. Buscar clientes pela situação
					Opção (Zero p/sair):\s""");
			opcao = input.nextInt();
			input.nextLine();
			switch (opcao) {
				case 1 -> inserir();
				case 2 -> atualizar();
				case 3 -> selectClientes();
				case 4 -> selectClientesById();
				case 5 -> selectClientesByNome();
				case 6 -> selectClientesBySituacao();
				default -> {
					if (opcao != 0)
						System.out.println("Opção inválida.");
				}
			}
		} while (opcao != 0);

	}
	
	//opção 1
	private static void inserir() {
		Cliente cliente = new Cliente();
        System.out.println("\n++++++ Cadastro de novo Cliente ++++++");
        System.out.print("Digite o nome do cliente: ");
        cliente.setNome(input.nextLine());
        System.out.print("\nDigite o sobrenome do cliente: ");
        cliente.setSobrenome(input.nextLine());
        cliente.setSituacao(true);
        
        if(daoCliente.begin()
			.insert(cliente)
			.commit()) {
        	System.out.println("\nCliente salvo com sucesso.");
        }else {
        	System.out.println("\nHouve um erro ao salvar o cliente. Por favor, contate o administrador do sistema.");
        }     
    }

	//opção 2
	private static void atualizar() {
		System.out.println("\n++++++ Alterar um Cliente ++++++");
		Cliente cliente = null;
        int opcao = 0;
        do{
            System.out.print("\nDigite o código do cliente (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if(codigo == 0) {
            	opcao = 0;
            } else {
                cliente = daoCliente.selectById(codigo);
                if(cliente == null){
                    System.out.println("Código inválido.");
                }else{
                    System.out.println("Nome: " + cliente.getNome());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if(input.nextInt() == 0){
                    	input.nextLine();
                        System.out.println("Digite o novo nome do cliente: ");
                        cliente.setNome(input.nextLine());
                    }
                    System.out.println("Sobrenome: " + cliente.getSobrenome());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if(input.nextInt() == 0){
                    	input.nextLine();
                        System.out.print("Digite o novo sobrenome do cliente: ");
                        cliente.setSobrenome(input.next());
                    }
                    cliente.setSituacao(true);
					//TODO: implementar esse update
//                    if(ClienteDAO.updateCliente(cliente)){
//                        System.out.println("cliente salvo:" + cliente);
//                    }else{
//                        System.out.println("Erro ao tentar salvar o cliente. Por favor, contate o adminstrador.");
//                    }
                    opcao = 1;
                }

            }
        }while(opcao != 0); 
	}
	
	//opção 3
	private static void selectClientes() {
		System.out.println("\nLista de clientes cadastrados no banco de dados:\n" + daoCliente.selectAll());
	}
	
	//opção 4
	private static void selectClientesById() {
		System.out.print("\nDigite o código do cliente: ");
        Cliente cliente = daoCliente.selectById(input.nextLong());
        input.nextLine();
        if(cliente != null){
            System.out.println(cliente);
        }else{
            System.out.println("Código não localizado.");
        }
	}
	
	//opção 5
	private static void selectClientesByNome() {
        System.out.print("Digite o nome do cliente: ");
        String nome = input.next();
        System.out.println("Chave de pesquisa: " + nome);
		//TODO: implementar esse select
        List<Cliente> clientes = new ArrayList<>();//ClienteDAO.selectClientesByName(nome);
        if(clientes.isEmpty()){
            System.out.println("Não há registros correspondentes para: " + nome);
        }else{
            System.out.println(clientes);
        }
    }
	
	//opção 6
	private static void selectClientesBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Cliente> clientes;
        switch(situacao) {
        	case 0:
				//TODO: implementar esse select
        		clientes = new ArrayList<>(); //ClienteDAO.selectClientesBySituacao(false);
        		System.out.println("Clientes na situação INATIVO:\n " + clientes);
        		break;
        	case 1:
        		clientes = new ArrayList<>(); //ClienteDAO.selectClientesBySituacao(true);
        		System.out.println("Clientes na situação ATIVO:\n " + clientes);
        		break;	
        }
    }

}//fim classe
