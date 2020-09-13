package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import dao.MarketDAO;
import dao.TeamDAO;
import model.Player;
import model.Team;
import services.team.BuyPlayerService;
import services.team.SellPlayerService;

class SellPlayerServiceTest {
	
	private TeamDAO teamDAO = TeamDAO.getInstance();
	private MarketDAO marketDAO = MarketDAO.getInstance();
	private SellPlayerService service = new SellPlayerService();
	
	@Test
	void CouldNotSellAPlayerWithNegativeId() {
		Team team = new Team("Magia FC");
	
		int id = -1;

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(team, id));
			assertEquals("The informed player does not exist in this team.", exception.getMessage());
	}
	
	@Test
	void CouldNotSellAPlayerWithId0() {
		Team team = new Team("Zueira FC");
		
		int id = 0;

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(team, id));
			assertEquals("The informed player does not exist in this team.", exception.getMessage());
	}
	
	@Test
	void ShouldsSellAPlayer() throws Exception{
		Team team = new Team("Alegria FC");
		Player player = new Player("Mbappé", "Flamengo", "Atacante", 15, 15);
		
		this.marketDAO.addPlayer(player);
		this.teamDAO.addTeam(team);
		
		BuyPlayerService buyService = new BuyPlayerService();
		buyService.execute(team, player);
		
		int expectedResult = team.getEscalation().size() - 1;
		
		this.service.execute(team, player.getId());
		
		int obtainedResult = this.teamDAO.getTeam(team.getId()).getEscalation().size();
		
		assertEquals(expectedResult, obtainedResult);	
	}
	
	@Test
	void ShouldSellAPlayer2() throws Exception {
		Team team = new Team("Sensacao FC");
		Player player = new Player("Diego", "Flamengo", "Meia", 15, 15);
		
		this.marketDAO.addPlayer(player);
		this.teamDAO.addTeam(team);
		
		BuyPlayerService buyPlayerService = new BuyPlayerService();
		buyPlayerService.execute(team, player);
		
		this.service.execute(team, player.getId());
		
		List<Player> obtainedResult = this.teamDAO.getTeam(team.getId()).getEscalation();
		
		assertFalse(obtainedResult.contains(player));
	}

}
