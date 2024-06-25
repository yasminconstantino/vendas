package dao;
/*
import java.sql.*;

public class BaseDAO {

    public static void main( String[] args ) throws SQLException {
        //create connection for a server installed in localhost, with a user "root" with no password
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/yasmin/Downloads/try proj/Untitled", "root", null)) {
            // create a Statement
            try (Statement stmt = conn.createStatement()) {
                //execute query
                try (ResultSet rs = stmt.executeQuery("SELECT 'Hello World!'")) {
                    //position result to first
                    rs.first();
                    System.out.println(rs.getString(1)); //result is "Hello World!"
                }
            }
        }
    }
}*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {
	public static Connection getConnection() {
		
		try {
			//a string com a url para o banco de dados
			//sintaxe: protocolo:tecnologia://domínioDoServidor:porta/database
			final String url = "jdbc:sqlite:/home/yasmin/Documentos/vendasProject/Untitled";
			//argumentos: url para o banco, usuário, senha.
			//retorna um objeto da classe Connection (do pacote java.sql -> que segue a especificação JDBC).
			return DriverManager.getConnection(url, "mariadb", "mariadb");
		} catch (SQLException e) {
			e.printStackTrace();
			return null; //se não conectar, retorna null.
		}
	}
	
	//um main para testar a conexão com o servidor do MariaDB
	public static void main(String[] args) {
		System.out.println(BaseDAO.getConnection()+"\nyeah");
	}

}