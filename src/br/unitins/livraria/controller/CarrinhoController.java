package br.unitins.livraria.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.livraria.application.Session;
import br.unitins.livraria.application.Util;
import br.unitins.livraria.dao.LivroDAO;
import br.unitins.livraria.dao.VendaDAO;
import br.unitins.livraria.model.ItemVenda;
import br.unitins.livraria.model.Livro;
import br.unitins.livraria.model.Usuario;
import br.unitins.livraria.model.Venda;

@Named
@RequestScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = -4131331164022656670L;

	private Venda venda;

	public void remover(String livro) {

		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		for (ItemVenda itemVenda : carrinho) {

			if (itemVenda.getLivro().getNome().equals(livro)) {
				carrinho.remove(itemVenda);
				return;
			}
		}
	}

	public void finalizar() {

		Usuario user = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
		getVenda().setCliente(user.getNome());

		getVenda().setUsuario((Usuario) Session.getInstance().getAttribute("usuarioLogado"));
		VendaDAO dao = new VendaDAO();
		dao.create(getVenda());

		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		carrinho.clear();
	}

	public Venda getVenda() {
		if (venda == null) {
			venda = new Venda();
		}
		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		if (carrinho != null)
			venda.setListaItemVenda(carrinho);
		else
			venda.setListaItemVenda(new ArrayList<ItemVenda>());

		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public void adicionar(int id) {
		// pesquisa o livro selecionado
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

		Util.addMessageError("Adicionado com Sucesso!");
	}
}