package utils;

import java.util.HashMap;
import java.util.Map;

public class ClubList {

	private Map<String, String> clubs = new HashMap<String, String>();
	private static ClubList uniqueInstance = null;
	
	private ClubList() {
		
	}
	
	public static ClubList getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new ClubList();
		}
		
		return uniqueInstance;
	}
	
	public void addClub(String club) {
		this.clubs.put(club,  club);
	}
	
	public boolean checkClubIsPresent(String club) {	
		return this.clubs.containsKey(club);
	}
}
