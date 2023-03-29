package model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "clientes")
@Data
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sobrenome;
	private Boolean situacao;

}
