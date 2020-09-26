package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Player;
import model.Team;

public class TeamDAO {

	private ConnectionFactory connectionFactory;
	
	public TeamDAO(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public boolean addTeam(Team team) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "INSERT INTO teams(id, name) VALUES(?, ?)";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, team.getId());
			stmt.setString(2, team.getName());
			
			stmt.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.out.println("erro sql: "+e);
			return false;
		} finally {
			connectionFactory.closeConnection(conn, stmt);
		}

	}
	
	private List<Player> getEscalation(int teamId){
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM players, team_player WHERE players.id = team_player.player_id "
				+ "AND team_player.team_id = ?";
		
		List<Player> players = new ArrayList<Player>();
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, teamId);
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
				System.out.println("pegou um player");
				System.out.println(player);
			}
			
			return players;
			
		} catch (SQLException e) {
			throw new RuntimeException("An SQL error occurred while fetching the escalation.");
		} finally {
			connectionFactory.closeConnection(conn, stmt, rs);
		}
		
	}
	
	public List<Team> getAllTeams(){
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		System.out.println("rs no get all");
		String sql = "SELECT * FROM teams";
		List<Team> teams = new ArrayList<Team>();
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Team team = new Team();
				team.setId(rs.getInt("id"));
				team.setName(rs.getString("name"));
				team.setTotalPoints(rs.getDouble("total_points"));
				
				teams.add(team);
			}
			
			for (Team t : teams) {
				t.setEscalation(this.getEscalation(t.getId()));
			}
			
			return teams;
			
			
		} catch (SQLException e) {
			throw new RuntimeException("An SQL error occurred while fetching the teams.");
		} finally {
			connectionFactory.closeConnection(conn, stmt, rs);
		}
		
	}
	
	public Team getTeam(int id){
	
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		System.out.println("rs no get");
		
		String sql = "SELECT * FROM teams WHERE id = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				Team team = new Team();
				team.setId(rs.getInt("id"));
				team.setName(rs.getString("name"));
				team.setTotalPoints(rs.getDouble("total_points"));
				team.setEscalation(this.getEscalation(id));
				
				System.out.println("tamanho "+team.getEscalation());
				return team;
			}else return null;
			
		} catch (SQLException e) {
			throw new RuntimeException("An SQL error occurred while fetching the team.");
		} finally {
			connectionFactory.closeConnection(conn, stmt, rs);
		}
		
	}
	
	public boolean removeTeam(int id) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "DELETE FROM teams WHERE id = ?";
		
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
	
	public boolean updateTeam(Team team) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "UPDATE teams SET name = ?, total_points = ? WHERE id = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, team.getName());
			stmt.setDouble(2, team.getTotalPoints());
			stmt.setInt(3,  team.getId());
			stmt.executeUpdate();
			
			return this.updateEscalation(team);
			
		} catch (SQLException e) {
			return false;
		} finally {
			connectionFactory.closeConnection(conn, stmt);
		}
		
	}
	
	private boolean updateEscalation(Team team) {
		
		List<Player> savedPlayers = this.getEscalation(team.getId());
		List<Player> newPlayers = new ArrayList<Player>();
		List<Player> excludedPlayers = new ArrayList<Player>();
		
		for(Player p: team.getEscalation()) {
			if(!savedPlayers.contains(p)) {
				newPlayers.add(p);
			}
		}
		
		for(Player p: savedPlayers) {
			if(!team.getEscalation().contains(p)) {
				excludedPlayers.add(p);
			}
		}
		
		return this.addPlayerToEscalation(newPlayers, team.getId()) && 
				this.removePlayerFromEscalation(excludedPlayers, team.getId());
		
	}
	
	private boolean addPlayerToEscalation(List<Player> newPlayers, int teamId) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "INSERT INTO team_player(team_id, player_id) VALUES(?, ?)";
		
		try {
			stmt = conn.prepareStatement(sql);
			
			for(Player p: newPlayers) {
				stmt.setInt(1, teamId);
				stmt.setInt(2, p.getId());
				stmt.executeUpdate();
			}
			
			return true;
			
		} catch (SQLException e) {
			System.out.println(e);
			throw new RuntimeException("There was an error adding new players to the team.");
		} finally {
			connectionFactory.closeConnection(conn, stmt);
		}
		
	}
	
	private boolean removePlayerFromEscalation(List<Player> excludedPlayers, int teamId) {
		
		Connection conn = connectionFactory.getConnection();
		PreparedStatement stmt = null;
	
		String sql = "DELETE FROM team_player WHERE team_id = ? AND player_id = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			
			for(Player p: excludedPlayers) {
				stmt.setInt(1, teamId);
				stmt.setInt(2, p.getId());
				stmt.executeUpdate();
			}
			
			return true;
			
		} catch (SQLException e) {
			throw new RuntimeException("There was an error excluding os players to the team.");
		} finally {
			connectionFactory.closeConnection(conn, stmt);
		}
		
	}
	
	
	
}
