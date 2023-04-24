package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clientes")
@NamedQueries({
	@NamedQuery(name="Cliente.buscarTodos", query="select c from Cliente c"),
	@NamedQuery(name="Cliente.buscarResultSetById", query = "select p from Cliente c inner join Pedido p on c.id = p.cliente.id where c.id = : id"),
	@NamedQuery(name="Cliente.buscarPeloNome", query="select c from Cliente c where c.nome like :nome"),
	@NamedQuery(name="Cliente.buscarPelaSituacao", query="select c from Cliente c where c.situacao = :situacao")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sobrenome;
	private Boolean situacao;
	@OneToMany(mappedBy = "cliente")
	List<Pedido> pedidos;


	@Override
	public String toString() {
		return "\nCliente{" +
			"id=" + id +
			", nome='" + nome + '\'' +
			", sobrenome='" + sobrenome + '\'' +
			", situacao=" + situacao +
			'}';
	}
}
