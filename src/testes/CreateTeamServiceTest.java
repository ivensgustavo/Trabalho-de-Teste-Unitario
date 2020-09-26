package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import connection.ConnectionFactory;
import dao.TeamDAO;
import model.Team;
import services.team.CreateTeamService;

class CreateTeamServiceTest {
	
	private TeamDAO teamDAO = new TeamDAO(new ConnectionFactory());
	private static Team team = new Team("Ivens FC");
	CreateTeamService service = new CreateTeamService();
	
	public CreateTeamServiceTest() {
		this.starTestEnvironment();
	}
	
	private void starTestEnvironment() {
		if(this.teamDAO.getTeam(team.getId()) == null) this.teamDAO.addTeam(team);
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
	
	@Test 
	void checkAddTeam() {
		
		Team expectedResult = this.service.execute("Ivens Gustavo FC");
		int id = expectedResult.getId();
		Team obtaineidResult = this.teamDAO.getTeam(id);
		
		assertEquals(expectedResult, obtaineidResult);
	}

}
