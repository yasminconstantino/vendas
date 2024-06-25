package dao;

import java.sql.*;
import java.util.*;

import model.Item;

public class ItemDAO extends BaseDAO {
    public static List<Item> selectItemByPedido(final long id) {
        final String sql = "SELECT * FROM itens WHERE id_pedido=?";
        try // try-witch-resource
        (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            List<Item> produtos = new ArrayList<>();
            while (rs.next()) {
                produtos.add(resultsetToProduto(rs));
            }
            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Item resultsetToProduto(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setIdItemPedido(rs.getLong("id"));
        item.setProduto(ProdutoDAO.selectProdutoById(rs.getLong("id_produto")));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setSituacao(rs.getBoolean("situacao"));
        item.setTotalItem(rs.getDouble("total_item"));

        return item;
    }

    public static void main(String[] args) {
        List<Item> itens = selectItemByPedido(1);
        System.out.println(itens);
    }
}
