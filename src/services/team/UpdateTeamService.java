package services.team;

import java.util.List;

import connection.ConnectionFactory;
import dao.TeamDAO;
import model.Team;

public class UpdateTeamService {

	private TeamDAO teamDAO;
	
	public UpdateTeamService() {
		this.teamDAO = new TeamDAO(new ConnectionFactory());
	}
	
	public UpdateTeamService(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}
	
	public boolean execute(int id, String name) {
		
		Team existentTeam = teamDAO.getTeam(id);
		
		if(existentTeam == null) {
			throw new IllegalArgumentException("The reporting team does not exist.");
		}
		
		try {
			if(!name.matches("^[a-zA-Z0-9][a-zA-Z0-9| ]*[a-zA-Z]$")) {
				throw new IllegalArgumentException("Team name invalid.");
			}
		}catch (NullPointerException e) {
			throw new NullPointerException("Team name null.");
		}
		
		Team team = new Team(name);
		team.setId(id);
		
		if(this.findExistentTeam(team)) {
			throw new IllegalArgumentException("This team already exists.");
		}
		
		return teamDAO.updateTeam(team);

	}
	
	private boolean findExistentTeam(Team team) {
	
		List<Team> existentTeams = teamDAO.getAllTeams();
		
		for(Team t: existentTeams) {
			if(t.equals(team)) return true;
		}
		
		return false;
	}
}
