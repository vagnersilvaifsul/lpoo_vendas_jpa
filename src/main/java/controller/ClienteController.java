package controller;

import dao.DAO;
import model.Cliente;

import java.util.List;
import java.util.Scanner;

public class ClienteController {

    private static final Scanner input = new Scanner(System.in);
    private static final DAO<Cliente> daoCliente = new DAO<>(Cliente.class);

    public static void main(String[] args) {

        int opcao;
        do {
            System.out.print("\n\"-------  MENU cliente -------\"");
            System.out.print(
                """

                    1. Inserir novo cliente
                    2. Atualizar um cliente
                    3. Excluir um cliente (tornar inativo)
                    4. Ativar um cliente
                    5. Listar todos os clientes
                    6. Buscar cliente pelo código
                    7. Buscar clientes pelo nome
                    8. Buscar clientes pela situação
                    Opção (Zero p/sair):\s""");
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> atualizar();
                case 3 -> excluir();
                case 4 -> ativar();
                case 5 -> selectClientes();
                case 6 -> selectClientesById();
                case 7 -> selectClientesByNome();
                case 8 -> selectClientesBySituacao();
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
        Cliente cliente;
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

                    if (daoCliente.begin()
                        .update(cliente)
                        .commit()) {
                        System.out.println("Cliente atualizado:" + cliente);
                    } else {
                        System.out.println("Erro ao tentar atualizar o cliente. Por favor, contate o adminstrador.");
                    }
                    opcao = 1;
                }

            }
        }while(opcao != 0);
    }

    private static void excluir() {
        System.out.println("\n++++++ Excluir um Cliente ++++++");
        Cliente cliente;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do cliente (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else if(codigo > 0){
                cliente = daoCliente.selectById(codigo);
                if (cliente == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println(cliente);
                    System.out.print("Excluir este cliente? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Tem certeza disso? (0-sim/1-não) ");
                        cliente.setSituacao(false);
                        input.nextLine();
                        if (daoCliente.begin()
                            .update(cliente)
                            .commit()) {
                            System.out.println("Cliente excluído com sucesso:" + cliente);
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
        System.out.println("\n++++++ Ativar um Cliente ++++++");
        Cliente cliente;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do cliente (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else if(codigo > 0){
                cliente = daoCliente.selectById(codigo);
                if (cliente == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println(cliente);
                    System.out.print("Ativar este cliente? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Tem certeza disso? (0-sim/1-não) ");
                        cliente.setSituacao(true);
                        input.nextLine();
                        if (daoCliente.begin()
                            .update(cliente)
                            .commit()) {
                            System.out.println("Cliente ativado com sucesso:" + cliente);
                        } else {
                            System.out.println("Erro ao tentar ativar o cliente. Por favor, contate o adminstrador.");
                        }
                    }

                }
            } else {
                System.out.println("Digite um código válido!!!");
            }
        } while (opcao != 0);
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
        List<Cliente> clientes = daoCliente.selectByName(nome);
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
        switch (situacao) {
            case 0 -> {
                clientes = daoCliente.selectBySituacao(false);
                System.out.println("Clientes na situação INATIVO:\n " + clientes);
            }
            case 1 -> {
                clientes = daoCliente.selectBySituacao(true);
                System.out.println("Clientes na situação ATIVO:\n " + clientes);
            }
        }
    }

}//fim classe
