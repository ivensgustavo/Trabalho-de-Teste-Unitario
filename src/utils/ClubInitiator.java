package utils;

public class ClubInitiator {
	
	private ClubList clubs = ClubList.getInstance();

	public ClubInitiator() {
		
	}
	
	public void createClubs() {
		clubs.addClub("Athletico Paranaense");
		clubs.addClub("Atlético Goianiense");
		clubs.addClub("Atlético Mineiro");
		clubs.addClub("Bahia");
		clubs.addClub("Botafogo");
		clubs.addClub("Ceará");
		clubs.addClub("Corinthians");
		clubs.addClub("Coritiba");
		clubs.addClub("Flamengo");
		clubs.addClub("Fluminense");
		clubs.addClub("Fortaleza");
	}
}
