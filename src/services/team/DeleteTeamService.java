package services.team;

import dao.TeamDAO;
import model.Team;

public class DeleteTeamService {

	public boolean execute(int id) {
		TeamDAO teamDAO = TeamDAO.getInstance();
		
		Team existentTeam = teamDAO.getTeam(id);
		
		if(existentTeam == null) {
			throw new IllegalArgumentException("The reporting team does not exist.");
		}
		
		return teamDAO.removeTeam(id);
		
	}
	
}
