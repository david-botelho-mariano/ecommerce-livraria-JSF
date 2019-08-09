package br.unitins.livraria.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.livraria.application.Util;
import br.unitins.livraria.dao.LivroDAO;
import br.unitins.livraria.dao.UsuarioDAO;
import br.unitins.livraria.model.Livro;

@Named
@ViewScoped
public class ConsultaLivroController implements Serializable {

	private static final long serialVersionUID = -5978894321765992515L;

	private String nome;

	private List<Livro> listaLivro = null;

	public List<Livro> getListaLivro() {
		if (listaLivro == null) {
			LivroDAO dao = new LivroDAO();
			listaLivro = dao.findByName(getNome());
			if (listaLivro == null)
				listaLivro = new ArrayList<Livro>();
			dao.closeConnection();
		}
		return listaLivro;
	}

	public void pesquisar() {
		listaLivro = null;
	}

	public void editar(int id) {
		LivroDAO dao = new LivroDAO();
		Livro livro = dao.findById(id);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("livroFlash", livro);
		Util.redirect("cadastrarlivro.xhtml");
	}

	public void excluir(int id) {
		LivroDAO dao = new LivroDAO();
		dao.delete(id);
		listaLivro = null;
	}

	public void cadastrarLivro() {
		Util.redirect("cadastrarlivro.xhtml");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void redirectLivro() {
		Util.redirect("livro.xhtml");
	}
}