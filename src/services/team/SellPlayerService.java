package services.team;

import connection.ConnectionFactory;
import dao.TeamDAO;
import model.Player;
import model.Team;

public class SellPlayerService {

	public boolean execute(Team team, int id) {
		TeamDAO teamDAO = new TeamDAO(new ConnectionFactory());
		
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
