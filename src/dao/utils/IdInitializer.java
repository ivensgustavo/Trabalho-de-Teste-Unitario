package dao.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;

public class IdInitializer {

	private ConnectionFactory connectionFactory = null;
	private Connection conn = null;
	
	public IdInitializer(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
		this.conn = this.connectionFactory.getConnection();
	}
	
	public int getInitialPlayerId(){
		String sql = "SELECT id FROM players  ORDER BY id DESC LIMIT 1";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = this.conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()) return rs.getInt("id") + 1;
			return 1;	
		} catch (SQLException e) {
			throw new RuntimeException("Error initializing player id");
		}
	}
	
	public int getInitialTeamId(){
		String sql = "SELECT id FROM teams  ORDER BY id DESC LIMIT 1";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = this.conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()) return rs.getInt("id") + 1;
			return 1;	
		} catch (SQLException e) {
			throw new RuntimeException("Error initializing player id");
		}
	}
}
