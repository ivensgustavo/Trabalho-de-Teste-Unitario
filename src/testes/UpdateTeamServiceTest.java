package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dao.TeamDAO;
import model.Team;
import services.team.UpdateTeamService;

class UpdateTeamServiceTest {

	private TeamDAO teamDAO = TeamDAO.getInstance();
	private static Team team = new Team("Contagia FC");
	private UpdateTeamService service = new UpdateTeamService();
	
	
	public UpdateTeamServiceTest() {
		this.starTestEnvironment();
	}
	
	private void starTestEnvironment() {
		if(this.teamDAO.getTeam(team.getId()) == null) this.teamDAO.addTeam(team);
	}
	
	@Test
	void couldNotUpdateWithId0() {
		
		int teamId = 0;
		String name = "Quixadá FC";
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(teamId, name));
			assertEquals("The reporting team does not exist.", exception.getMessage());
	}
	
	@Test
	void couldNotUpdateWithNegativeId() {
		
		int teamId = -1;
		String name = "Sofrimento FC";
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(teamId, name));
			assertEquals("The reporting team does not exist.", exception.getMessage());
	}
	
	@Test
	void CouldNotUpdateWithEmptyTeamName() {
	
		String name = "";
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			this.service.execute(team.getId(), name));
			assertEquals("Team name invalid.", exception.getMessage());
	}
	
	@Test 
	void CouldNotUpdateWithNullTeamName() {
		
		String name = null;
		
		Exception exception = assertThrows(NullPointerException.class, () ->
			this.service.execute(team.getId(), name));
			assertEquals("Team name null.", exception.getMessage());
	}
	
	@Test
	void shouldUpdateATeam(){
		
		Team updatedTeam = new Team("Neymar FC");
		updatedTeam.setId(team.getId());
		
		this.service.execute(team.getId(), "Neymar FC");
		
		Team expectedResult = updatedTeam;
		Team obtainedresult = this.teamDAO.getTeam(team.getId());
		
		assertEquals(expectedResult, obtainedresult);
	}

}
