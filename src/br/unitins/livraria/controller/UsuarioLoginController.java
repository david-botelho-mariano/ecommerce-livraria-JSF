package br.unitins.livraria.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.livraria.application.Util;
import br.unitins.livraria.dao.UsuarioLoginDAO;
import br.unitins.livraria.model.Perfil;
import br.unitins.livraria.model.Usuario;

@Named
@ViewScoped
public class UsuarioLoginController implements Serializable {

	private static final long serialVersionUID = 698776264788143042L;

	private Usuario usuario;

	private List<Usuario> listaUsuario = null;

	public UsuarioLoginController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		usuario = (Usuario) flash.get("usuarioFlash");
	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			UsuarioLoginDAO dao = new UsuarioLoginDAO();
			listaUsuario = dao.findAll();
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
			dao.closeConnection();
		}

		return listaUsuario;
	}

	public void incluir() {
		// encriptando a senha do usuario
		getUsuario().setSenha(Util.encrypt(getUsuario().getSenha()));

		UsuarioLoginDAO dao = new UsuarioLoginDAO();
		if (dao.create(getUsuario())) {
			limpar();
			// para atualizar o data table
			listaUsuario = null;
		}
		dao.closeConnection();
	}

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}

	public void limpar() {
		usuario = null;
	}

	public void redirect() {
		Util.redirect("login.xhtml");
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
}