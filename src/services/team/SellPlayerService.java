package services.team;

import dao.TeamDAO;
import model.Player;
import model.Team;

public class SellPlayerService {

	public boolean execute(Team team, int id) {
		TeamDAO teamDAO = TeamDAO.getInstance();
		
		Player existentPlayer = team.getPlayer(id);
		
		if(existentPlayer == null) {
			throw new IllegalArgumentException("The informed player does not exist in this team.");
		}
		
		if(team.sellPlayer(id)) {
			return teamDAO.updateTeam(team);
		}
		
		return false;
	}
}
