package control;

import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import model.Cliente;

public class ClienteController {
	
	private static final Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		int opcao = 0;
		do {
			System.out.print("\n\"-------  MENU cliente -------\"");
			System.out.print(
					"\n1. Inserir novo cliente" + 
					"\n2. Atualizar um cliente" +
					"\n3. Listar todos os clientes" + 
					"\n4. Buscar cliente pelo código" + 
					"\n5. Buscar clientes pelo nome"
					+ "\n6. Buscar clientes pela situação" + 
					"\nOpção (Zero p/sair): ");
			opcao = input.nextInt();
			input.nextLine();
			switch (opcao) {
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
				if (opcao != 0)
					System.out.println("Opção inválida.");
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
        
        if(ClienteDAO.insertCliente(cliente)) {
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
                cliente = ClienteDAO.selectClienteById(codigo);
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
                    if(ClienteDAO.updateCliente(cliente)){
                        System.out.println("cliente salvo:" + cliente);
                    }else{
                        System.out.println("Erro ao tentar salvar o cliente. Por favor, contate o adminstrador.");
                    }
                    opcao = 1;
                }

            }
        }while(opcao != 0); 
	}
	
	//opção 3
	private static void selectClientes() {
		System.out.println("\nLista de clientes cadastrados no banco de dados:\n" + ClienteDAO.selectClientes());
	}
	
	//opção 4
	private static void selectClientesById() {
		System.out.print("\nDigite o código do cliente: ");
        Cliente cliente = ClienteDAO.selectClienteById(input.nextLong());
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
        List<Cliente> clientes = ClienteDAO.selectClientesByName(nome);
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
        		clientes = ClienteDAO.selectClientesBySituacao(false);
        		System.out.println("Clientes na situação INATIVO:\n " + clientes);
        		break;
        	case 1:
        		clientes = ClienteDAO.selectClientesBySituacao(true);
        		System.out.println("Clientes na situação ATIVO:\n " + clientes);
        		break;	
        }
    }

}//fim classe
