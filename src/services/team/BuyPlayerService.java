package services.team;

import dao.TeamDAO;
import model.Player;
import model.Team;

public class BuyPlayerService {

	public boolean execute(Team team, Player player) throws Exception {
				
		TeamDAO teamDAO = TeamDAO.getInstance();
		
		if(team.getEscalation().size() == 11) {
			throw new Exception("There are already 11 players on this team.");
		}
		
		if(team.getEscalation().contains(player)) {
			throw new IllegalArgumentException("The player is already on the team.");
		}
		
		if(team.buyPlayer(player)) {
			return teamDAO.updateTeam(team);
		}
		
		return false;
	}
}
