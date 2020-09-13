package utils;

public class PositionsInitiator {
	
	private PositionsList positions = PositionsList.getInstance();
	
	public PositionsInitiator() {
		
	}
	
	public void createPositions() {
		this.positions.addPosition("Goleiro");
		this.positions.addPosition("Zagueiro");
		this.positions.addPosition("Lateral");
		this.positions.addPosition("Meia");
		this.positions.addPosition("Atacante");
	}

}
