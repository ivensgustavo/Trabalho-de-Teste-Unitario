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
import services.player.DeletePlayerService;
import utils.ClubInitiator;
import utils.PositionsInitiator;

class DeletePlayerServiceTest {
	
	@Mock
	private ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);
	
	@Mock
	private Connection conn = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	private static ClubInitiator clubInitiator = null;
	private static PositionsInitiator positionsInitiator = null;
	private DeletePlayerService service = null;
	private MarketDAO marketDAO = null;
	private Player player = new Player("Micael", "Flamengo", "Atacante", 10, 12);
	
	public DeletePlayerServiceTest() throws SQLException {
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
		Mockito.when(rs.next()).thenReturn(false);
		Mockito.when(rs.getInt("id")).thenReturn(player.getId());
		Mockito.when(rs.getString("name")).thenReturn(this.player.getName());
		Mockito.when(rs.getString("club")).thenReturn(this.player.getClub());
		Mockito.when(rs.getString("position")).thenReturn(this.player.getPosition());
		Mockito.when(rs.getDouble("value")).thenReturn(this.player.getValue());
		Mockito.when(rs.getDouble("points")).thenReturn(this.player.getPoints());
		
		this.marketDAO = new MarketDAO(factory);
		this.service = new DeletePlayerService(marketDAO);
		
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
	void checkDeletePlayer() throws SQLException {

		Mockito.when(rs.next()).thenReturn(true, false);
		
		int id = this.player.getId();
		
		this.marketDAO.addPlayer(this.player);
		
		boolean obtainedResult = this.service.execute(id);
		
		assertTrue(obtainedResult);
	}

}
