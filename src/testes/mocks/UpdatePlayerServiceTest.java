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
import services.player.UpdatePlayerService;
import utils.ClubInitiator;
import utils.PositionsInitiator;

class UpdatePlayerServiceTest {
	

	@Mock
	private ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);
	
	@Mock
	private Connection conn = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	private MarketDAO marketDAO = null;
	private UpdatePlayerService service = null;
	private Player player = new Player("Pedro Rocha", "Ceará", "Zagueiro", 5, 7);
	private static ClubInitiator clubInitiator = null;
	private static PositionsInitiator positionsInitiator = null;
	
	
	public UpdatePlayerServiceTest() throws SQLException {
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
		Mockito.when(rs.next()).thenReturn(true, true, false);
		Mockito.when(rs.getInt("id")).thenReturn(this.player.getId());
		Mockito.when(rs.getString("name")).thenReturn(this.player.getName());
		Mockito.when(rs.getString("club")).thenReturn(this.player.getClub());
		Mockito.when(rs.getString("position")).thenReturn(this.player.getPosition());
		Mockito.when(rs.getDouble("value")).thenReturn(this.player.getValue());
		Mockito.when(rs.getDouble("points")).thenReturn(this.player.getPoints());
		this.marketDAO = new MarketDAO(factory);
		this.service = new UpdatePlayerService(marketDAO);
		System.out.println(factory);
	}
	
	@Test
	void checkInvalidPlayerName() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(player.getId(), "", "Bragantino", "Atacante", 15, 10));
		assertEquals("Player name invalid.", exception.getMessage());
	}
	
	@Test
	void checkInvalidPlayerName2() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(player.getId(), "     ", "Bragantino", "Atacante", 15, 10));
		assertEquals("Player name invalid.", exception.getMessage());
	}
	
	@Test
	void checkInvalidPlayerName3() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(player.getId(), "ivens&gustavo", "Bragantino", "Atacante", 15, 10));
		assertEquals("Player name invalid.", exception.getMessage());
	}
	
	@Test
	void checkInvalidPlayerName4() {
		
		Exception exception = assertThrows(NullPointerException.class, () ->
			this.service.execute(player.getId(), null, "Bragantino", "Atacante", 15, 10));
		assertEquals("Player name null.", exception.getMessage());
	}
	
	@Test
	void checkInvalidClub() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(player.getId(), "Erivaldo", "Ponte Preta", "Atacante", 15, 10));
		assertEquals("Invalid club.", exception.getMessage());
	}
	

	@Test
	void checkInvalidPosition() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(player.getId(), "Erivaldo", "Flamengo", "Volante", 15, 10));
			assertEquals("Invalid position.", exception.getMessage());
		
	}
	
	
	@Test
	void checkInvalidPlayerValue() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(player.getId(), "Erivaldo", "Flamengo", "Atacante", -2, 0));
			assertEquals("Invalid player value.", exception.getMessage());
		
	}
	
	@Test
	void checkInvalidPlayerValue2() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(player.getId(), "Erivaldo", "Flamengo", "Atacante", 0, 0));
			assertEquals("Invalid player value.", exception.getMessage());
		
	}
	
	
	@Test 
	void checkExistentPlayer() {
	
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(player.getId(), "Pedro Rocha", "Ceará", "Zagueiro", 5, 7));
			assertEquals("This player already exists.", exception.getMessage());
		
	}
	
	private void setMocksForUpdatePlayer() throws SQLException {
		Mockito.when(rs.next()).thenReturn(true, false, true);
		Mockito.when(rs.getInt("id")).thenReturn(this.player.getId());
		Mockito.when(rs.getString("name")).thenReturn("Pedro Rocha");
		Mockito.when(rs.getString("club")).thenReturn("Coritiba");
		Mockito.when(rs.getString("position")).thenReturn("Zagueiro");
		Mockito.when(rs.getDouble("value")).thenReturn(20.0);
		Mockito.when(rs.getDouble("points")).thenReturn(7.0);
	}
	
	@Test
	void checkUpdatePlayer() throws SQLException {
		
		Player updatedPlayer = new Player("Pedro Rocha", "Coritiba", "Zagueiro", 20, 7);
		updatedPlayer.setId(player.getId());
		
		this.setMocksForUpdatePlayer();
		
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
