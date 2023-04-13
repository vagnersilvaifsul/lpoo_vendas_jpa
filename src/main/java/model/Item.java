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
	private Produto produto;
	@ManyToOne
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
