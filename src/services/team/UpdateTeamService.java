package services.team;

import java.util.List;

import dao.TeamDAO;
import model.Team;

public class UpdateTeamService {

	public boolean execute(int id, String name) {
		TeamDAO teamDAO = TeamDAO.getInstance();
		
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
		TeamDAO teamDAO = TeamDAO.getInstance();
		List<Team> existentTeams = teamDAO.getAllTeams();
		
		for(Team t: existentTeams) {
			if(t.equals(team)) return true;
		}
		
		return false;
	}
}
