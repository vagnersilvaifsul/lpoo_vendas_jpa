package model;

import javax.persistence.*;
import java.text.NumberFormat;

@Entity
@Table(name = "produtos")
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Double valor;
	private Integer estoque;
	private Boolean situacao;
}
