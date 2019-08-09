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
import br.unitins.livraria.model.Livro;

@Named
@ViewScoped
public class LivroController implements Serializable {

	private static final long serialVersionUID = 8171417293031286615L;

	private Livro livro;

	private List<Livro> listaLivro = null;

	public LivroController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		livro = (Livro) flash.get("livroFlash");
	}

	public List<Livro> getListLivro() {
		if (listaLivro == null) {
			LivroDAO dao = new LivroDAO();
			listaLivro = dao.findAll();
			if (listaLivro == null)
				listaLivro = new ArrayList<Livro>();
			dao.closeConnection();
		}

		return listaLivro;
	}

	public void editar(int id) {
		LivroDAO dao = new LivroDAO();
		setLivro(dao.findById(id));
	}

	public void incluir() {
		LivroDAO dao = new LivroDAO();
		if (dao.create(getLivro())) {
			limpar();
			// para atualizar o data table
			listaLivro = null;
		}
		dao.closeConnection();
	}

	public void alterar() {
		LivroDAO dao = new LivroDAO();
		if (dao.update(getLivro())) {
			limpar();
			// para atualizar o data table
			listaLivro = null;
		}
		dao.closeConnection();
	}

	public void excluir(int id) {
		LivroDAO dao = new LivroDAO();
		if (dao.delete(id)) {
			limpar();
			// para atualizar o data table
			listaLivro = null;
		}
		dao.closeConnection();
	}

	public void limpar() {
		livro = null;
	}

	public Livro getLivro() {
		if (livro == null)
			livro = new Livro();
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void goback() {
		Util.redirect("consultfilm.xhtml");
	}
}