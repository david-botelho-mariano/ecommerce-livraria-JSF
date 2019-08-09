package br.unitins.livraria.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.livraria.application.Util;
import br.unitins.livraria.dao.UsuarioDAO;
import br.unitins.livraria.model.Usuario;

@Named
@ViewScoped
public class ConsultaUsuarioController implements Serializable {

	private static final long serialVersionUID = -4762693472692597449L;

	private String name;

	private List<Usuario> listaUsuario = null;

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			UsuarioDAO dao = new UsuarioDAO();
			listaUsuario = dao.findByNome(getName());
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
			dao.closeConnection();
		}

		return listaUsuario;
	}

	public void pesquisar() {
		listaUsuario = null;
	}

	public void editar(int id) {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = dao.findById(id);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("usuarioFlash", usuario);
		Util.redirect("cadastrofuncionario.xhtml");
	}

	public void cadastrarFuncionario() {
		Util.redirect("cadastrofuncionario.xhtml");
	}

	public void excluir(int id) {
		UsuarioDAO dao = new UsuarioDAO();
		dao.delete(id);
		listaUsuario = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void redirectClientRegister() {
		Util.redirect("cliente.xhtml");
	}
}