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

	private static MarketDAO uniqueInstance = null;
	private Connection conn = ConnectionFactory.getConnection();
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	private MarketDAO() {
		
	}
	
	public static MarketDAO getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new MarketDAO();
		}
		
		return uniqueInstance;
	}
	
	public boolean addPlayer(Player player) {
		
		String sql = "INSERT INTO players(id, name, club, position, value, points) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt.setInt(1, player.getId());
			this.stmt.setString(2, player.getName());
			this.stmt.setString(3, player.getClub());
			this.stmt.setString(4, player.getPosition());
			this.stmt.setDouble(5,  player.getValue());
			this.stmt.setDouble(6, player.getPoints());
			this.stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean removePlayer(int id) {
		
		String sql = "DELETE FROM players WHERE id = ?";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	
	}
	
	public List<Player> getAllPlayers() {
		
		String sql = "SELECT * FROM players";
		List<Player> players = new ArrayList<Player>();
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.rs = this.stmt.executeQuery();
			
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
		}
	}
	
	public Player getPlayer(int id) {
		
		String sql = "SELECT * FROM players WHERE id = ?";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt.setInt(1, id);
			this.rs = this.stmt.executeQuery();
			
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
		}
	}
	
	public boolean updatePlayer(Player player) {
		
		String sql = "UPDATE players SET name = ?, club = ?, position = ?, value = ?, points = ? "
				+ "WHERE id = ?";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt.setString(1, player.getName());
			this.stmt.setString(2, player.getClub());
			this.stmt.setString(3, player.getPosition());
			this.stmt.setDouble(4,  player.getValue());
			this.stmt.setDouble(5, player.getPoints());
			this.stmt.setInt(6, player.getId());
			
			this.stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
}
