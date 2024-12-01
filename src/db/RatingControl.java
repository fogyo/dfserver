package db;

import java.util.ArrayList;

import addition.PrizeControl;
import server.Server;

public class RatingControl {
	
	private int pl_id;
	private String leaderboard_places;
	private String current_place;
	private double[] prize_arr;
	private String leaderboard = "";
	
	public RatingControl(int pl_id) {
		this.pl_id = pl_id;
	}
	
	public void run() {
		leaderboard_places = Server.db.pl_rating(Server.connect, pl_id);
		current_place = Server.db.get_current_place(Server.connect, pl_id);
		int players_num = Server.db.tournament_kol(Server.connect, pl_id);
		double entry_cost = Server.db.entry_cost(Server.connect, pl_id);
		PrizeControl pc = new PrizeControl(players_num, entry_cost); 
		prize_arr = pc.prize_raspr(players_num, entry_cost);
		for (int i=0 ; i<prize_arr.length; i++) {
			leaderboard = leaderboard + leaderboard_places.split(",")[i]+" "+String.valueOf(prize_arr[i])+",";
		}
		Server.logs.log("Leaderboard composed by "+pl_id);
	}
	
	public String getRes() {
		return leaderboard+";"+current_place;
	}

}
