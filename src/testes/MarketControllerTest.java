package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import control.MarketController;
import dao.MarketDAO;
import model.Player;
import utils.ClubInitiator;
import utils.PositionsInitiator;

class MarketControllerTest {

	MarketController marketController = new MarketController();
	MarketDAO marketDAO = MarketDAO.getInstance();

	private void startTestEnvironment() {
		ClubInitiator clubInitiator = new ClubInitiator();
		clubInitiator.createClubs();
		
		PositionsInitiator positionsInitiator = new PositionsInitiator();
		positionsInitiator.createPositions();
	}
	
	
	@Test
	void couldNotAddAPlayerWithInvalidName() {
		this.startTestEnvironment();
		
		String expectedErrorMessage = "Player name invalid.";
		
		boolean obtaindedResult  = this.marketController.addPlayer(" Ivens", "Coritiba", "Atacante", "15", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void  couldNotAddAPlayerWithNullName(){
		this.startTestEnvironment();
		
		String expectedErrorMessage = "Player name null.";
		
		boolean obtaindedResult  = this.marketController.addPlayer(null, "Coritiba", "Atacante", "15", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void  couldNotAddAPlayerWithNullValue(){
		this.startTestEnvironment();
		
		String expectedErrorMessage = "Player value is invalid.";
		
		boolean obtaindedResult  = this.marketController.addPlayer(null, "Coritiba", "Atacante", null, "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void  couldNotAddAPlayerWithInvalidValue(){
		this.startTestEnvironment();
		
		String expectedErrorMessage = "Player value is invalid.";
		
		boolean obtaindedResult  = this.marketController.addPlayer(null, "Coritiba", "Atacante", "10a", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void  couldNotAddAPlayerWithNullPoints(){
		this.startTestEnvironment();
		
		String expectedErrorMessage = "Player points is invalid.";
		
		boolean obtaindedResult  = this.marketController.addPlayer("Ewerton", "Coritiba", "Atacante", "16", null);
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void  couldNotAddAPlayerWithInvalidPoints(){
		this.startTestEnvironment();
		
		String expectedErrorMessage = "Player points is invalid.";
		
		boolean obtaindedResult  = this.marketController.addPlayer("Toro", "Coritiba", "Atacante", "17", "1,5");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void couldNotAddWithInvalidClub() {
		this.startTestEnvironment();
		
		String expectedErrorMessage = "Invalid club.";
		
		boolean obtainedResult = this.marketController.addPlayer("Erivaldo", "Guarani", "Atacante", "15", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
			
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void couldNotAddWithInvalidPosition() {
		this.startTestEnvironment();
		
		String expectedErrorMessage = "Invalid position.";
		
		boolean obtainedResult = this.marketController.addPlayer("Erivaldo", "Flamengo", "Volante", "15", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test 
	void couldNotAddAnExistingPlayer() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		
		marketDAO.addPlayer(new Player("Pedro Ramos", "Bahia", "Meia", 5, 7));
		
		String expectedErrorMessage = "This player already added.";
		
		boolean obtainedResult = this.marketController.addPlayer("Pedro Ramos", "Bahia", "Meia", "5", "7");
		String obtainedErrorMessage = this.marketController.getErrorMessage();	
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void shouldAddAPlayer() {
		this.startTestEnvironment();
		
		boolean  obtainedResult = this.marketController.addPlayer("Gustavo Ivens", "Flamengo", "Atacante", "15", "12"); 
		
		assertTrue(obtainedResult);
	}
	
	@Test
	void couldNotUpdateANonExistentPlayer() {
		this.startTestEnvironment();
		
		String expectedErrorMessage = "The reporting player does not exist.";
		
		boolean obtainedResult = this.marketController.updatePlayer("0", "Gustavo", "Flamengo", "Atacante", "15", "12");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}

	@Test
	void couldNotUpdateAPlayerWithInvalidName() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
		int id = player.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Player name invalid.";
		
		boolean obtaindedResult  = this.marketController.updatePlayer(stringId, "   ", "Coritiba", "Atacante", "15", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void couldNotUpdateAPlayerWithNullName() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
		int id = player.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Player name null.";
		
		boolean obtaindedResult  = this.marketController.updatePlayer(stringId, null, "Coritiba", "Atacante", "15", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void couldNotUpdateAPlayerWithNullValue() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
		int id = player.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Player value is invalid.";
		
		boolean obtaindedResult  = this.marketController.updatePlayer(stringId, "Ivens", "Coritiba", "Atacante", null, "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void couldNotUpdateAPlayerWithInvalidValue() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
		int id = player.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Player value is invalid.";
		
		boolean obtaindedResult  = this.marketController.updatePlayer(stringId, null, "Coritiba", "Atacante", null, "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void couldNotUpdateAPlayerWithNullPoints() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
		int id = player.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Player points is invalid.";
		
		boolean obtaindedResult  = this.marketController.updatePlayer(stringId, "Emerson", "Coritiba", "Atacante", "16", null);
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void couldNotUpdateAPlayerWithInvalidPoints() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
		int id = player.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Player points is invalid.";
		
		boolean obtaindedResult  = this.marketController.updatePlayer(stringId, "Toro", "Coritiba", "Atacante", "17", "1,5");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertFalse(obtaindedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
	
	@Test
	void couldNotUpdateAPlayerWithInvalidClub() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
		int id = player.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Invalid club.";
		
		boolean obtainedResult = this.marketController.updatePlayer(stringId, "Erivaldo", "Guarani", "Atacante", "15", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
			
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void couldNotUpdateAPlayerWithInvalidPosition() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
		int id = player.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Invalid position.";
		
		boolean obtainedResult = this.marketController.updatePlayer(stringId, "Erivaldo", "Flamengo", "Volante", "15", "10");
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void CouldNotUpdateAPlayerWithDataEqualToThatOfAnother() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Ramos", "Bahia", "Meia", 5, 7);
		Player player2 = new Player("Pedro Rocha", "Ceará", "Atacante", 10, 9);
		int id = player2.getId();
		String stringId = String.valueOf(id);
		marketDAO.addPlayer(player);
		marketDAO.addPlayer(player2);
		
		String expectedErrorMessage = "This player already exists.";
		
		boolean obtainedResult = this.marketController.updatePlayer(stringId, "Pedro Ramos", "Bahia", "Meia", "5", "7");
		String obtainedErrorMessage = this.marketController.getErrorMessage();	
		
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void shouldUpdateAPlayer() {
		this.startTestEnvironment();
		MarketDAO marketDAO = MarketDAO.getInstance();
		Player player = new Player("Pedro Ramos", "Bahia", "Meia", 5, 7);
		String stringId = String.valueOf(player.getId());
		marketDAO.addPlayer(player);
		
		boolean  obtainedResult = this.marketController.updatePlayer(stringId, "Erivelton", "Flamengo", "Atacante", "15", "12"); 
		
		assertTrue(obtainedResult);
	}
	
	@Test
	void couldNotDeleteANonExistentPlayer() {
		this.startTestEnvironment();
		Player player = new Player("Antonio Ferreira", "Flamengo", "Meia", 5, 7);
		String stringId = "50";
		this.marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "The reporting player does not exist.";
		
		boolean obtainedResult = this.marketController.deletePlayer(stringId);
		String obtainedMessage = this.marketController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void couldNotDeleteWithNegativeId() {
		this.startTestEnvironment();
		Player player = new Player("Antonio Ferreira", "Flamengo", "Meia", 5, 7);
		String stringId = "-2";
		this.marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "The reporting player does not exist.";
		
		boolean obtainedResult = this.marketController.deletePlayer(stringId);
		String obtainedMessage = this.marketController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void couldNotDeleteWithInvalidId() {
		this.startTestEnvironment();
		Player player = new Player("Antonio Ferreira", "Flamengo", "Meia", 5, 7);
		String stringId = "2.5";
		this.marketDAO.addPlayer(player);
		
		String expectedErrorMessage = "Invalid player id.";
		
		boolean obtainedResult = this.marketController.deletePlayer(stringId);
		String obtainedMessage = this.marketController.getErrorMessage();
		
		assertEquals(expectedErrorMessage, obtainedMessage);
		assertFalse(obtainedResult);
	}
	
	@Test
	void shouldDeleteAPlayer() {
		this.startTestEnvironment();
		Player player = new Player("Antonio Ferreira", "Flamengo", "Meia", 5, 7);
		String stringId = String.valueOf(player.getId());
		this.marketDAO.addPlayer(player);
		
		boolean obtainedResult = this.marketController.deletePlayer(stringId);

		assertTrue(obtainedResult);
	}
	
	@Test
	void couldNotGetWithInvalidId() {
		this.startTestEnvironment();
		Player player = new Player("Antonio Ferreira", "Flamengo", "Meia", 5, 7);
		this.marketDAO.addPlayer(player);
		
		String id = "4,5";
		
		String expectedErrorMessage = "Player id is invalid.";
		
		Player obtainedResult = this.marketController.getPlayer(id);
		String obtainedErrorMessage = this.marketController.getErrorMessage();
		

		assertNull(obtainedResult);
		assertEquals(expectedErrorMessage, obtainedErrorMessage);
	}
}
