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
import dao.TeamDAO;
import model.Team;
import services.team.DeleteTeamService;

class DeleteTeamServiceTest {
	
	@Mock
	private ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);
	
	@Mock
	private Connection conn = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);

	private TeamDAO teamDAO = null;
	private DeleteTeamService service = null;
	private Team team = new Team("Zoeira FC");
	
	public DeleteTeamServiceTest() throws SQLException {
		this.startTestEnvironment();
	}
	
	private void startTestEnvironment() throws SQLException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(factory.getConnection()).thenReturn(conn);
		Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(stmt);
		Mockito.when(stmt.executeUpdate()).thenReturn(1);
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(false);
		Mockito.when(rs.getInt("id")).thenReturn(this.team.getId());
		Mockito.when(rs.getString("name")).thenReturn(this.team.getName());
		
		this.teamDAO = new TeamDAO(factory);
		this.service = new DeleteTeamService(teamDAO);
	}
	
	@Test
	void couldNotDeleteWithId0() {
		
		int id = 0;
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(id));
			assertEquals("The reporting team does not exist.", exception.getMessage());
	}
	
	@Test
	void couldNotDeleteWithNegativeId() {
		
		int id = -1;
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			service.execute(id));
			assertEquals("The reporting team does not exist.", exception.getMessage());
	}
	
	@Test
	void shouldDeleteATeam() throws SQLException {
		
		Mockito.when(rs.next()).thenReturn(true, false);
		
		Team team = new Team("Zoeira FC");
		int id = team.getId();
		
		this.teamDAO.addTeam(team);
		
		boolean obtainedResult = service.execute(id);
		
		assertTrue(obtainedResult);
	}

}
