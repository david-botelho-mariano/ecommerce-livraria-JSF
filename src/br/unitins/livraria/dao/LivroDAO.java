package br.unitins.livraria.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.livraria.application.Util;
import br.unitins.livraria.model.Livro;

public class LivroDAO extends DAO<Livro> {

	@Override
	public boolean create(Livro obj) {
		boolean result = false;

		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}

		PreparedStatement stat = null;
		try {
			stat = getConnection()
					.prepareStatement("INSERT INTO livro(nome, autor, editora, valor)" + "VALUES (?, ?, ?, ?)");
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getAutor());
			stat.setString(3, obj.getEditora());
			stat.setDouble(4, obj.getValor());

			stat.execute();
			Util.addMessageError("Cadastro realizado com sucesso!");
			result = true;
		} catch (SQLException e) {
			Util.addMessageError("Falha ao incluir.");
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public boolean update(Livro obj) {
		boolean result = false;

		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}

		PreparedStatement stat = null;
		try {
			stat = getConnection()
					.prepareStatement("UPDATE livro SET nome = ?, autor = ?, editora = ?, valor = ? WHERE id = ?");
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getAutor());
			stat.setString(3, obj.getEditora());
			stat.setDouble(4, obj.getValor());
			stat.setInt(5, obj.getId());

			stat.execute();
			Util.addMessageError("Alteracao realizada com sucesso!");
			result = true;
		} catch (SQLException e) {
			Util.addMessageError("Falha ao Alterar.");
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	@Override
	public boolean delete(int id) {
		boolean result = false;

		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}

		PreparedStatement stat = null;
		try {
			stat = getConnection().prepareStatement("DELETE FROM livro WHERE id = ? ");
			stat.setInt(1, id);

			stat.execute();
			Util.addMessageError("Exclusao realizada com sucesso!");
			result = true;
		} catch (SQLException e) {
			Util.addMessageError("Falha ao Excluir.");
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Livro findById(int id) {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}
		Livro livro = null;

		PreparedStatement stat = null;

		try {
			stat = getConnection().prepareStatement("SELECT * FROM livro WHERE id = ?");
			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setNome(rs.getString("nome"));
				livro.setAutor(rs.getString("autor"));
				livro.setEditora(rs.getString("editora"));
				livro.setValor(rs.getDouble("valor"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Util.addMessageError("Falha ao consultar o Banco de Dados.");
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return livro;
	}

	@Override
	public List<Livro> findAll() {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}

		List<Livro> listLivro = new ArrayList<Livro>();

		PreparedStatement stat = null;

		try {
			stat = getConnection().prepareStatement("SELECT * FROM Livro");
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				Livro livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setNome(rs.getString("nome"));
				livro.setAutor(rs.getString("autor"));
				livro.setEditora(rs.getString("editora"));
				livro.setValor(rs.getDouble("valor"));

				listLivro.add(livro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Util.addMessageError("Falha ao consultar o Banco de Dados.");
			listLivro = null;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listLivro;
	}

	public List<Livro> findByName(String name) {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}

		List<Livro> listLivro = new ArrayList<Livro>();

		PreparedStatement stat = null;

		try {
			stat = getConnection().prepareStatement("SELECT * FROM Livro WHERE nome ILIKE ?");
			stat.setString(1, (name == null ? "%" : "%" + name + "%"));
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Livro livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setNome(rs.getString("nome"));
				livro.setAutor(rs.getString("autor"));
				livro.setEditora(rs.getString("editora"));
				livro.setValor(rs.getDouble("valor"));

				listLivro.add(livro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Util.addMessageError("Falha ao consultar o Banco de Dados.");
			listLivro = null;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listLivro;
	}
}