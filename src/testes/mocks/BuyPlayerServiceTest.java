package testes.mocks;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import connection.ConnectionFactory;
import dao.MarketDAO;
import dao.TeamDAO;
import model.Player;
import model.Team;
import services.team.BuyPlayerService;


class BuyPlayerServiceTest {
	
	@Mock
	private ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);
	
	@Mock
	private Connection conn = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	private TeamDAO teamDAO = null;
	private MarketDAO marketDAO;
	BuyPlayerService service = new BuyPlayerService();
	private Player player = new Player("Mbappé", "Flamengo", "Atacante", 15, 15);
	private List<Player> listPlayers = new ArrayList<Player>();

	
	public BuyPlayerServiceTest() throws SQLException {
		this.starTestEnvironment();
	}
	
	private void starTestEnvironment() throws SQLException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(factory.getConnection()).thenReturn(conn);
		Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(stmt);
		Mockito.when(stmt.executeUpdate()).thenReturn(1);
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(false, true, true, false);
		Mockito.when(rs.getInt("id")).thenReturn(this.player.getId());
		Mockito.when(rs.getString("name")).thenReturn(this.player.getName());
		Mockito.when(rs.getString("club")).thenReturn(this.player.getClub());
		Mockito.when(rs.getString("position")).thenReturn(this.player.getPosition());
		Mockito.when(rs.getDouble("value")).thenReturn(this.player.getValue());
		Mockito.when(rs.getDouble("points")).thenReturn(this.player.getPoints());
		
		
		this.teamDAO = new TeamDAO(factory);
		this.marketDAO = new MarketDAO(factory);
		this.service = new BuyPlayerService(this.teamDAO);
		this.listPlayers.add(player);
	}
	
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
		
		Mockito.doNothing().when(factory).closeConnection(conn, stmt);
		
		
		Team team = new Team("Barbaridade FC");
		Player newPlayer = new Player("Zyech", "Flamengo", "Atacante", 15, 15);
		
		this.teamDAO.addTeam(team);
		this.marketDAO.addPlayer(newPlayer);
		
		int expectedResult = team.getEscalation().size() + 1;
		
		this.service.execute(team, newPlayer);
		
		int obtainedResult = this.teamDAO.getTeam(team.getId()).getEscalation().size();
		
		System.out.println("esperado " +expectedResult);
		System.out.println("obtido: "+obtainedResult);
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
