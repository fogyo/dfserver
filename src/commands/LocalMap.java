package commands;

import java.util.HashMap;

import server.Server;

public class LocalMap {

	
	private HashMap<Integer, String> templateLocalMapCoords =  new HashMap<>() {{
		put(1, "-20,20");
		put(2, "-10,20");
		put(3, "0,20");
		put(4, "10,20");
		put(5, "20,20");
		put(6, "-20,10");
		put(7, "-10,10");
		put(8, "0,10");
		put(9, "10,10");
		put(10, "20,10");
		put(11, "-20,0");
		put(12, "-10,0");
		put(13, "0,0");
		put(14, "10,0");
		put(15, "20,0");
		put(16, "-20,-10");
		put(17, "-10,-10");
		put(18, "0,-10");
		put(19, "10,-10");
		put(20, "20,-10");
		put(21, "-20,-20");
		put(22, "-10,-20");
		put(23, "0,-20");
		put(24, "10,-20");
		put(25, "20,-20");
	}};
	
	public void createLocalMap(int pl_id) {
		String player_template = Server.db.get_template_by_pl_id(Server.connect, pl_id);
		String pl_globe_square[] = player_template.split(",");
		String[] pl_ids = Server.db.get_pl_ids(Server.connect, pl_id).split(" ");
		HashMap<Integer, String> square_x_y = new HashMap<>(); 
		//создадим хэшмап square - (x,y) 
		for (String p : pl_ids){
			int user_id = Integer.parseInt(p);
			if (user_id < 0) {
				String res = Server.db.get_coords_from_bots(Server.connect, user_id);
				square_x_y.put(Integer.parseInt(res.split(",")[0]), res.split(",")[1]+","+res.split(",")[2]);
			}
			else {
				String res = Server.db.get_coords_from_player(Server.connect, user_id);
				square_x_y.put(Integer.parseInt(res.split(",")[0]), res.split(",")[1]+","+res.split(",")[2]);
			}
		}
		int[][] a = new int[50][50];
		for (int i = 0 ; i < 50; i++) {
			for (int j = 0; j< 50; j++) {
				a[i][j] = 0;
			}
		}
		String coords_opp = "";
		String coords_pl = "";
		for (int sq = 1; sq<=25; sq++) {
			int i = sq - 1; //i для передвижения по массиву глоб квадратов
			int square_from_template = Integer.parseInt(pl_globe_square[i]);
			int x_to_plus = Integer.parseInt(templateLocalMapCoords.get(sq).split(",")[0])+20;
			int y_to_plus = Integer.parseInt(templateLocalMapCoords.get(sq).split(",")[1])+20;
			int x_square = Integer.parseInt(square_x_y.get(square_from_template).split(",")[0]);
			int y_square = Integer.parseInt(square_x_y.get(square_from_template).split(",")[1]);
			int x = x_square + x_to_plus;
			int y = y_square + y_to_plus;
			if (30>x && x>20 && 30>y && y>20) {
				coords_pl = String.valueOf(x) + "," + String.valueOf(y);
			}
			else {
				coords_opp += String.valueOf(x) + "," + String.valueOf(y) + " ";	
			}			
		}
		Server.db.add_local_map(Server.connect, pl_id, coords_pl, coords_opp);
	}
}
