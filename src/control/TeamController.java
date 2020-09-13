package control;

import java.util.List;

import dao.TeamDAO;
import model.Player;
import model.Team;
import services.team.BuyPlayerService;
import services.team.CreateTeamService;
import services.team.DeleteTeamService;
import services.team.SellPlayerService;
import services.team.UpdateTeamService;

public class TeamController {

	private String errorMessage = "";
	private TeamDAO teamDAO = TeamDAO.getInstance();
	private MarketController marketController = new MarketController();
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	private int convertId(String id) {
		try {
			int i = Integer.parseInt(id);
			return i;
		}catch (Exception e) {
			return -1;
		}
	}
	
	public boolean addTeam(String name) {
		CreateTeamService service = new CreateTeamService();
		
		try {
			service.execute(name);
			return true;
		} catch (IllegalArgumentException | NullPointerException e) {
			this.errorMessage = e.getMessage();
			return false;
		}
	}
	
	public boolean updateTeam(String id, String name) {
		UpdateTeamService service = new UpdateTeamService();
		
		int parsedId = this.convertId(id);
		
		if(parsedId == -1) {
			this.errorMessage = "Team id is invalid.";
			return false;
		}
		
		try {
			service.execute(parsedId, name);
			return true;
		} catch (IllegalArgumentException | NullPointerException e) {
			this.errorMessage = e.getMessage();
			return false;
		}
	}
	
	public boolean deleteTeam(String id) {
		DeleteTeamService service = new DeleteTeamService();
		
		int parsedId = this.convertId(id);
		
		if(parsedId == -1) {
			this.errorMessage = "Team id is invalid.";
			return false;
		}
		
		try {
			service.execute(parsedId);
			return true;
		} catch (IllegalArgumentException e) {
			this.errorMessage = e.getMessage();
			return false;
		}
	}
	
	public List<Team> getALLTeams(){
		return this.teamDAO.getAllTeams();
	}
	
	public Team getTeam(String id){
		
		int parsedId = this.convertId(id);

		if(parsedId == -1) {
			this.errorMessage = "The team id is invalid.";
			return null;
		}
		return this.teamDAO.getTeam(parsedId);
	}
	
	public boolean buyPlayer(String teamId, String playerId) {
		BuyPlayerService service = new BuyPlayerService();
		
		int parsedTeamId = this.convertId(teamId);
		int parsedPlayerId = this.convertId(playerId);
		
		if(parsedTeamId == -1) {
			this.errorMessage = "The team id is invalid.";
			return false;
		}
		
		if(parsedPlayerId == -1) {
			this.errorMessage = "The player id is invalid.";
			return false;
		}
		
		if(this.teamDAO.getTeam(parsedTeamId) == null) {
			this.errorMessage = "There is no team with the given id.";
			return false;
		}
		
		if(this.marketController.getPlayer(playerId) == null) {
			this.errorMessage = "There is no player with the given id.";
			return false;
		}
		
		try {
			Team team = this.teamDAO.getTeam(parsedTeamId);
			Player player = this.marketController.getPlayer(playerId);
			service.execute(team, player);
			return true;
		} catch (Exception e) {
			this.errorMessage = e.getMessage();
			return false;
		}
	}
	
	public boolean sellPlayer(String teamId, String playerId) {
		SellPlayerService service = new SellPlayerService();
		
		int parsedTeamId = this.convertId(teamId);
		int parsedPlayerId = this.convertId(playerId);
		
		if(parsedTeamId == -1) {
			this.errorMessage = "The team id is invalid.";
			return false;
		}
		
		if(parsedPlayerId == -1) {
			this.errorMessage = "The player id is invalid.";
			return false;
		}
		
		if(this.teamDAO.getTeam(parsedTeamId) == null) {
			this.errorMessage = "There is no team with the given id.";
			return false;
		}
		
		if(this.marketController.getPlayer(playerId) == null) {
			this.errorMessage = "There is no player with the given id.";
			return false;
		}
		
		try {
			Team team = this.teamDAO.getTeam(parsedTeamId);
			service.execute(team, parsedPlayerId);
			return true;
		} catch (Exception e) {
			this.errorMessage = e.getMessage();
			return false;
		}
	}
}
