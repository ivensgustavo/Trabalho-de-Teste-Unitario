package testes.mocks;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import connection.ConnectionFactory;
import dao.MarketDAO;
import model.Player;
import services.player.CreatePlayerService;
import utils.ClubInitiator;
import utils.PositionsInitiator;


class CreatePlayerServiceTest {
	
	@Mock
	private ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);
	
	@Mock
	private Connection conn = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	
	private MarketDAO marketDAO = null;
	private CreatePlayerService service = null;
	private Player player = new Player("Pedro Fonseca", "Bahia", "Meia", 5, 7);
	private static ClubInitiator clubInitiator = null;
	private static PositionsInitiator positionsInitiator = null;
	
	public CreatePlayerServiceTest() throws SQLException {
		this.startTestEnvironment();
	}
	
	private void startTestEnvironment() throws SQLException {
		
		if(clubInitiator == null) {
			clubInitiator = new ClubInitiator();
			clubInitiator.createClubs();
		}
		
		if(positionsInitiator == null) {
			positionsInitiator = new PositionsInitiator();
			positionsInitiator.createPositions();
		}
		
		MockitoAnnotations.initMocks(this);
		Mockito.when(factory.getConnection()).thenReturn(conn);
		Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(stmt);
		Mockito.when(stmt.executeUpdate()).thenReturn(1);
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(true, false);
		Mockito.when(rs.getInt("id")).thenReturn(this.player.getId());
		Mockito.when(rs.getString("name")).thenReturn(this.player.getName());
		Mockito.when(rs.getString("club")).thenReturn(this.player.getClub());
		Mockito.when(rs.getString("position")).thenReturn(this.player.getPosition());
		Mockito.when(rs.getDouble("value")).thenReturn(this.player.getValue());
		Mockito.when(rs.getDouble("points")).thenReturn(this.player.getPoints());
		this.marketDAO = new MarketDAO(factory);
		this.service = new CreatePlayerService(marketDAO);
		
	}
	
	@Test
	void checkInvalidPlayerName() throws SQLException {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(" Ivens", "Guarani", "Atacante", 15, 10));
		assertEquals("Player name invalid.", exception.getMessage());
	}
	@Test
	void checkInvalidPlayerName2() throws SQLException {
		
		
		
		Exception exception = assertThrows(NullPointerException.class, () ->
			service.execute(null, "Guarani", "Atacante", 15, 10));
		assertEquals("Player name null.", exception.getMessage());
	}
	
	@Test
	void checkInvalidClub() throws SQLException {
		
	
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Erivaldo", "Guarani", "Atacante", 15, 10));
		assertEquals("Invalid club.", exception.getMessage());
	}
	
	@Test
	void checkInvalidPosition() throws SQLException {
		
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Erivaldo", "Flamengo", "Volante", 15, 10));
			assertEquals("Invalid position.", exception.getMessage());
		
	}
	
	@Test
	void checkInvalidPlayerValue() throws SQLException {
		
		
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Erivaldo", "Flamengo", "Atacante", -2, 0));
			assertEquals("Invalid player value.", exception.getMessage());
		
	}
	
	@Test
	void checkInvalidPlayerValue2() throws SQLException {
		
		

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute("Erivaldo", "Flamengo", "Atacante", 0, 0));
			assertEquals("Invalid player value.", exception.getMessage());
		
	}
	
	@Test 
	void checkExistentPlayer() throws SQLException {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute("Pedro Fonseca", "Bahia", "Meia", 5, 7));
			assertEquals("This player already added.", exception.getMessage());
		
	}
	
	private void setMocksForAddPlayer(int id) throws SQLException {
		Mockito.when(rs.next()).thenReturn(true, false);
		Mockito.when(rs.getInt("id")).thenReturn(id);
		Mockito.when(rs.getString("name")).thenReturn("Gustavo Ivens");
		Mockito.when(rs.getString("club")).thenReturn("Flamengo");
		Mockito.when(rs.getString("position")).thenReturn("Atacante");
		Mockito.when(rs.getDouble("value")).thenReturn(15.0);
		Mockito.when(rs.getDouble("points")).thenReturn(12.0);
	}
	
	@Test
	void checkAddPlayer() throws SQLException {
	
		Player expectedResult = service.execute("Gustavo Ivens", "Flamengo", "Atacante", 15.0, 12.0); 
		int id = expectedResult.getId();
		
		this.setMocksForAddPlayer(id);
		
		Player obtainedResult = marketDAO.getPlayer(id);
		
		assertEquals(expectedResult, obtainedResult);
	}

}
