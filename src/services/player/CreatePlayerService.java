package services.player;

import java.util.List;

import connection.ConnectionFactory;
import dao.MarketDAO;
import model.Player;
import utils.ClubList;
import utils.PositionsList;

public class CreatePlayerService {
	
	private MarketDAO marketDAO;

	public CreatePlayerService(MarketDAO marketDAO) {
		this.marketDAO = marketDAO;
	}
	
	public CreatePlayerService() {
		this.marketDAO = new MarketDAO(new ConnectionFactory());
	}

	public Player execute(String name, String club, String position, double value, double points) {
		ClubList clubs = ClubList.getInstance();
		PositionsList positions = PositionsList.getInstance();
		
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
		
		if(this.findExistentPlayer(player)) {
			throw new IllegalArgumentException("This player already added.");
		}
		
		this.marketDAO.addPlayer(player);
		
		return player;
	}
	
	private boolean findExistentPlayer(Player newPlayer) {
		
		List<Player> existentPlayers = this.marketDAO.getAllPlayers();
		
		for(Player p: existentPlayers) {
			if(p.equals(newPlayer)) return true;
		}
		
		return false;
	}
}
