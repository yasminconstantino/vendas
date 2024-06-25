package dao;

import java.sql.*;
import java.util.*;

import model.Cliente;

public class ClienteDAO extends BaseDAO {
    public static List<Cliente> selectClientes() {
        final String sql = "SELECT * FROM clientes ORDER BY nome";
        try // try-witch-resource
        (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();) {
            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                clientes.add(resultsetToCliente(rs));
            }
            return clientes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Cliente> selectClientesByName(String nome) {
        final String sql = "SELECT * FROM clientes WHERE nome LIKE ? ORDER BY nome";
        try // try-witch-resource
        (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, nome.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();
            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                clientes.add(resultsetToCliente(rs));
            }
            return clientes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Cliente> selectClientesBySituacao(boolean situacao) {
        final String sql = "SELECT * FROM clientes WHERE situacao=?";
        try // try-witch-resource
        (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setBoolean(1, situacao);
            ResultSet rs = pstmt.executeQuery();
            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                clientes.add(resultsetToCliente(rs));
            }
            return clientes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Cliente selectClienteById(Long id) {
        final String sql = "SELECT * FROM clientes WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            Cliente cliente = null;
            if (rs.next()) {
                cliente = resultsetToCliente(rs);
            }
            rs.close();
            return cliente;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean insertCliente(Cliente cliente) {
        final String sql = "INSERT INTO clientes (nome, sobrenome, situacao) VALUES (?, ?, ?)";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getSobrenome());
            pstmt.setBoolean(3, cliente.getSituacao());
            int count = pstmt.executeUpdate();
            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCliente(Cliente cliente) {
        final String sql = "UPDATE clientes SET nome=?, sobrenome=?, situacao=? WHERE id=?";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getSobrenome());
            pstmt.setBoolean(3, cliente.getSituacao());
            pstmt.setLong(4, cliente.getId());
            int count = pstmt.executeUpdate();
            return count > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean softDeleteCliente(long id, boolean situacao) {
        final String sql = "UPDATE clientes SET situacao=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setBoolean(1, situacao);
            pstmt.setLong(2, id);
            int count = pstmt.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // método utilitário, converte ResultSet na classe de modelo (nesse caso,
    // Cliente)
    private static Cliente resultsetToCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getLong("id"));
        c.setNome(rs.getString("nome"));
        c.setSobrenome(rs.getString("sobrenome"));
        c.setSituacao(rs.getBoolean("situacao"));

        return c;
    }

    public static void main(String[] args) {
         Cliente cliente = new Cliente("Hermione", "Granger", true);
 		 System.out.println(insertCliente(cliente));
        //System.out.println(softDeleteCliente(3, false));
    }
}
