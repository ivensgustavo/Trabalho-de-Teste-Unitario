package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dao.MarketDAO;
import model.Player;
import services.player.CreatePlayerService;
import utils.ClubInitiator;
import utils.PositionsInitiator;


class CreatePlayerServiceTest {
	
	private MarketDAO marketDAO = MarketDAO.getInstance();
	private CreatePlayerService service = new CreatePlayerService();
	private static Player player = new Player("Pedro Fonseca", "Bahia", "Meia", 5, 7);
	private static ClubInitiator clubInitiator = null;
	private static PositionsInitiator positionsInitiator = null;
	
	public CreatePlayerServiceTest() {
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
			service.execute(" Ivens", "Guarani", "Atacante", 15, 10));
		assertEquals("Player name invalid.", exception.getMessage());
	}
	@Test
	void checkInvalidPlayerName2() {
		
		Exception exception = assertThrows(NullPointerException.class, () ->
			service.execute(null, "Guarani", "Atacante", 15, 10));
		assertEquals("Player name null.", exception.getMessage());
	}
	
	@Test
	void checkInvalidClub() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Erivaldo", "Guarani", "Atacante", 15, 10));
		assertEquals("Invalid club.", exception.getMessage());
	}
	
	@Test
	void checkInvalidPosition() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Erivaldo", "Flamengo", "Volante", 15, 10));
			assertEquals("Invalid position.", exception.getMessage());
		
	}
	
	@Test
	void checkInvalidPlayerValue() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Erivaldo", "Flamengo", "Atacante", -2, 0));
			assertEquals("Invalid player value.", exception.getMessage());
		
	}
	
	@Test
	void checkInvalidPlayerValue2() {
	
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Erivaldo", "Flamengo", "Atacante", 0, 0));
			assertEquals("Invalid player value.", exception.getMessage());
		
	}
	
	@Test 
	void checkExistentPlayer() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Pedro Fonseca", "Bahia", "Meia", 5, 7));
			assertEquals("This player already added.", exception.getMessage());
		
	}
	
	@Test
	void checkAddPlayer() {
		
		Player expectedResult = service.execute("Gustavo Ivens", "Flamengo", "Atacante", 15, 12); 
		int id = expectedResult.getId();
		Player obtainedResult = marketDAO.getPlayer(id);
		
		assertEquals(expectedResult, obtainedResult);
	}

}
