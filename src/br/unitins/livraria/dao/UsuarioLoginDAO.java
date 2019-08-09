package br.unitins.livraria.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.livraria.application.Util;
import br.unitins.livraria.model.Perfil;
import br.unitins.livraria.model.Usuario;

public class UsuarioLoginDAO extends DAO<Usuario> {

	@Override
	public boolean create(Usuario obj) {
		boolean resultado = false;

		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}

		PreparedStatement stat = null;
		try {
			stat = getConnection().prepareStatement("INSERT INTO usuario ( " + " nome, " + " login, " + " senha, "
					+ " datanascimento," + " perfil ) " + "VALUES ( " + " ?, " + " ?, " + " ?, " + " ?," + " 1 ) ");
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getLogin());
			stat.setString(3, obj.getSenha());
			stat.setDate(4, (obj.getDataNascimento() == null ? null : java.sql.Date.valueOf(obj.getDataNascimento())));
			stat.execute();
			Util.addMessageError("Cadastro realizado com sucesso!");
			resultado = true;
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
		return resultado;
	}

	@Override
	public Usuario findById(int id) {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}
		Usuario usuario = null;

		PreparedStatement stat = null;

		try {
			stat = getConnection().prepareStatement("SELECT * FROM usuario WHERE id = ?");
			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setDataNascimento(
						rs.getDate("datanascimento") == null ? null : (rs.getDate("data_nascimento").toLocalDate()));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
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
		return usuario;
	}

	@Override
	public List<Usuario> findAll() {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}

		List<Usuario> listaUsuario = new ArrayList<Usuario>();

		PreparedStatement stat = null;

		try {
			stat = getConnection().prepareStatement("SELECT * FROM usuario");
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setDataNascimento(
						rs.getDate("datanascimento") == null ? null : (rs.getDate("data_nascimento").toLocalDate()));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				listaUsuario.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Util.addMessageError("Falha ao consultar o Banco de Dados.");
			listaUsuario = null;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaUsuario;

	}

	public List<Usuario> findByNome(String nome) {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}

		List<Usuario> listaUsuario = new ArrayList<Usuario>();

		PreparedStatement stat = null;

		try {
			stat = getConnection().prepareStatement("SELECT * FROM usuario WHERE nome ILIKE ?");
			stat.setString(1, (nome == null ? "%" : "%" + nome + "%"));
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setDataNascimento(
						rs.getDate("datanascimento") == null ? null : (rs.getDate("data_nascimento").toLocalDate()));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				listaUsuario.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Util.addMessageError("Falha ao consultar o Banco de Dados.");
			listaUsuario = null;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaUsuario;
	}

	@Override
	public boolean update(Usuario obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
