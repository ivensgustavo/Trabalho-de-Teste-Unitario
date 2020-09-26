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
import services.team.CreateTeamService;

class CreateTeamServiceTest {
	
	@Mock
	private ConnectionFactory factory = Mockito.mock(ConnectionFactory.class);
	
	@Mock
	private Connection conn = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	private TeamDAO teamDAO = null;
	private CreateTeamService service = null;
	private Team team = new Team("Ivens FC");
	
	public CreateTeamServiceTest() throws SQLException {
		this.starTestEnvironment();
	}
	
	private void starTestEnvironment() throws SQLException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(factory.getConnection()).thenReturn(conn);
		Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(stmt);
		Mockito.when(stmt.executeUpdate()).thenReturn(1);
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(true, false);
		Mockito.when(rs.getInt("id")).thenReturn(this.team.getId());
		Mockito.when(rs.getString("name")).thenReturn(this.team.getName());
		
		this.teamDAO = new TeamDAO(factory);
		this.service = new CreateTeamService(this.teamDAO);
	}
	
	@Test
	void checkInvalidTeamName() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(""));
		assertEquals("Team name invalid.", exception.getMessage());
	}
	
	@Test 
	void checkNullTeamName() {
		
		Exception exception = assertThrows(NullPointerException.class, () ->
			this.service.execute(null));
			assertEquals("Team name null.", exception.getMessage());
	}
	
	@Test 
	void checkExistentTeam() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute("Ivens FC"));
			assertEquals("This team already exists.", exception.getMessage());
	}
	
	private void setMocksForAddTeam() throws SQLException {
		Mockito.when(rs.next()).thenReturn(false, true, false);
		Mockito.when(rs.getString("name")).thenReturn("Ivens Gustavo FC");
	}
	
	@Test 
	void checkAddTeam() throws SQLException {
		
		this.setMocksForAddTeam();
		
		Team expectedResult = this.service.execute("Ivens Gustavo FC");
		int id = expectedResult.getId();
		Team obtaineidResult = this.teamDAO.getTeam(id);
		
		assertEquals(expectedResult, obtaineidResult);
	}

}
