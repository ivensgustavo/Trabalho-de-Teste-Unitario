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

	private static TeamDAO uniqueInstance = null;
	private Connection conn = ConnectionFactory.getConnection();
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	private TeamDAO() {
		
	}
	
	public static TeamDAO getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new TeamDAO();
		}
		
		return uniqueInstance;
	}
	
	public boolean addTeam(Team team) {
		
		String sql = "INSERT INTO teams(id, name) VALUES(?, ?)";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt.setInt(1, team.getId());
			this.stmt.setString(2, team.getName());
			
			this.stmt.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.out.println("erro sql: "+e);
			return false;
		}

	}
	
	private List<Player> getEscalation(int teamId){
		
		String sql = "SELECT * FROM players, team_player WHERE players.id = team_player.player_id "
				+ "AND team_player.team_id = ?";
		
		List<Player> players = new ArrayList<Player>();
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt.setInt(1, teamId);
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
			throw new RuntimeException("An SQL error occurred while fetching the escalation.");
		} 
		
	}
	
	public List<Team> getAllTeams(){
		
		String sql = "SELECT * FROM teams";
		List<Team> teams = new ArrayList<Team>();
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.rs = this.stmt.executeQuery();
			
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
		}
		
	}
	
	public Team getTeam(int id){
		String sql = "SELECT * FROM teams WHERE id = ?";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt.setInt(1, id);
			rs = this.stmt.executeQuery();
			
			if(rs.next()) {
				Team team = new Team();
				team.setId(rs.getInt("id"));
				team.setName(rs.getString("name"));
				team.setTotalPoints(rs.getDouble("total_points"));
				team.setEscalation(this.getEscalation(id));
				
				return team;
			}else return null;
			
		} catch (SQLException e) {
			throw new RuntimeException("An SQL error occurred while fetching the team.");
		}
		
	}
	
	public boolean removeTeam(int id) {
		String sql = "DELETE FROM teams WHERE id = ?";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt.setInt(1, id);
			this.stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	public boolean updateTeam(Team team) {
		
		String sql = "UPDATE teams SET name = ?, total_points = ? WHERE id = ?";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			this.stmt.setString(1, team.getName());
			this.stmt.setDouble(2, team.getTotalPoints());
			this.stmt.setInt(3,  team.getId());
			this.stmt.executeUpdate();
			
			return this.updateEscalation(team);
			
		} catch (SQLException e) {
			return false;
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
		String sql = "INSERT INTO team_player(team_id, player_id) VALUES(?, ?)";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			
			for(Player p: newPlayers) {
				this.stmt.setInt(1, teamId);
				this.stmt.setInt(2, p.getId());
				this.stmt.executeUpdate();
			}
			
			return true;
			
		} catch (SQLException e) {
			System.out.println(e);
			throw new RuntimeException("There was an error adding new players to the team.");
		}
		
	}
	
	private boolean removePlayerFromEscalation(List<Player> excludedPlayers, int teamId) {
		String sql = "DELETE FROM team_player WHERE team_id = ? AND player_id = ?";
		
		try {
			this.stmt = this.conn.prepareStatement(sql);
			
			for(Player p: excludedPlayers) {
				this.stmt.setInt(1, teamId);
				this.stmt.setInt(2, p.getId());
				this.stmt.executeUpdate();
			}
			
			return true;
			
		} catch (SQLException e) {
			throw new RuntimeException("There was an error excluding os players to the team.");
		}
		
	}
	
	
	
}
