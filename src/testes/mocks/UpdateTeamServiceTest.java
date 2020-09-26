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
import model.Player;
import model.Team;
import services.team.UpdateTeamService;

class UpdateTeamServiceTest {

	@Mock
	private ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);
	
	@Mock
	private Connection conn = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	private TeamDAO teamDAO = null;
	private UpdateTeamService service = null;
	private Team team = new Team("Contagia FC");
	
	
	
	public UpdateTeamServiceTest() throws SQLException {
		this.starTestEnvironment();
	}
	
	private void starTestEnvironment() throws SQLException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(factory.getConnection()).thenReturn(conn);
		Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(stmt);
		Mockito.when(stmt.executeUpdate()).thenReturn(1);
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		//Mockito.when(rs.next()).thenReturn(true, false);
		Mockito.when(rs.getInt("id")).thenReturn(this.team.getId());
		Mockito.when(rs.getString("name")).thenReturn(this.team.getName());
		
		this.teamDAO = new TeamDAO(factory);
		this.service = new UpdateTeamService(this.teamDAO);
	}
	
	@Test
	void couldNotUpdateWithId0() throws SQLException {
		
		Mockito.when(rs.next()).thenReturn(false);
		
		int teamId = 0;
		String name = "Quixadá FC";
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(teamId, name));
			assertEquals("The reporting team does not exist.", exception.getMessage());
	}
	
	@Test
	void couldNotUpdateWithNegativeId() throws SQLException {
		
		Mockito.when(rs.next()).thenReturn(false);
		
		int teamId = -1;
		String name = "Sofrimento FC";
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(teamId, name));
			assertEquals("The reporting team does not exist.", exception.getMessage());
	}
	
	@Test
	void CouldNotUpdateWithEmptyTeamName() throws SQLException {
		
		Mockito.when(rs.next()).thenReturn(true, false);
	
		String name = "";
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(team.getId(), name));
			assertEquals("Team name invalid.", exception.getMessage());
	}
	
	@Test 
	void CouldNotUpdateWithNullTeamName() throws SQLException {
		
		Mockito.when(rs.next()).thenReturn(true, false);
		
		String name = null;
		
		Exception exception = assertThrows(NullPointerException.class, () ->
			this.service.execute(team.getId(), name));
			assertEquals("Team name null.", exception.getMessage());
	}
	
	@Test
	void shouldUpdateATeam() throws SQLException{
		
		Mockito.when(rs.next()).thenReturn(true, false, false, false, true, false);
		Mockito.when(rs.getInt("id")).thenReturn(team.getId());
		Mockito.when(rs.getString("name")).thenReturn("Neymar FC");
		
		Team updatedTeam = new Team("Neymar FC");
		updatedTeam.setId(team.getId());
		
		this.service.execute(team.getId(), "Neymar FC");
		
		Team expectedResult = updatedTeam;
		Team obtainedresult = this.teamDAO.getTeam(team.getId());
		
		assertEquals(expectedResult, obtainedresult);
	}

}
