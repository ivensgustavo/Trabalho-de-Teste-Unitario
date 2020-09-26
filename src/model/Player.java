package model;

import connection.ConnectionFactory;
import dao.utils.IdInitializer;

public class Player {
	
	private int id;
	private static int nextid = new IdInitializer(new ConnectionFactory()).getInitialPlayerId();
	private String name;
	private String club;
	private String position;
	private double value;
	private double points;

	public Player() {
		
	}
	
	public Player(String name, String club, String position, double value, double points) {
		this.incrementId();
		this.name = name;
		this.club = club;
		this.position = position;
		this.value = value;
		this.points = points;
	}
	
	public void incrementId() {
		this.id = nextid;
		nextid++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		Player p = (Player) obj;
		
		return
				this.name.equals(p.getName()) &&
				this.club.equals(p.getClub()) &&
				this.position.equals(p.getPosition()) &&
				Double.compare(this.value, p.getValue()) == 0 &&
				Double.compare(this.points, p.getPoints()) == 0;
		
	}
	
	@Override
	public String toString() {
		return "id: "+this.id+ " | Name: "+this.name + " | Club: "+this.club + " | Position: "+this.position + 
				" | Value: "+this.value + " | Points"+this.points + "\n";
	}
}
