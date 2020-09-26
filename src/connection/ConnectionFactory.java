package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionFactory {
	
	private String DRIVER = "org.postgresql.Driver";
	private String URL = "jdbc:postgresql://localhost:5432/cartola_db";
	private String USER = "postgres";
	private String PASSWORD = "08102806";
	
	public Connection getConnection() {
		try {
			
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Erro na conexão com o banco.", e);
		}
	}
	
	public void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na conexão com o banco! \n"+e);
			}
		}
	}
	
	public void closeConnection(Connection conn, PreparedStatement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na conexão com o banco! \n"+e);
			}
			closeConnection(conn);
		}
	}
	
	public void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro na conexão com o banco! \n"+e);
			}
			closeConnection(conn, stmt);
		}
	}

}
