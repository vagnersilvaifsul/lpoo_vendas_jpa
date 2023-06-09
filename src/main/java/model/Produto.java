package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "produtos")
@NamedQueries({
	@NamedQuery(name="Produto.buscarTodos", query="select p from Produto p"),
	@NamedQuery(name="Produto.buscarPeloNome", query="select p from Produto p where p.nome like :nome"),
	@NamedQuery(name="Produto.buscarPelaSituacao", query="select p from Produto p where p.situacao = :situacao")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Double valor;
	private Integer estoque;
	private Boolean situacao;

	@Override
	public String toString() {
		return "\nProduto{" +
			"id=" + id +
			", nome='" + nome + '\'' +
			", descricao='" + descricao + '\'' +
			", valor=" + valor +
			", estoque=" + estoque +
			", situacao=" + situacao +
			'}';
	}
}
