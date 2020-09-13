package services.team;

import java.util.List;

import dao.TeamDAO;
import model.Team;

public class CreateTeamService {

	public Team execute(String name) {
		TeamDAO teamDAO = TeamDAO.getInstance();
		
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
		TeamDAO teamDAO = TeamDAO.getInstance();
		List<Team> existentTeams = teamDAO.getAllTeams();
		
		for(Team t: existentTeams) {
			if(t.equals(team)) return true;
		}
		
		return false;
	}
}
