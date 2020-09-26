package services.team;

import connection.ConnectionFactory;
import dao.TeamDAO;
import model.Team;

public class DeleteTeamService {

	
	private TeamDAO teamDAO;

	public DeleteTeamService() {
		this.teamDAO = new TeamDAO(new ConnectionFactory());
	}
	
	public DeleteTeamService(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}
	
	public boolean execute(int id) {
		
		Team existentTeam = teamDAO.getTeam(id);
		
		if(existentTeam == null) {
			throw new IllegalArgumentException("The reporting team does not exist.");
		}
		
		return teamDAO.removeTeam(id);
		
	}
	
}
