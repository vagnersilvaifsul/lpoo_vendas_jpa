package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
@NamedQueries({
	@NamedQuery(name="Pedido.buscarTodos", query="select p from Pedido p"),
	@NamedQuery(name="Pedido.buscarPedidos", query = "select p from Cliente c inner join Pedido p on c.id = p.cliente.id where c.id = : id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String formaPagamento;
	private String estado;
	private LocalDate dataCriacao;
	private LocalDate dataModificacao;
	private Double totalPedido;
	private Boolean situacao;
	@ManyToOne
	private Cliente cliente;
	@OneToMany
	@JoinColumn(name="pedido_id", referencedColumnName="id")
	List<Item> itens;

	@Override
	public String toString() {
		return "\n\nPedido{" +
			"id=" + id +
			", formaPagamento='" + formaPagamento + '\'' +
			", estado='" + estado + '\'' +
			", dataCriacao=" + dataCriacao +
			", dataModificacao=" + dataModificacao +
			", totalPedido=" + totalPedido +
			", situacao=" + situacao +
			", cliente=" + cliente +
			", itens=" + itens +
			'}';
	}
}
