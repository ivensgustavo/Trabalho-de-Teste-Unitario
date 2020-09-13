package testes;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import dao.MarketDAO;
import model.Player;
import services.player.UpdatePlayerService;
import utils.ClubInitiator;
import utils.PositionsInitiator;

class UpdatePlayerServiceTest {
	
	private MarketDAO marketDAO = MarketDAO.getInstance();
	private UpdatePlayerService service = new UpdatePlayerService();
	private static Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
	private static ClubInitiator clubInitiator = null;
	private static PositionsInitiator positionsInitiator = null;
	
	
	public UpdatePlayerServiceTest() {
		this.startTestEnvironment();
	}
	
	private void startTestEnvironment() {
		if(clubInitiator == null) {
			clubInitiator = new ClubInitiator();
			clubInitiator.createClubs();
		}
		
		if(positionsInitiator == null) {
			positionsInitiator = new PositionsInitiator();
			positionsInitiator.createPositions();
		}
		
		
		if(this.marketDAO.getPlayer(player.getId()) == null) {
			this.marketDAO.addPlayer(player);
		}
	}
	
	@Test
	void checkInvalidPlayerName() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(player.getId(), "", "Bragantino", "Atacante", 15, 10));
		assertEquals("Player name invalid.", exception.getMessage());
	}
	
	@Test
	void checkInvalidPlayerName2() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(player.getId(), "     ", "Bragantino", "Atacante", 15, 10));
		assertEquals("Player name invalid.", exception.getMessage());
	}
	
	@Test
	void checkInvalidPlayerName3() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(player.getId(), "ivens&gustavo", "Bragantino", "Atacante", 15, 10));
		assertEquals("Player name invalid.", exception.getMessage());
	}
	
	@Test
	void checkInvalidPlayerName4() {
		
		Exception exception = assertThrows(NullPointerException.class, () ->
			service.execute(player.getId(), null, "Bragantino", "Atacante", 15, 10));
		assertEquals("Player name null.", exception.getMessage());
	}
	
	@Test
	void checkInvalidClub() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(player.getId(), "Erivaldo", "Ponte Preta", "Atacante", 15, 10));
		assertEquals("Invalid club.", exception.getMessage());
	}
	

	@Test
	void checkInvalidPosition() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(player.getId(), "Erivaldo", "Flamengo", "Volante", 15, 10));
			assertEquals("Invalid position.", exception.getMessage());
		
	}
	
	
	@Test
	void checkInvalidPlayerValue() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(player.getId(), "Erivaldo", "Flamengo", "Atacante", -2, 0));
			assertEquals("Invalid player value.", exception.getMessage());
		
	}
	
	@Test
	void checkInvalidPlayerValue2() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(player.getId(), "Erivaldo", "Flamengo", "Atacante", 0, 0));
			assertEquals("Invalid player value.", exception.getMessage());
		
	}
	
	
	@Test 
	void checkExistentPlayer() {
	
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(player.getId(), "Pedro Rocha", "Ceará", "Zagueiro", 5, 7));
			assertEquals("This player already exists.", exception.getMessage());
		
	}
	
	@Test
	void checkUpdatePlayer() {
		
		Player updatedPlayer = new Player("Pedro Rocha", "Coritiba", "Zagueiro", 20, 7);
		updatedPlayer.setId(player.getId());
		
		service.execute(
				player.getId(), 
				updatedPlayer.getName(), 
				updatedPlayer.getClub(), 
				updatedPlayer.getPosition(), 
				updatedPlayer.getValue(), 
				updatedPlayer.getPoints());
		
		Player expectedResult = updatedPlayer;
		
		Player obtainedResult = marketDAO.getPlayer(player.getId());
		
		assertEquals(expectedResult, obtainedResult);
	}

}
