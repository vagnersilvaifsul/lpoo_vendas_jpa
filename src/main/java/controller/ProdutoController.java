package control;

import java.util.List;
import java.util.Scanner;

import dao.ProdutoDAO;
import model.Produto;

public class ProdutoController {

	private static final Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		
		int opcao = 0;
		do {
			System.out.print("\n\"-------  MENU PRODUTO -------\"");
			System.out.print(		
				"\n1. Inserir novo produto" +
				"\n2. Atualizar um produto" +
				"\n3. Listar todos os produtos" +
				"\n4. Buscar produto pelo código" +
				"\n5. Buscar produtos pelo nome" +
				"\n6. Buscar produtos pela situação" +
				"\nOpção (Zero p/sair): ");
			opcao = input.nextInt();
			input.nextLine();
			switch(opcao) {
				case 1:
					inserir();
					break;
				case 2:
					atualizar();
					break;
				case 3:
					selectProdutos();
					break;
				case 4:
					selectProdutosById();
					break;
				case 5:
					selectProdutosByNome();
					break;
				case 6:
					selectProdutosBySituacao();
					break;
				default:
					if(opcao != 0) System.out.println("Opção inválida.");
			}
		} while(opcao != 0) ;	
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
        if(ProdutoDAO.insertProduto(produto)) {
        	System.out.println("\nProduto salvo com sucesso.");
        }else {
        	System.out.println("\nHouve um erro ao salvar o produto. Por favor, contate o administrador do sistema.");
        }     
    }
	
	//opção 2
	private static void atualizar() {
		System.out.println("\n++++++ Alterar um Produto ++++++");
		Produto produto = null;
        int opcao = 0;
        do{
            System.out.print("\nDigite o código do produto (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if(codigo == 0) {
            	opcao = 0;
            } else {
                produto = ProdutoDAO.selectProdutoById(codigo);
                if(produto == null){
                    System.out.println("Código inválido.");
                }else{
                    System.out.println("Nome: " + produto.getNome());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if(input.nextInt() == 0){
                    	input.nextLine();
                        System.out.println("Digite o novo nome do produto: ");
                        produto.setNome(input.nextLine());
                    }
                    System.out.println("Descrição: " + produto.getDescricao());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if(input.nextInt() == 0){
                    	input.nextLine();
                        System.out.print("Digite a nova descrição do produto: ");
                        produto.setDescricao(input.next());
                    }
                    System.out.println("Valor: " + produto.getValor());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if(input.nextInt() == 0){
                    	input.nextLine();
                        System.out.print("Digite o novo valor do produto: ");
                        produto.setValor(input.nextDouble());
                        input.nextLine();
                    }
                    System.out.println("Estoque: " + produto.getEstoque());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if(input.nextInt() == 0){
                    	input.nextLine();
                        System.out.print("Digite o novo estoque do produto: ");
                        produto.setEstoque(input.nextInt());
                        input.nextLine();
                    }
                    produto.setSituacao(true);
                    if(ProdutoDAO.updateProduto(produto)){
                        System.out.println("produto salvo:" + produto);
                    }else{
                        System.out.println("Erro ao tentar salvar o produto. Por favor, contate o adminstrador.");
                    }
                    opcao = 1;
                }

            }
        }while(opcao != 0); 
	}
	
	//opção 3
	private static void selectProdutos() {
		System.out.println("\nLista de produtos cadastrados no banco de dados:\n" + ProdutoDAO.selectProdutos());
	}
	
	//opção 4
	private static void selectProdutosById() {
		System.out.print("\nDigite o código do produto: ");
        Produto produto = ProdutoDAO.selectProdutoById(input.nextLong());
        input.nextLine();
        if(produto != null){
            System.out.println(produto);
        }else{
            System.out.println("Código não localizado.");
        }
	}
	
	//opção 5
	private static void selectProdutosByNome() {
        System.out.print("Digite o nome do produto: ");
        String nome = input.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Produto> produtos = ProdutoDAO.selectProdutosByName(nome);
        if(produtos.isEmpty()){
            System.out.println("Não há registros correspondentes para: " + nome);
        }else{
            System.out.println(produtos);
        }
    }
	
	//opção 6
	private static void selectProdutosBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Produto> produtos;
        switch(situacao) {
        	case 0:
        		produtos = ProdutoDAO.selectProdutosBySituacao(false);
        		System.out.println("Produtos na situação INATIVO:\n " + produtos);
        		break;
        	case 1:
        		produtos = ProdutoDAO.selectProdutosBySituacao(true);
        		System.out.println("Produtos na situação ATIVO:\n " + produtos);
        		break;	
        }
    }

}//fim classe
