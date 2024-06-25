package dao;

import java.util.*;
import java.sql.*;

import model.Produto;

public class ProdutoDAO extends BaseDAO {
	public static List<Produto> selectProdutos() {
		final String sql = "SELECT * FROM produtos";
		try (Connection conn = getConnection(); // conexao, que usamos em base
				PreparedStatement pstnt = conn.prepareStatement(sql); // pegamos objeto de conexao com o bd e damos um
																		// prepare statement
				ResultSet rs = pstnt.executeQuery(); // pegamos o statement que queremos executar no banco e executamos
														// a query
		) {
			List<Produto> produtos = new ArrayList<>();
			while (rs.next()) { // varremos com while enquanto o proximo nao for nulo e equanto for possivel
								// vamos converter usando o resultSet
				produtos.add(resultsetToProduto(rs));
			}
			return produtos;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Produto> selectProdutoByName(String nome) {
		final String sql = "SELECT * FROM produtos WHERE nome LIKE ? ORDER BY nome"; // o ? pertence ao prepared
		try (
				Connection conn = getConnection();
				PreparedStatement pstnt = conn.prepareStatement(sql);) {
			pstnt.setString(1, nome.toLowerCase() + "%");
			ResultSet rs = pstnt.executeQuery();
			List<Produto> p = new ArrayList<>();
			while (rs.next()) { // varremos com while enquanto o proximo nao for nulo e equanto for possivel
				p.add(resultsetToProduto(rs));
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null; // deve restornar o null aqui e o produto no try
		}
	}

	public static List<Produto> selectProdutoBySituacao(Boolean situacao) {
		final String sql = "SELECT * FROM produtos WHERE situacao=?"; // o ? pertence ao prepared
		try (Connection conn = getConnection(); PreparedStatement pstnt = conn.prepareStatement(sql);) {
			pstnt.setBoolean(1, situacao);
			ResultSet rs = pstnt.executeQuery();
			List<Produto> p = new ArrayList<>();
			while (rs.next()) { // varremos com while enquanto o proximo nao for nulo e equanto for possivel
								// vamos converter usando o resultSet
				p.add(resultsetToProduto(rs));
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null; // deve restornar o null aqui e o produto no try
		}
	}

	public static Produto selectProdutoById(Long idProduto) {
		final String sql = "SELECT * FROM produtos WHERE id=?"; // o ? pertence ao prepared statement e conforme for
																// executado sera substituido pelo valor q chegar pela
																// pprta de enrada do metodo7
		try (
				Connection conn = getConnection();
				PreparedStatement pstnt = conn.prepareStatement(sql);) {
			pstnt.setLong(1, idProduto);
			ResultSet rs = pstnt.executeQuery();
			// Produto p = new Produto(); contrtuo o objeto de forma padrao
			Produto p = null; // sempre retorna o null quando não houver registro
			if (rs.next()) {
				p = resultsetToProduto(rs);
			}
			rs.close();// liberamos o recurso
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null; // deve restornar o null aqui e o produto no try
		}
	}

	public static boolean insertProduto(Produto produto) {
		final String sql = "INSERT INTO produtos (nome, descricao, valor, situacao, quantidade) VALUES (?, ?, ?, ?, ?)";
		try (
				Connection conn = getConnection();
				PreparedStatement pstnt = conn.prepareStatement(sql);) {
			pstnt.setString(1, produto.getNome());
			pstnt.setString(2, produto.getDescricao());
			pstnt.setDouble(3, produto.getValor());
			pstnt.setBoolean(4, produto.getSituacao());
			pstnt.setInt(5, produto.getQuantidade());
			int count = pstnt.executeUpdate();

			return count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateProduto(Produto produto) {
		final String sql = "UPDATE produtos SET nome=?, descricao=?, valor=?, situacao=?, quantidade=? WHERE id=?";
		try (
				Connection conn = getConnection();
				PreparedStatement pstnt = conn.prepareStatement(sql);) {
			pstnt.setString(1, produto.getNome());
			pstnt.setString(2, produto.getDescricao());
			pstnt.setDouble(3, produto.getValor());
			pstnt.setBoolean(4, produto.getSituacao());
			pstnt.setInt(5, produto.getQuantidade());
			pstnt.setLong(6, produto.getIdProduto());
			int count = pstnt.executeUpdate();

			return count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean SoftDeleteProduto(long id, boolean situacao) {
		final String sql = "UPDATE produtos SET situacao=? WHERE id=?";
		try (
				Connection conn = getConnection();
				PreparedStatement pstnt = conn.prepareStatement(sql);) {
			pstnt.setBoolean(1, situacao);

			pstnt.setLong(2, id);
			int count = pstnt.executeUpdate();

			return count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static Produto resultsetToProduto(ResultSet rs) throws SQLException { // faz a conversao do produto
		// colocamos um throws pois ja usamos o try catch acima e ele ja faz o
		// tratamento das execoes
		Produto p = new Produto();
		p.setIdProduto(rs.getLong("id"));
		p.setNome(rs.getString("nome"));
		p.setValor(rs.getDouble("valor"));
		p.setDescricao(rs.getString("descricao"));
		p.setSituacao(rs.getBoolean("situacao"));
		p.setQuantidade(rs.getInt("quantidade"));

		return p;
	}

	public static void main(String[] args) {
		// System.out.println(selectProdutos());
		// System.out.println(selectProdutoByName("chá"));
		// System.out.println(selectProdutoBySituacao(true));
		// Produto produto = new Produto(0L, "Corote", "Sabor morango 500ml", 5.6, true,
		// 60);
		// System.out.println(insertProduto(produto));
		// Produto produto = new Produto(6L, "Corote", "Sabor morango 500ml", 5.6, true,
		// 58);
		// System.out.println(updateProduto(produto));
		System.out.println(SoftDeleteProduto(6L, true));
	}

}
