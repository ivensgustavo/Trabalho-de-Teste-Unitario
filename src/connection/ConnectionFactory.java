package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionFactory {
	
	private static String DRIVER = "org.postgresql.Driver";
	private static String URL = "jdbc:postgresql://localhost:5432/cartola_db";
	private static String USER = "postgres";
	private static String PASSWORD = "08102806";
	
	public static Connection getConnection() {
		try {
			
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Erro na conex�o com o banco.", e);
		}
	}
	
	public static void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na conex�o com o banco! \n"+e);
			}
		}
	}
	
	public static void closeConnection(Connection conn, PreparedStatement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na conex�o com o banco! \n"+e);
			}
			closeConnection(conn);
		}
	}
	
	public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na conex�o com o banco! \n"+e);
			}
			closeConnection(conn, stmt);
		}
	}

}
