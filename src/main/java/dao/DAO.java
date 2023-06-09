package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class  DAO<E> {

    private static final EntityManagerFactory emf;
    private final EntityManager em;
    private final Class<E> entidade;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("lpoo_vendas_jpa");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public DAO(Class<E> entidade) {
        this.entidade = entidade;
        em = emf.createEntityManager();
    }

    public DAO<E> begin() {
        em.getTransaction().begin();
        return this;
    }

    public boolean commit() {
        try{
            em.getTransaction().commit();
            return true;
        } catch (Exception e){
            System.err.println("Problemas com a transação: " + e);
            em.getTransaction().rollback();
            return false;
        }
    }

    public DAO<E> insert(E entidade) {
        em.persist(entidade);
        return this;
    }

    public DAO<E> update(E entidade){
        em.merge(entidade);
        return this;
    }

    public List selectAll() {
        //return em.createQuery("from Carro").getResultList();
        return em.createNamedQuery(entidade.getSimpleName() + ".buscarTodos").getResultList();
    }

    public E selectById(Object id) {
        return em.find(entidade, id);
    }

    public List selectByName(String nome){
        return em.createNamedQuery(entidade.getSimpleName() + ".buscarPeloNome")
            .setParameter("nome", nome + "%")
            .getResultList();
    }

    public List selectBySituacao(boolean situacao){
        return em.createNamedQuery(entidade.getSimpleName() + ".buscarPelaSituacao")
            .setParameter("situacao", situacao)
            .getResultList();
    }

    public List selectResultSetById(Object id){
        return em.createNamedQuery(entidade.getSimpleName() + ".buscarResultSetById")
            .setParameter("id", id)
            .getResultList();
    }

}
