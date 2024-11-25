package db;

import server.Server;

public class PlayerInteraction {
	
	private boolean success = true;
	
	public void run(int pl_id, String coordinates) {
		String[] opp_coords = Server.db.get_local_map(Server.connect, pl_id).split(" ");
		String[] template_squares = Server.db.get_template_by_pl_id(Server.connect, pl_id).split(",");
		int map_id = Server.db.get_map_id(Server.connect, pl_id);
		int sq = 0;
		for (int i = 0; i<24; i++) {
			if (sq == 12){
				sq+=1;
			}
			if (coordinates.equals(opp_coords[i])){
				int square_to_destroy = Integer.parseInt(template_squares[sq]);
				int id_to_destroy = Server.db.bot_or_player(Server.connect, map_id, square_to_destroy);
				if (id_to_destroy<0) {
					Server.db.bot_destruction(Server.connect, id_to_destroy, pl_id);
				}
				else {
					if (Server.db.check_success_of_attack(Server.connect, id_to_destroy, pl_id)) {
						Server.db.pl_destruction(Server.connect, id_to_destroy, pl_id);
					}
					else {
						Server.db.unsuccessful_attack(Server.connect, pl_id);
						success = false;
					}
				}
			}
		}
	}
	
	public String getRes() {
		if (success) {
			return "Destroyed";
		}
		else {
			return "U'r victim now";
		}
	}

}
