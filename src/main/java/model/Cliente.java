package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
@NamedQueries({
	@NamedQuery(name="Cliente.buscarTodos", query="select c from Cliente c"),
	@NamedQuery(name="Cliente.buscarPedidos", query = "select p from Cliente c inner join Pedido p on c.id = p.cliente.id where c.id = : id")
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

//	@OneToMany
//	@JoinColumn(name="cliente_id", referencedColumnName="id")
//	List<Pedido> pedidos;


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
