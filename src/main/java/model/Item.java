package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "itens")
@NamedQueries({
	@NamedQuery(name="Item.buscarTodos", query="select i from Item i"),
	@NamedQuery(name="Item.buscarResultSetById", query = "select i from Item i where i.pedido.id = : id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantidade;
	private Double totalItem;
	private Boolean situacao;
	@ManyToOne
	@JoinColumn(name = "produto_id", referencedColumnName = "id")
	private Produto produto;
	@ManyToOne
	@JoinColumn(name = "pedido_id", referencedColumnName = "id")
	private Pedido pedido;


	public Item(Produto produto){
		this.produto = produto;
	}

	@Override
	public String toString() {
		return "\nItem{" +
			"id=" + id +
			", quantidade=" + quantidade +
			", totalItem=" + totalItem +
			", situacao=" + situacao +
			", produto=" + produto +
			'}';
	}
}
