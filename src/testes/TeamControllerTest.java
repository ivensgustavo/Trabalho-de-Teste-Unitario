package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import control.TeamController;
import dao.MarketDAO;
import dao.TeamDAO;
import model.Player;
import model.Team;


class TeamControllerTest {
	
	private TeamController teamController = new TeamController();
	private TeamDAO teamDAO = TeamDAO.getInstance();

	@Test
	void coulNotAddATeamWithEmptyName() {
		
		String expectedErrorMessage = "Team name invalid.";
		
		boolean obtainedResult = this.teamController.addTeam("");
		String obtainedErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void coulNotAddATeamWithNullName() {
		
		String expectedErrorMessage = "Team name null.";
		
		boolean obtainedResult = this.teamController.addTeam(null);
		String obtainedErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}

	@Test 
	void couldNotAddAnExistingTeam() {
		
		Team team = new Team("Envolvente FC");
		this.teamDAO.addTeam(team);
		
		String expectedErrorMessage = "This team already exists.";
		
		boolean obtainedResult = this.teamController.addTeam("Envolvente FC");
		String obtainedErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	

	@Test 
	void shouldAddATeam() {
		
		boolean obtainedResult = this.teamController.addTeam("Ousadia FC");
		
		assertTrue(obtainedResult);
	}
	
	@Test
	void coulNotUpdateATeamWithInvalidId() {
		
		String expectedErrorMessage = "Team id is invalid.";
		
		String id = "5df";
		String name = "ES FC";
		
		boolean obtainedResult = this.teamController.updateTeam(id, name);
		String obtainedErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void coulNotUpdateATeamWithEmptyName() {
		Team team = new Team("Orgulho FC");
		this.teamDAO.addTeam(team);
		
		String id = String.valueOf(team.getId());
		String name = "";
		
		String expectedErrorMessage = "Team name invalid.";
		
		boolean obtainedResult = this.teamController.updateTeam(id, name);
		String obtainedErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void coulNotUpdateATeamWithNullName() {
		Team team = new Team("Felicidade FC");
		this.teamDAO.addTeam(team);
		
		String id = String.valueOf(team.getId());
		String name = null;
		
		String expectedErrorMessage = "Team name null.";

		boolean obtainedResult = this.teamController.updateTeam(id, name);
		String obtainedErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void couldNotUpdateATeamWithTheSameDataAsAnother() {
		Team team = new Team("Envolvente FC");
		Team team2 = new Team("Quixada FC");
		this.teamDAO.addTeam(team);
		this.teamDAO.addTeam(team2);
		

		String id = String.valueOf(team.getId());
		String name = "Quixada FC";
		
		String expectedErrorMessage = "This team already exists.";
		
		boolean obtainedResult = this.teamController.updateTeam(id, name);
		String obtainedErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void shouldUpdateATeam() {
		Team team = new Team("Carisma FC");
		this.teamDAO.addTeam(team);
		
		String id = String.valueOf(team.getId());
		String name = "Sao Benedito FC";
		
		boolean obtainedResult = this.teamController.updateTeam(id, name);
		
		assertTrue(obtainedResult);
	}
	
	@Test 
	void couldNotDeleteATeamWithInvalidId() {
		Team team = new Team("Arrepio FC");
		this.teamDAO.addTeam(team);
		
		String id = "-2a";
		
		String expectedErrorMessage = "Team id is invalid.";
		
		boolean obtainedResult = this.teamController.deleteTeam(id);
		String obtaindesErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtaindesErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void couldNotDeleteATeamWithNegativeId() {
		Team team = new Team("Arrepio FC");
		this.teamDAO.addTeam(team);
		
		String id = "-2";
		
		String expectedErrorMessage = "The reporting team does not exist.";
		
		boolean obtainedResult = this.teamController.deleteTeam(id);
		String obtaindesErrorMessage = this.teamController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtaindesErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void shouldDeleteATeam() {
		Team team = new Team("Arrepio FC");
		this.teamDAO.addTeam(team);
		
		String id = String.valueOf(team.getId());
		
		boolean obtainedResult = this.teamController.deleteTeam(id);
		
		assertTrue(obtainedResult);
	}
	
	@Test 
	void couldNotBuyWithInvalidTeamId() {
		
		String teamId =  "3a";
		String playerId = "2";
		
		String expectedMessageError = "The team id is invalid.";
		
		boolean obtainedResult = this.teamController.buyPlayer(teamId, playerId);
		String obtainedMessageError = this.teamController.getErrorMessage();
		
		assertEquals(expectedMessageError, obtainedMessageError);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void couldNotBuyWithInvalidPlayerId() {
		
		String teamId =  "2";
		String playerId = "2.5";
		
		String expectedMessageError = "The player id is invalid.";
		
		boolean obtainedResult = this.teamController.buyPlayer(teamId, playerId);
		String obtainedMessageError = this.teamController.getErrorMessage();
		
		assertEquals(expectedMessageError, obtainedMessageError);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void couldNotBuyWithNonExistentTeam() {
		
		String teamId =  "2";
		String playerId = "2";
		
		String expectedMessageError = "There is no team with the given id.";
		
		boolean obtainedResult = this.teamController.buyPlayer(teamId, playerId);
		String obtainedMessageError = this.teamController.getErrorMessage();
		
		assertEquals(expectedMessageError, obtainedMessageError);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void couldNotBuyWithNonExistentPlayer() {
		Team team = new Team("Contagia FC");
		this.teamDAO.addTeam(team);
		
		String teamId =  String.valueOf(team.getId());
		String playerId = "2";
		
		String expectedMessageError = "There is no player with the given id.";
		
		boolean obtainedResult = this.teamController.buyPlayer(teamId, playerId);
		String obtainedMessageError = this.teamController.getErrorMessage();
		
		assertEquals(expectedMessageError, obtainedMessageError);
		assertFalse(obtainedResult);
	}
	
	@Test
	void CouldNotBuyMoreThan11Players() {
		
		Team team = new Team("Baile FC");
		MarketDAO marketDAO = MarketDAO.getInstance();
		
		team.getEscalation().add(new Player("Messi", "Flamengo", "Atacante", 20, 10));
		team.getEscalation().add(new Player("Cristiano Ronaldo", "Flamengo", "Atacante", 20, 10));
		team.getEscalation().add(new Player("Neymar", "Flamengo", "Atacante", 18, 10));
		team.getEscalation().add(new Player("Gerson", "Flamengo", "Meia", 10, 8));
		team.getEscalation().add(new Player("Pogba", "Flamengo", "Meia", 12, 7));
		team.getEscalation().add(new Player("De Bruyne", "Flamengo", "Meia", 9, 7));
		team.getEscalation().add(new Player("Marcelo", "Flamengo", "Lateral", 8, 6));
		team.getEscalation().add(new Player("Daniel Alves", "Flamengo", "Lateral",9, 8));
		team.getEscalation().add(new Player("Thiago Silva", "Flamengo", "Zagueiro", 12, 7));
		team.getEscalation().add(new Player("Marquinhos", "Flamengo", "Zagueiro", 8, 10));
		team.getEscalation().add(new Player("Alisson", "Flamengo", "Goleiro", 12, 7));
		
		Player newPlayer = new Player("Mbappé", "Flamengo", "Atacante", 15, 15);
		
		this.teamDAO.addTeam(team);
		marketDAO.addPlayer(newPlayer);
		
		String teamId =  String.valueOf(team.getId());
		String playerId = String.valueOf(newPlayer.getId());
		
		String expectedMessageError = "There are already 11 players on this team.";
		
		boolean obtainedResult = this.teamController.buyPlayer(teamId, playerId);
		String obtainedMessageError = this.teamController.getErrorMessage();
		
		assertEquals(expectedMessageError, obtainedMessageError);
		assertFalse(obtainedResult);	
	}
	
	@Test 
	void CouldNotBuyTheSamePlayer(){
		MarketDAO marketDAO = MarketDAO.getInstance();
		Team team = new Team("Constrangimento FC");
		team.getEscalation().add(new Player("Messi", "Flamengo", "Atacante", 20, 10));
		this.teamDAO.addTeam(team);
		
		Player newPlayer = new Player("Messi", "Flamengo", "Atacante", 20, 10);
		marketDAO.addPlayer(newPlayer);
		
		String teamId =  String.valueOf(team.getId());
		String playerId = String.valueOf(newPlayer.getId());
		
		String expectedMessageError = "The player is already on the team.";
		
		boolean obtainedResult = this.teamController.buyPlayer(teamId, playerId);
		String obtainedMessageError = this.teamController.getErrorMessage();
		
		assertEquals(expectedMessageError, obtainedMessageError);
		assertFalse(obtainedResult);
		
	}
	
	@Test 
	void shouldSellAPlayer() {
		MarketDAO marketDAO = MarketDAO.getInstance();
		Team team = new Team("Arrepio FC");
		String teamId = String.valueOf(team.getId());
		Player newPlayer = new Player("Willian", "Bahia", "Atacante", 11, 10);
		String playerId = String.valueOf(newPlayer.getId());
		team.getEscalation().add(newPlayer);
		this.teamDAO.addTeam(team);
		marketDAO.addPlayer(newPlayer);
		
		boolean obtainedResult = this.teamController.sellPlayer(teamId, playerId);
		
		assertTrue(obtainedResult);
	}

}
