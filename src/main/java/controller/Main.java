package controller;

import dao.DAO;
import model.Cliente;

public class main {
    public static void main(String[] args) {
        //isso cria a base de dados no SGBD utilizando o Hybernate (JPA)
        DAO<Cliente> daoCliente = new DAO<>(Cliente.class);
    }
}
