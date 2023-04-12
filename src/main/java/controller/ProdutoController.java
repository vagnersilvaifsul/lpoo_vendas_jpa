package controller;

import dao.DAO;
import model.Produto;

import java.util.List;
import java.util.Scanner;

public class ProdutoController {

    private static final Scanner input = new Scanner(System.in);
    private static final DAO<Produto> daoProduto = new DAO<>(Produto.class);

    public static void main(String[] args) {

        int opcao;
        do {
            System.out.print("\n\"-------  MENU PRODUTO -------\"");
            System.out.print(
                """

                    1. Inserir novo produto
                    2. Atualizar um produto
                    3. Excluir um produto
                    4. Listar todos os produtos
                    5. Buscar produto pelo código
                    6. Buscar produtos pelo nome
                    7. Buscar produtos pela situação
                    Opção (Zero p/sair):\s""");
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> atualizar();
                case 3 -> excluir();
                case 4 -> selectProdutos();
                case 5 -> selectProdutosById();
                case 6 -> selectProdutosByNome();
                case 7 -> selectProdutosBySituacao();
                default -> {
                    if (opcao != 0) System.out.println("Opção inválida.");
                }
            }
        } while (opcao != 0);
    }

    //opção 1
    private static void inserir() {
        Produto produto = new Produto();
        System.out.println("\n++++++ Cadastro de novo Produto ++++++");
        System.out.print("Digite o nome do produto: ");
        produto.setNome(input.nextLine());
        System.out.print("\nDigite a descrição do produto: ");
        produto.setDescricao(input.nextLine());
        System.out.print("\nDigite o valor do produto: ");
        produto.setValor(input.nextDouble());
        input.nextLine(); //limpa o input
        System.out.print("\nDigite a quantidade em estoque: ");
        produto.setEstoque(input.nextInt());
        input.nextLine(); //limpa o input
        produto.setSituacao(true);
        if (daoProduto.begin()
            .create(produto)
            .commit()) {
            System.out.println("\nProduto salvo com sucesso.");
        } else {
            System.out.println("\nHouve um erro ao salvar o produto. Por favor, contate o administrador do sistema.");
        }
    }

    //opção 2
    private static void atualizar() {
        System.out.println("\n++++++ Alterar um Produto ++++++");
        Produto produto;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do produto (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else {
                produto = daoProduto.selectById(codigo);
                if (produto == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println("Nome: " + produto.getNome());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.println("Digite o novo nome do produto: ");
                        produto.setNome(input.nextLine());
                    }
                    System.out.println("Descrição: " + produto.getDescricao());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Digite a nova descrição do produto: ");
                        produto.setDescricao(input.next());
                    }
                    System.out.println("Valor: " + produto.getValor());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Digite o novo valor do produto: ");
                        produto.setValor(input.nextDouble());
                        input.nextLine();
                    }
                    System.out.println("Estoque: " + produto.getEstoque());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Digite o novo estoque do produto: ");
                        produto.setEstoque(input.nextInt());
                        input.nextLine();
                    }
                    produto.setSituacao(true);
                    if (daoProduto.begin()
                        .update(produto)
                        .commit()) {
                        System.out.println("produto atualizado:" + produto);
                    } else {
                        System.out.println("Erro ao tentar atualizar o produto. Por favor, contate o adminstrador.");
                    }
                    opcao = 1;
                }
            }
        } while (opcao != 0);
    }

    private static void excluir() {
        System.out.println("\n++++++ Excluir um Produto ++++++");
        Produto produto;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do produto (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else if(codigo > 0){
                produto = daoProduto.selectById(codigo);
                if (produto == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println(produto);
                    System.out.print("Excluir este produto? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Tem certeza disso? (0-sim/1-não) ");
                        produto.setSituacao(false);
                        input.nextLine();
                        if (daoProduto.begin()
                            .update(produto)
                            .commit()) {
                            System.out.println("Produto excluído com sucesso:" + produto);
                        } else {
                            System.out.println("Erro ao tentar excluir o produto. Por favor, contate o adminstrador.");
                        }
                    }

                }
            } else {
                System.out.println("Digite um código válido!!!");
            }
        } while (opcao != 0);
    }

    //opção 3
    private static void selectProdutos() {
        System.out.println("\nLista de produtos cadastrados no banco de dados:\n" + daoProduto.selectAll());
    }

    //opção 4
    private static void selectProdutosById() {
        System.out.print("\nDigite o código do produto: ");
        Produto produto = daoProduto.selectById(input.nextLong());
        input.nextLine();
        if (produto != null) {
            System.out.println(produto);
        } else {
            System.out.println("Código não localizado.");
        }
    }

    //opção 5
    private static void selectProdutosByNome() {
        System.out.print("Digite o nome do produto: ");
        String nome = input.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Produto> produtos = daoProduto.selectByName(nome);
        if (produtos.isEmpty()) {
            System.out.println("Não há registros correspondentes para: " + nome);
        } else {
            System.out.println(produtos);
        }
    }

    //opção 6
    private static void selectProdutosBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Produto> produtos;
        switch (situacao) {
            case 0 -> {
                produtos = daoProduto.selectBySituacao(false);
                System.out.println("Produtos na situação INATIVO:\n " + produtos);
            }
            case 1 -> {
                produtos = daoProduto.selectBySituacao(true);
                System.out.println("Produtos na situação ATIVO:\n " + produtos);
            }
        }
    }

}//fim classe
