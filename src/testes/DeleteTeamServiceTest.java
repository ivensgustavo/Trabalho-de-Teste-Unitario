package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import connection.ConnectionFactory;
import dao.TeamDAO;
import model.Team;
import services.team.DeleteTeamService;

class DeleteTeamServiceTest {

	private TeamDAO teamDAO = new TeamDAO(new ConnectionFactory());
	private DeleteTeamService service = new DeleteTeamService();
	
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
	void shouldDeleteATeam() {
		
		Team team = new Team("Zoeira FC");
		int id = team.getId();
		this.teamDAO.addTeam(team);
		
		boolean obtainedResult = service.execute(id);
		
		assertTrue(obtainedResult);
	}

}
