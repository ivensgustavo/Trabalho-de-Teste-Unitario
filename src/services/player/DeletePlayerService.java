package services.player;

import connection.ConnectionFactory;
import dao.MarketDAO;
import model.Player;

public class DeletePlayerService {
	
	private MarketDAO marketDAO;

	public DeletePlayerService() {
		this.marketDAO = new MarketDAO(new ConnectionFactory());
	}
	
	public DeletePlayerService(MarketDAO marketDAO) {
		this.marketDAO = marketDAO;
	}

	public boolean execute(int id) {
		
		Player existentPlayer = this.marketDAO.getPlayer(id);
		
		if(existentPlayer == null) {
			throw new IllegalArgumentException("The reporting player does not exist.");
		}
		
		return marketDAO.removePlayer(id);
	}
}
