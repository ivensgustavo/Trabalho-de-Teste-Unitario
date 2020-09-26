package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Player;

public class MarketDAO {

	private ConnectionFactory connectionFactory = null;
	
	public MarketDAO(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public boolean addPlayer(Player player) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "INSERT INTO players(id, name, club, position, value, points) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, player.getId());
			stmt.setString(2, player.getName());
			stmt.setString(3, player.getClub());
			stmt.setString(4, player.getPosition());
			stmt.setDouble(5,  player.getValue());
			stmt.setDouble(6, player.getPoints());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			connectionFactory.closeConnection(conn, stmt);
		}
	}
	
	public boolean removePlayer(int id) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "DELETE FROM players WHERE id = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			connectionFactory.closeConnection(conn, stmt);
		}
	
	}
	
	public List<Player> getAllPlayers() {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM players";
		List<Player> players = new ArrayList<Player>();
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Player player = new Player();
				player.setId(rs.getInt("id"));
				player.setName(rs.getString("name"));
				player.setClub(rs.getString("club"));
				player.setPosition(rs.getString("position"));
				player.setValue(rs.getDouble("value"));
				player.setPoints(rs.getDouble("points"));
				
				players.add(player);
			}
			
			return players;
		} catch (SQLException e) {
			throw new RuntimeException("An SQL error occurred while fetching players.");
		} finally {
			connectionFactory.closeConnection(conn, stmt, rs);
		}
	}
	
	public Player getPlayer(int id) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM players WHERE id = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				Player player = new Player();
				player.setId(rs.getInt("id"));
				player.setName(rs.getString("name"));
				player.setClub(rs.getString("club"));
				player.setPosition(rs.getString("position"));
				player.setValue(rs.getDouble("value"));
				player.setPoints(rs.getDouble("points"));
				
				return player;
			}else return null;
		} catch (SQLException e) {
			throw new RuntimeException("An SQL error occurred while fetching the player.");
		} finally {
			connectionFactory.closeConnection(conn, stmt, rs);
		}
	}
	
	public boolean updatePlayer(Player player) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "UPDATE players SET name = ?, club = ?, position = ?, value = ?, points = ? "
				+ "WHERE id = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, player.getName());
			stmt.setString(2, player.getClub());
			stmt.setString(3, player.getPosition());
			stmt.setDouble(4,  player.getValue());
			stmt.setDouble(5, player.getPoints());
			stmt.setInt(6, player.getId());
			
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			connectionFactory.closeConnection(conn, stmt);
		}
	}
	
}
