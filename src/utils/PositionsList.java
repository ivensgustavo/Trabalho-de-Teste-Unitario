package utils;

import java.util.HashMap;
import java.util.Map;

public class PositionsList {

	private Map<String, String> positions = new HashMap<String, String>();
	private static PositionsList uniqueInstance = null;
	
	private PositionsList() {
		
	}
	
	public static PositionsList getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new PositionsList();
		}
		
		return uniqueInstance;
	}
	
	public void addPosition(String pos) {
		this.positions.put(pos, pos);
	}
	
	public boolean checkPositionIsPresent(String pos) {
		return this.positions.containsKey(pos);
	}
}
