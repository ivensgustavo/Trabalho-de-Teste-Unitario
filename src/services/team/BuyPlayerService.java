package services.team;

import connection.ConnectionFactory;
import dao.TeamDAO;
import model.Player;
import model.Team;

public class BuyPlayerService {
	
	private TeamDAO teamDAO;
	
	public BuyPlayerService() {
		this.teamDAO = new TeamDAO(new ConnectionFactory());
	}
	
	public BuyPlayerService(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}

	public boolean execute(Team team, Player player) throws Exception {
		
		if(team.getEscalation().size() == 11) {
			throw new Exception("There are already 11 players on this team.");
		}
		
		if(team.getEscalation().contains(player)) {
			throw new IllegalArgumentException("The player is already on the team.");
		}
		
		if(team.buyPlayer(player)) {
			return this.teamDAO.updateTeam(team);
		}
		
		return false;
	}
}
