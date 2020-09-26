package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import connection.ConnectionFactory;
import dao.MarketDAO;
import model.Player;
import services.player.DeletePlayerService;
import utils.ClubInitiator;
import utils.PositionsInitiator;

class DeletePlayerServiceTest {
	
	private static ClubInitiator clubInitiator = null;
	private static PositionsInitiator positionsInitiator = null;
	private DeletePlayerService service = new DeletePlayerService();
	
	public DeletePlayerServiceTest() {
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
	}
	
	@Test
	void checkInvalidID() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(0));
			assertEquals("The reporting player does not exist.", exception.getMessage());
	}
	
	@Test
	void checkInvalidID2() {
	
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(-3));
			assertEquals("The reporting player does not exist.", exception.getMessage());
	}

	@Test
	void checkDeletePlayer() {
		MarketDAO marketDAO = new MarketDAO(new ConnectionFactory());
		Player player = new Player("Micael", "Flamengo", "Atacante", 10, 12);
		int id = player.getId();
		
		marketDAO.addPlayer(player);
		
		boolean obtainedResult = service.execute(id);
		
		assertTrue(obtainedResult);
	}

}
