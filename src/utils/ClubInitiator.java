package utils;

public class ClubInitiator {
	
	private ClubList clubs = ClubList.getInstance();

	public ClubInitiator() {
		
	}
	
	public void createClubs() {
		clubs.addClub("Athletico Paranaense");
		clubs.addClub("Atl�tico Goianiense");
		clubs.addClub("Atl�tico Mineiro");
		clubs.addClub("Bahia");
		clubs.addClub("Botafogo");
		clubs.addClub("Cear�");
		clubs.addClub("Corinthians");
		clubs.addClub("Coritiba");
		clubs.addClub("Flamengo");
		clubs.addClub("Fluminense");
		clubs.addClub("Fortaleza");
	}
}
