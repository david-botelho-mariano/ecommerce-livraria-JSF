package br.unitins.livraria.model;

import javax.validation.constraints.NotBlank;

public class Livro {
	private Integer id;

	@NotBlank(message = "O nome do Livro deve ser informado.")
	private String nome;

	@NotBlank(message = "O nome do Autor deve ser informado.")
	private String autor;

	@NotBlank(message = "O nome da Editora deve ser informado.")
	private String editora;

	private Double valor;

	public Livro() {
	}

	public Livro(Integer id, String nome, String autor, String editora) {
		super();
		this.id = id;
		this.nome = nome;
		this.autor = autor;
		this.editora = editora;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}