package controller;

import dao.DAO;
import model.Cliente;
import model.Item;
import model.Pedido;
import model.Produto;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendasController {
	
	private static final Scanner input = new Scanner(System.in);
    private static double totalPedido;

    private static final DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
    private static final DAO<Produto> daoProduto = new DAO<>(Produto.class);

    private static final DAO<Pedido> daoPedido = new DAO<>(Pedido.class);
    private static final DAO<Item> daoItem = new DAO<>(Item.class);

    public static void main(String[] args) {
        int opcao;
        Cliente cliente;
        Produto produto;
        List<Item> itens = new ArrayList<>();
        do {
            opcao = 0;
            System.out.println("\n\n******** Vendas ********");
            System.out.print("Digite o código do cliente: ");
            long codigoCliente = input.nextLong();
            input.nextLine();
            cliente = daoCliente.selectById(codigoCliente);
            if(cliente == null){
                System.out.println("Código inválido");
                opcao = 1;
            }else{
                System.out.println("Cliente selecionado: " + cliente);
                int sair = 2;
                do{
                    System.out.print("Digite o código do produto: ");
                    long codigoProduto = input.nextLong();
                    input.nextLine();
                    produto = daoProduto.selectById(codigoProduto);
                    if(produto == null){
                        System.out.println("Código inválido");
                        sair = 1;
                    }else{
                        System.out.println("Produto selecionado:" + produto);
                        System.out.print("Digite a quantidade: ");
                        int quantidade = input.nextInt();
                        input.nextLine();
                        if(quantidade > produto.getEstoque()){
                            System.out.println("Quantidade inválida.");
                            sair = 1;
                        }else{
                            Item item = new Item(produto);
                            item.setQuantidade(quantidade);
                            item.setTotalItem(quantidade * produto.getValor());
                            item.setSituacao(true);
                            itens.add(item);
                            System.out.println("Produto adionado ao carrinho.");
                            baixarEstoque(item); //baixa o estoque ao adicionar no carrinho
                            System.out.print("\nDeseja vender outro produto (sim-1/não-2)? ");
                            sair = input.nextInt();
                            input.nextLine();
                        }
                    }
                }while(sair != 2);
                if (itens.isEmpty()) {
                    System.out.println("\n******* Seu carrinho está VAZIO*******");
                } else { //se tem itens no carrinho
                    System.out.println("\n******* Seu carrinho *******");
                    totalPedido = 0.0;
                    itens.forEach( i -> { //firula para alinhar as colunas na impressão do carrinho
                        StringBuilder nome = new StringBuilder(i.getProduto().getNome());
                        StringBuilder precoUnitario = new StringBuilder(NumberFormat.getCurrencyInstance().format(i.getProduto().getValor()));
                        int MAX = 20;
                        if(nome.length() <= MAX){
                            nome.append(" ".repeat(Math.max(0, MAX - nome.length())));
                        }
                        if(precoUnitario.length() <= MAX){
                            precoUnitario.append(" ".repeat(Math.max(0, MAX - 5 - precoUnitario.length())));
                        } //fim da firula
                        System.out.println(
                                "\tProduto: " + nome +
                                "\tValor unidade = " +  precoUnitario +
                                "\t\tQuantidade = " + i.getQuantidade() +
                                "\t\tTotalItem = " + (NumberFormat.getCurrencyInstance().format(i.getQuantidade()*i.getProduto().getValor()))
                                );
                        totalPedido += i.getQuantidade() * i.getProduto().getValor();
                    });
                    System.out.println("*************************************\n" + "TOTAL DO PEDIDO = " + NumberFormat.getCurrencyInstance().format(totalPedido));
                    System.out.print("Fechar o pedido?(1-sim/2-não) ");
                    opcao = input.nextInt();
                    input.nextLine();
                    if(opcao == 1){
                        //salva o pedido
                        Pedido pedido = new Pedido(null, "visa débito", "aberto", LocalDate.now(), LocalDate.now(), totalPedido, true, cliente, null);
                        if(daoPedido.begin()
                            .insert(pedido)
                            .commit()){
                            System.out.println("Pedido salvo.");
                            System.out.println(pedido.getId());
                            if(pedido.getId() != null) {
                                itens.forEach(i -> {
                                    i.setPedido(pedido);
                                    daoItem.begin()
                                        .insert(i)
                                        .commit();
                                });
                            }
                        }else{
                            System.out.println("Não foi possível salvar o pedido. Por favor, contate o administrador.");
                        }
                    }else if(opcao == 2){
                        System.out.print("Ops! Tem certeza? Você perderá esse pedido. (sim-1/não-2) ");
                        opcao = input.nextInt();
                        input.nextLine();
                        if(opcao == 1){
                            System.out.println("Pedido cancelado.");
                            //volta o estoque que foi baixado na venda
                            itens.forEach(VendasController::voltarEstoque);
                        }
                    }
                    opcao = 0;
                }
            }
        }while (opcao != 0);
  }

    private static void baixarEstoque(Item item){
        Produto produto = item.getProduto();
        produto.setEstoque(produto.getEstoque() - item.getQuantidade());
        if (daoProduto.begin()
            .update(produto)
            .commit()) {
            System.out.println("estoque atualizado:" + produto);
        } else {
            System.out.println("Erro ao tentar atualizar o estoque. Por favor, contate o adminstrador.");
        }
    }

    private static void voltarEstoque(Item item){
        Produto produto = item.getProduto();
        produto.setEstoque(produto.getEstoque() + item.getQuantidade());
        if (daoProduto.begin()
            .update(produto)
            .commit()) {
            System.out.println("estoque estornado:" + produto);
        } else {
            System.out.println("Erro ao tentar estornar o estoque. Por favor, contate o adminstrador.");
        }
    }
}
