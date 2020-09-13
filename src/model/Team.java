package model;

import java.util.ArrayList;
import java.util.List;

import dao.utils.IdInitializer;

public class Team {

	private int id;
	private static int nextid = new IdInitializer().getInitialTeamId();
	private String name;
	private List<Player> escalation;
	private double totalPoints;
	
	public Team() {
		
	}

	public Team(String name) {
		this.incrementId();
		this.name = name;
		this.escalation = new ArrayList<Player>();
		this.totalPoints = 0;
	}
	
	private void incrementId() {
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
	public List<Player> getEscalation() {
		return escalation;
	}
	public void setEscalation(List<Player> escalation) {
		this.escalation = escalation;
	}
	public double getTotalPoints() {
		this.totalPoints  = 0;
		
		for(Player p: this.escalation) {
			this.totalPoints += p.getPoints();
		}
		
		return this.totalPoints;
	}
	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}
	
	public Player getPlayer(int id) {
		for (Player p: this.escalation) {
			if(p.getId() == id) return p;
		}
		
		return null;
	}
	
	public boolean buyPlayer(Player player) {
		return this.escalation.add(player);
	}
	
	public boolean sellPlayer(int id) {
		for(Player p: this.escalation) {
			if(p.getId() == id){
				this.escalation.remove(p);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		Team t = (Team) obj;
		
		return this.name.equals(t.getName());
	}
	
	@Override
	public String toString() {

		String exit = "---------------------------------------------------------------------------------\n"+
				"id:" + this.id+" | Name:"+this.name+" | Total Points: "+this.getTotalPoints()+"\nEscalation: \n";
		for (Player p : this.escalation) {
			exit += p;
		}
		
		exit += "---------------------------------------------------------------------------------";
		
		return exit;
	}
	
}
