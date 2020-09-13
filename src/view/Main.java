package view;



import java.util.Scanner;

import connection.ConnectionFactory;
import control.MarketController;
import control.TeamController;
import model.Player;
import model.Team;
import utils.ClubInitiator;
import utils.PositionsInitiator;

public class Main {
	
	private static MarketController marketController = new MarketController();
	private static TeamController teamController = new TeamController();
	static Scanner scan = new java.util.Scanner(System.in);
	
	private static void startValuesClubAndPosition() {
		ClubInitiator clubInitiator = new ClubInitiator();
		PositionsInitiator positionsInitiator = new PositionsInitiator();
		
		clubInitiator.createClubs();
		positionsInitiator.createPositions();
	}
	
	private static void showAllAllPlayers() {
		System.out.println("-------------------MARKET-------------------");
		for (Player p : marketController.getAllPlayers()) {
			System.out.println(p);
		}
		System.out.println("---------------------------------------------");
	}
	
	private static void printMenu() {
		
		while(true) {
		
			String op = "";
			
			System.out.println("Choose an option:");
			System.out.println("1- Show all players in the market");
			System.out.println("2- Add player to market");
			System.out.println("3- Update market player");
			System.out.println("4- Remove player from market");
			System.out.println("5- Show all teams");
			System.out.println("6- Add a team");
			System.out.println("7- Update a team");
			System.out.println("8- Delete a team");
			System.out.println("9- Buy player");
			System.out.println("10- Sell player");
			System.out.println("11- Shut down system.");
			
			op = scan.next();
			
			switch (op) {
			case "1": {
				showAllAllPlayers();
				break;
			}
			case "2": {
				addPlayer();
				break;
			}
			case "3": {
				updatePlayer();
				break;
			}
			case "4": {
				deletePlayer();
				break;
			}
			case "5": {
				showAllTeams();
				break;
			}
			case "6": {
				addTeam();
				break;
			}
			case "7": {
				updateTeam();
				break;
			}
			case "8": {
				deleteTeam();
				break;
			}
			case "9": {
				buyPlayer();
				break;
			}
			case "10": {
				sellPlayer();
				break;
			}
			case "11": {
				System.exit(0);
				break;
			}
			default:
				System.out.println("Invalid option. Check the options and try again");
				System.out.println("---------------------------------");
			}
		}
	}
	
	private static void addPlayer() {
		String name, club, position, value, points;
		
		System.out.println("Enter the player's name:");
		name = scan.next();
		System.out.println("Enter the player's club:");
		club = scan.next();
		System.out.println("Enter the player's position:");
		position = scan.next();
		System.out.println("Enter the player's value:");
		value = scan.next();
		System.out.println("Enter the player's points:");
		points = scan.next();
		
		boolean res = marketController.addPlayer(name, club, position, value, points);
		
		if(res) {
			System.out.println("Player successfully added");
			System.out.println("---------------------------------");
		}else {
			System.out.println(marketController.getErrorMessage());
			System.out.println("---------------------------------");
		}
	}
	
	private static void updatePlayer() {
		String id, name, club, position, value, points;
		
		System.out.println("Enter the player's id:");
		id = scan.next();
		System.out.println("Enter the player's name:");
		name = scan.next();
		System.out.println("Enter the player's club:");
		club = scan.next();
		System.out.println("Enter the player's position:");
		position = scan.next();
		System.out.println("Enter the player's value:");
		value = scan.next();
		System.out.println("Enter the player's points:");
		points = scan.next();
		
		boolean res = marketController.updatePlayer(id, name, club, position, value, points);
		
		if(res) {
			System.out.println("Player successfully updated.");
			System.out.println("---------------------------------");
		}else {
			System.out.println(marketController.getErrorMessage());
			System.out.println("---------------------------------");
		}
	}
	
	private static void deletePlayer() {
		String id;
		System.out.println("Enter the player's id:");
		id = scan.next();
		
		boolean res = marketController.deletePlayer(id);
		
		if(res) {
			System.out.println("Player successfully deleted.");
			System.out.println("---------------------------------");
		}else {
			System.out.println(marketController.getErrorMessage());
			System.out.println("---------------------------------");
		}
	}
	
	private static void showAllTeams() {
		System.out.println("----------------------------------ALL TEAMS--------------------------------------");
		for (Team t: teamController.getALLTeams()) {
			System.out.println(t);
		}
	}
	
	private static void addTeam() {
		String name;
		
		System.out.println("Enter team name:");
		if(scan.hasNextLine()) {
			scan.nextLine();
		}
		name = scan.nextLine();
		System.out.println("time: "+name);
		
		boolean res = teamController.addTeam(name);
		
		if(res) {
			System.out.println("Team successfully added");
			System.out.println("---------------------------------");
		}else {
			System.out.println(teamController.getErrorMessage());
			System.out.println("---------------------------------");
		}
		
	}
	
	private static void updateTeam() {
		String id, name;
		
		System.out.println("Enter team id:");
		id = scan.next();
		
		System.out.println("Enter team name:");
		if(scan.hasNextLine()) {
			scan.nextLine();
		}
		name = scan.nextLine();
		System.out.println("time: "+name);
		
		boolean res = teamController.updateTeam(id, name);
		
		if(res) {
			System.out.println("Team successfully updated.");
			System.out.println("---------------------------------");
		}else {
			System.out.println(teamController.getErrorMessage());
			System.out.println("---------------------------------");
		}
		
	}
	
	private static void deleteTeam() {
		String id;
		System.out.println("Enter the team id:");
		id = scan.next();
		
		boolean res = teamController.deleteTeam(id);
		
		if(res) {
			System.out.println("Team successfully deleted.");
			System.out.println("---------------------------------");
		}else {
			System.out.println(teamController.getErrorMessage());
			System.out.println("---------------------------------");
		}
	}
	
	private static void buyPlayer() {
		String teamId, playerId;
		System.out.println("Enter the id of the player you want to buy");
		playerId = scan.next();
		System.out.println("Enter the id of the team that wants to buy the player");
		teamId = scan.next();
		
		boolean res = teamController.buyPlayer(teamId, playerId);
		
		if(res) {
			System.out.println("Player successfully purchased.");
			System.out.println("---------------------------------");
		}else {
			System.out.println(teamController.getErrorMessage());
			System.out.println("---------------------------------");
		}
	}
	
	private static void sellPlayer() {
		String teamId, playerId;
		System.out.println("Enter the id of the player you want to sell");
		playerId = scan.next();
		System.out.println("Enter the id of the team that wants to sell the player");
		teamId = scan.next();
		
		boolean res = teamController.sellPlayer(teamId, playerId);
		
		if(res) {
			System.out.println("Player successfully sold");
			System.out.println("---------------------------------");
		}else {
			System.out.println(teamController.getErrorMessage());
			System.out.println("---------------------------------");
		}
	}
	
	public  static void main(String[] args) {
		startValuesClubAndPosition();
		printMenu();
		
		//System.out.println(ConnectionFactory.getConnection());
	}
	
	
	

}
