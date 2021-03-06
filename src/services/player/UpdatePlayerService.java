package services.player;

import java.util.List;

import connection.ConnectionFactory;
import dao.MarketDAO;
import model.Player;
import utils.ClubList;
import utils.PositionsList;

public class UpdatePlayerService {
	
	private MarketDAO marketDAO;

	public UpdatePlayerService() {
		this.marketDAO = new MarketDAO(new ConnectionFactory());
	}
	
	public UpdatePlayerService(MarketDAO marketDAO) {
		this.marketDAO = marketDAO;
	}

	public Player execute(int id, String name, String club, String position, double value, double points) {
		ClubList clubs = ClubList.getInstance();
		PositionsList positions = PositionsList.getInstance();
		
		Player existentPlayer = this.marketDAO.getPlayer(id);
		
		if(existentPlayer == null) {
			throw new IllegalArgumentException("The reporting player does not exist.");
		}
		
		try {
			if(!name.matches("^[a-zA-Z0-9][a-zA-Z0-9| ]*[a-zA-Z]$")) {
				throw new IllegalArgumentException("Player name invalid.");
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Player name null.");
		}
		
			
		if(!clubs.checkClubIsPresent(club)) {
			throw new IllegalArgumentException("Invalid club.");
		}
		
		if(!positions.checkPositionIsPresent(position)) {
			throw new IllegalArgumentException("Invalid position.");
		}
		
		if(value <= 0) {
			throw new IllegalArgumentException("Invalid player value.");
		}
		
		Player player = new Player(name, club, position, value, points);
		player.setId(id);
		
		if(findExistentPlayer(player)) {
			throw new IllegalArgumentException("This player already exists.");
		}
		
		this.marketDAO.updatePlayer(player);
		
		return player;
	}
	
	private boolean findExistentPlayer(Player updatedPlayer) {
		
		List<Player> existentPlayers = this.marketDAO.getAllPlayers();
		
		for(Player p: existentPlayers) {
			if(p.equals(updatedPlayer)) return true;
		}
		
		return false;
	}
}
