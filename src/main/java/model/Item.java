package model;

import jdk.jfr.MemoryAddress;
import lombok.Data;

import javax.persistence.*;
import java.text.NumberFormat;

@Entity
@Table(name = "itens")
@Data
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
}
