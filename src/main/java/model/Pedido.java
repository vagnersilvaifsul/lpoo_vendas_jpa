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
	@NamedQuery(name="Pedido.buscarTodos", query="select p from Pedido p")
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
	
}
