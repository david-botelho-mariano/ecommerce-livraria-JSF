package br.unitins.livraria.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.livraria.application.Session;
import br.unitins.livraria.application.Util;
import br.unitins.livraria.dao.LivroDAO;
import br.unitins.livraria.model.ItemVenda;
import br.unitins.livraria.model.Livro;

@Named
@ViewScoped
public class VendaLivroController implements Serializable {

	private static final long serialVersionUID = 5537729380314258216L;

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

	public void adicionar(int id) {
		// pesquisa o servico selecionado
		LivroDAO dao = new LivroDAO();
		Livro livro = dao.findById(id);

		// verifica se existe o carrinho na sessao
		if (Session.getInstance().getAttribute("carrinho") == null) {
			// adiciona o carrinho na sessao
			Session.getInstance().setAttribute("carrinho", new ArrayList<ItemVenda>());
		}
		// busca o carrinho da sessao
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		// cria um item de venda
		ItemVenda item = new ItemVenda();
		item.setLivro(livro);
		item.setValor(livro.getValor());

		// adiciona o item no objeto de referencia do carrinho
		carrinho.add(item);

		// atualiza o carrinho
		Session.getInstance().setAttribute("carrinho", carrinho);

		Util.addMessageError("Adicionado com Sucesso! Quantidade de itens: " + carrinho.size());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}