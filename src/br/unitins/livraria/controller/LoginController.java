package br.unitins.livraria.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.livraria.application.Session;
import br.unitins.livraria.application.Util;
import br.unitins.livraria.dao.UsuarioDAO;
import br.unitins.livraria.model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;

	public void entrar() {
		UsuarioDAO dao = new UsuarioDAO();
		// gerando o hash da senha informada na tela de login
		String senhaEncriptada = Util.encrypt(getUsuario().getSenha());

		Usuario usuLogado = dao.findUsuario(getUsuario().getLogin(), senhaEncriptada);

		// comparando os dados da tela de login com o banco de dados
		if (usuLogado != null) {
			Session.getInstance().setAttribute("usuarioLogado", usuLogado);
			// login valido
			Util.redirect("template.xhtml");
		} else
			Util.addMessageError("Usuario ou senha invalido.");

	}

	public void limpar() {
		setUsuario(null);
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void fazerCadastro() {
		Util.redirect("cadastrocliente.xhtml");
	}
}