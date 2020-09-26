package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import connection.ConnectionFactory;
import dao.MarketDAO;
import dao.TeamDAO;
import model.Player;
import model.Team;
import services.team.BuyPlayerService;


class BuyPlayerServiceTest {
	
	private TeamDAO teamDAO = new TeamDAO(new ConnectionFactory());
	private MarketDAO marketDAO = new MarketDAO(new ConnectionFactory());
	BuyPlayerService service = new BuyPlayerService();

	@Test
	void CouldNotBuyMoreThan11Players() {
		
		Team team = new Team("Ousadia FC");
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
		
		Exception exception = assertThrows(Exception.class, () ->
			this.service.execute(team, newPlayer));
			assertEquals("There are already 11 players on this team.", exception.getMessage());
		
	}
	
	@Test 
	void CouldNotBuyTheSamePlayer(){
		Team team = new Team("Alegria FC");
		team.getEscalation().add(new Player("Messi", "Flamengo", "Atacante", 20, 10));
		
		Player newPlayer = new Player("Messi", "Flamengo", "Atacante", 20, 10);
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(team, newPlayer));
			assertEquals("The player is already on the team.", exception.getMessage());
	}
	
	@Test
	void ShouldBuyAPlayer() throws Exception {
		Team team = new Team("Barbaridade FC");
		Player newPlayer = new Player("Zyech", "Flamengo", "Atacante", 15, 15);
		
		this.teamDAO.addTeam(team);
		this.marketDAO.addPlayer(newPlayer);
		
		int expectedResult = team.getEscalation().size() + 1;
		
		this.service.execute(team, newPlayer);
		
		int obtainedResult = this.teamDAO.getTeam(team.getId()).getEscalation().size();
		
		assertEquals(expectedResult, obtainedResult);	
	}
	
	@Test
	void ShouldBuyAPlayer2() throws Exception {
		Team team = new Team("Amizade FC");
		Player newPlayer = new Player("Mbappé", "Flamengo", "Atacante", 15, 15);
		
		this.teamDAO.addTeam(team);
		this.marketDAO.addPlayer(newPlayer);
		
		
		this.service.execute(team, newPlayer);
		
		List<Player> obtainedResult = this.teamDAO.getTeam(team.getId()).getEscalation();
		
		assertTrue(obtainedResult.contains(newPlayer));
	}

}
