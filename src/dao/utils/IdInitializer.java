package dao.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;

public class IdInitializer {

	private Connection conn = ConnectionFactory.getConnection();
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public int getInitialPlayerId(){
		String sql = "SELECT id FROM players  ORDER BY id DESC LIMIT 1";
		try {
			this.stmt = this.conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()) return rs.getInt("id") + 1;
			return 1;	
		} catch (SQLException e) {
			throw new RuntimeException("Error initializing player id");
		}
	}
	
	public int getInitialTeamId(){
		String sql = "SELECT id FROM teams  ORDER BY id DESC LIMIT 1";
		try {
			this.stmt = this.conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()) return rs.getInt("id") + 1;
			return 1;	
		} catch (SQLException e) {
			throw new RuntimeException("Error initializing player id");
		}
	}
}
