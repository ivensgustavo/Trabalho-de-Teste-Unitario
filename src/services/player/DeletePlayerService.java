package services.player;

import dao.MarketDAO;
import model.Player;

public class DeletePlayerService {

	public boolean execute(int id) {
		MarketDAO marketDAO = MarketDAO.getInstance();
		
		Player existentPlayer = marketDAO.getPlayer(id);
		
		if(existentPlayer == null) {
			throw new IllegalArgumentException("The reporting player does not exist.");
		}
		
		return marketDAO.removePlayer(id);
	}
}
