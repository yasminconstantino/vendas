package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import model.Item;
import model.Pedido;

public class PedidoDAO extends BaseDAO {
    public static boolean insertPedido(Pedido pedido) {
        /*
         * Estas variáveis tem escopo de método, foram colocadas aqui para permitir o
         * acesso em qualquer
         * bloco try-catch contido nesse método.
         */
        final String sql_insert_pedidos = "INSERT INTO pedidos (pagamento, estado, data_criacao, data_modificacao, id_cliente, total_pedido, situacao) VALUES (?, ?, ?, ?, ?, ?, ?);";
        final String sql_insert_itens = "INSERT INTO itens (id_produto, id_pedido, quantidade, total_item, situacao) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt_pedidos = conn.prepareStatement(sql_insert_pedidos,
                        Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmt_itens = conn.prepareStatement(sql_insert_itens);) {
            /*
             * Inicia a transação, desligando o autocommit.
             */
            conn.setAutoCommit(false);
            pstmt_pedidos.setString(1, pedido.getFormaPagamento());
            pstmt_pedidos.setString(2, pedido.getEstado());
            pstmt_pedidos.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            pstmt_pedidos.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            pstmt_pedidos.setInt(5, pedido.getCliente().getId());
            pstmt_pedidos.setDouble(6, pedido.getTotalPedido());
            pstmt_pedidos.setBoolean(7, true);
            int count = pstmt_pedidos.executeUpdate();
            int id = 0;
            if (count > 0) { // se inseriu o pedido na tabela pedidos, pega o id e salva os itens
                ResultSet rs = pstmt_pedidos.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                rs.close(); // libera o objeto
                /*
                 * Realiza a operação de inserção na tabela itens.
                 */
                for (Item i : pedido.getItens()) {
                    pstmt_itens.setInt(1, i.getProduto().getIdProduto());
                    pstmt_itens.setInt(2, id); // id foi gerado no insert anterior, da tabela pedidos
                    pstmt_itens.setInt(3, i.getQuantidade());
                    pstmt_itens.setDouble(4, i.getTotalItem());
                    pstmt_itens.setBoolean(5, true);
                    count = pstmt_itens.executeUpdate();
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
            /*
             * Fim da transação ao comitar. Religa o autocomit, assim outros comportamentos
             * da classe
             * ficam liberados para realizar as operações com o autocommit.
             */
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Pedido selectPedidoById(int id) {
        final String sql = "SELECT * FROM pedidos WHERE id = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return resultsetToPedido(rs);
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updatePedido(Pedido pedido) {
        final String sql = "UPDATE pedidos SET pagamento = ?, estoque = ?, data_modificacao = ?, total_pedido = ?, situacao = ? WHERE id=?";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, pedido.getFormaPagamento());
            pstmt.setString(2, pedido.getEstado());
            pstmt.setDate(3, java.sql.Date.valueOf(pedido.getDataModificacao()));
            pstmt.setDouble(4, pedido.getTotalPedido());
            pstmt.setBoolean(5, pedido.getSituacao());
            pstmt.setInt(6, pedido.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deletePedido(int id) {
        final String sql = "DELETE FROM pedidos WHERE id = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Pedido> selectPedidosBySituacao(boolean situacao) {
        final String sql = "SELECT * FROM pedidos WHERE situacao = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setBoolean(1, situacao);
            ResultSet rs = pstmt.executeQuery();
            List<Pedido> pedidos = new ArrayList<>();
            while (rs.next()) {
                pedidos.add(resultsetToPedido(rs));
            }
            rs.close();
            return pedidos;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<Pedido> selectPedidosByIdCliente(int id) {
        final String sql = "SELECT * FROM pedidos WHERE id_cliente=?";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            List<Pedido> pedidos = new ArrayList<>();
            if (rs.next()) {
                pedidos.add(resultsetToPedido(rs));
            }
            rs.close();
            return pedidos;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Pedido> selectPedidosByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        final String sql = "SELECT * FROM pedidos WHERE data_criacao BETWEEN ? AND ?";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setDate(1, java.sql.Date.valueOf(dataInicio));
            pstmt.setDate(2, java.sql.Date.valueOf(dataFim));
            ResultSet rs = pstmt.executeQuery();
            List<Pedido> pedidos = new ArrayList<>();
            while (rs.next()) {
                pedidos.add(resultsetToPedido(rs));
            }
            rs.close();
            return pedidos;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // método utilitário, converte ResultSet na classe de modelo (nesse caso,
    // Produto)
    private static Pedido resultsetToPedido(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setId(rs.getInt("id"));
        p.setFormaPagamento(rs.getString("pagamento"));
        p.setEstado(rs.getString("estado"));
        p.setDataCriacao(rs.getDate("data_criacao").toLocalDate());
        p.setDataModificacao(rs.getDate("data_modificacao").toLocalDate());
        p.setTotalPedido(rs.getDouble("total_pedido"));
        p.setSituacao(rs.getBoolean("situacao"));
        p.setCliente(ClienteDAO.selectClienteById(rs.getInt("id_cliente")));
        p.setItens(ItemDAO.selectItemByPedido(p.getId()));

        return p;
    }
}
