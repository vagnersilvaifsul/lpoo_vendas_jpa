package controller;

import dao.DAO;
import model.Cliente;
import model.Item;
import model.Pedido;
import model.Produto;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //consultas
//        DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
//        System.out.println(daoCliente.selectAll());
//        System.out.println(daoCliente.selectById(1L));
//
//        DAO<Produto> daoProduto = new DAO<>(Produto.class);
//        System.out.println(daoProduto.selectAll());
//        System.out.println(daoProduto.selectById(1L));

//        DAO<Pedido> daoPedido = new DAO<>(Pedido.class);
//        System.out.println(daoPedido.selectAll());
//        System.out.println(daoPedido.selectById(25L));

        DAO<Pedido> daoPedido = new DAO<>(Pedido.class);
        System.out.println("Pedido com seus itens");
//        System.out.println(daoItem.selectAll());
        System.out.println(daoPedido.selectById(25L));

        DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
        System.out.println(daoCliente.selectPedidosByCliente(2L));

        //isso cria a base de dados no SGBD utilizando o Hybernate (JPA)
//        Cliente cliente1 = new Cliente(1L, "Ana", "Silva", true);
//        Cliente cliente2 = new Cliente(null, "Rafael", "Torres", true);
//        DAO<Cliente> daoCliente = new DAO<>();
//        daoCliente.begin()
//            .create(cliente1)
//            .create(cliente2)
//            .commit();

//        Produto produto1 = new Produto(1L, "Arroz", "Ceolin tipo 1 5kg", 16.90, 200, true);
//        Produto produto2 = new Produto(2L, "Feijão", "Tordilho tipo 1 1kg", 7.90, 500, true);
//        DAO<Produto> daoProduto = new DAO<>();
//        daoProduto.begin()
//            .create(produto1)
//            .create(produto2)
//            .commit();

        /*
            Transação. Ou tudo acontece, ou cancela tudo.
         */
        //Objetos do controller
//        Item item1 = new Item(null, 5, produto1.getValor() * 5, true, produto1, null);
//        Item item2 = new Item(null, 10, produto2.getValor() * 10, true, produto2, null);
//        Pedido pedido = new Pedido(null, "visa débito", "aberto", LocalDate.now(), LocalDate.now(), item1.getTotalItem() + item2.getTotalItem(), true, cliente1);
//        DAO<Pedido> daoPedido = new DAO<>();
//        daoPedido.begin()
//            .create(pedido)
//            .commit();
//        System.out.println(pedido.getId());
//        if(pedido.getId() != null) {
//            item1.setPedido(pedido);
//            item2.setPedido(pedido);
//            System.out.println(item1);
//            System.out.println(item2);
//            DAO<Item> daoItem = new DAO<>();
//            daoItem.begin()
//                .create(item1)
//                .create(item2)
//                .commit();
//        }
        /*
            Fim da transação.
         */


    }
}
