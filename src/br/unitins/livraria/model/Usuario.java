package br.unitins.livraria.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Usuario {
	private Integer id;

	@NotBlank(message = "O nome deve ser informado.")
	private String nome;

	@Email(message = "Email invalido.")
	private String login;

	private String senha;

	private LocalDate dataNascimento;

	private Perfil perfil;

	public Usuario() {
	}

	public Usuario(Integer id, String nome, String login, String senha, Perfil perfil) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.perfil = perfil;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
