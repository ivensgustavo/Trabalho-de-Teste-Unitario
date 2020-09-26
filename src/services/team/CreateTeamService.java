package services.team;

import java.util.List;

import connection.ConnectionFactory;
import dao.TeamDAO;
import model.Team;

public class CreateTeamService {
	
	private TeamDAO teamDAO;

	public CreateTeamService() {
		this.teamDAO = new TeamDAO(new ConnectionFactory());
	}
	
	public CreateTeamService(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}
	
	

	public Team execute(String name) {
		
		try {
			if(!name.matches("^[a-zA-Z0-9][a-zA-Z0-9| ]*[a-zA-Z]$")) {
				throw new IllegalArgumentException("Team name invalid.");
			}
		}catch (NullPointerException e) {
			throw new NullPointerException("Team name null.");
		}
		
		Team team = new Team(name);
		
		if(this.findExistentTeam(team)) {
			throw new IllegalArgumentException("This team already exists.");
		}
		
		if(teamDAO.addTeam(team)) return team;
		else return null;
	
	}
	
	private boolean findExistentTeam(Team team) {
	
		List<Team> existentTeams = teamDAO.getAllTeams();
		
		for(Team t: existentTeams) {
			if(t.equals(team)) return true;
		}
		
		return false;
	}
}
