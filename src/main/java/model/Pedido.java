package model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
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
	
}
