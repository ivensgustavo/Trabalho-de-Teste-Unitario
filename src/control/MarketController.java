package control;

import java.util.List;

import dao.MarketDAO;
import model.Player;
import services.player.CreatePlayerService;
import services.player.DeletePlayerService;
import services.player.UpdatePlayerService;

public class MarketController {

	private String errorMessage = "";
	private MarketDAO marketDAO = MarketDAO.getInstance();
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	private double convertValue(String value) {
		try {
			double d = Double.parseDouble(value);
			return d;
		} catch (Exception e) {
			return -1;
		}
	}
	
	private double convertPoints(String points) {
		try {
			double d = Double.parseDouble(points);
			return d;
		} catch (Exception e) {
			return -1;
		}
	}
	
	private int convertId(String id) {
		try {
			int i = Integer.parseInt(id);
			return i;
		}catch (Exception e) {
			return -1;
		}
	}
	
	public boolean addPlayer(String name, String club, String position, String value, String points) {
		CreatePlayerService service = new CreatePlayerService();
		
		double parsedValue = this.convertValue(value);
		double parsedPoints = this.convertPoints(points);
		
		if(parsedValue == -1) {
			this.errorMessage = "Player value is invalid.";
			return false;
		}
		
		if(parsedPoints == -1) {
			this.errorMessage = "Player points is invalid.";
			return false;
		}
		
		try {
			service.execute(name, club, position, parsedValue, parsedPoints);
			return true;
		}catch (IllegalArgumentException | NullPointerException e) {
			this.errorMessage = e.getMessage();
			return false;
		}
		
	}
	
	public boolean updatePlayer(String id, String name, String club, String position, String value, String points) {
		UpdatePlayerService service = new UpdatePlayerService();
		
		int parsedId = this.convertId(id);
		double parsedValue = this.convertValue(value);
		double parsedPoints = this.convertPoints(points);
		
		if(parsedId == -1) {
			this.errorMessage = "Player id is invalid.";
			return false;
		}
		
		if(parsedValue == -1) {
			this.errorMessage = "Player value is invalid.";
			return false;
		}
		
		if(parsedPoints == -1) {
			this.errorMessage = "Player points is invalid.";
			return false;
		}
		
		try {
			service.execute(parsedId, name, club, position, parsedValue, parsedPoints);
			return true;
		} catch (IllegalArgumentException | NullPointerException e){
			this.errorMessage = e.getMessage();
			return false;
		}
		
	}

	public boolean deletePlayer(String id) {
		DeletePlayerService service = new DeletePlayerService();
		
		int parsedId = this.convertId(id);
		
		if(parsedId == -1) {
			this.errorMessage = "Invalid player id.";
			return false;
		}
		
		try {
			service.execute(parsedId);
			return true;
		} catch(IllegalArgumentException e) {
			this.errorMessage = e.getMessage();
			return false;
		}
	}

	public List<Player> getAllPlayers(){
		return this.marketDAO.getAllPlayers();
	}
	
	public Player getPlayer(String id) {
		
		int parsedId = convertId(id);
		
		if(parsedId == -1) {
			this.errorMessage = "Player id is invalid.";
			return null;
		}
		
		return this.marketDAO.getPlayer(parsedId);
	}
}
