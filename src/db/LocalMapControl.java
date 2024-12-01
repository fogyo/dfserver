package db;

import java.util.HashMap;

import server.Server;

public class LocalMapControl {

	private int pl_id;
	private String res;
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
	
	public LocalMapControl(int pl_id) {
		this.pl_id = pl_id;
	}
	
	private double distance (int x1, int y1, int x2, int y2) {
		return Math.sqrt((Math.pow((x2-x1),2) + Math.pow((y2-y1),2)));
	}
	
	private String make_local_coords(int pl_id, int opp_square, int x, int y) {
		String map_template[] = Server.db.get_template_by_pl_id(Server.connect, pl_id).split(",");
		for (int i = 1; i<26; i++) {
			if (Integer.parseInt(map_template[i-1]) == opp_square) {
				x += Integer.parseInt(templateLocalMapCoords.get(i).split(",")[0]);
				y += Integer.parseInt(templateLocalMapCoords.get(i).split(",")[1]);
			}
		}
		return String.valueOf(x)+","+String.valueOf(y);
	}
	
	public void run() {
		//1 пункт, вывод координат разрушенных ребят
		int map_id = Server.db.get_map_id(Server.connect, pl_id);
		String[] dcs = Server.db.get_destroyed_coords(Server.connect, map_id).split(",");
		for (int i = 0; i < dcs.length; i++) {
			String[] div_coords = dcs[i].split(" ");
			res += make_local_coords(pl_id, Integer.parseInt(div_coords[0]), Integer.parseInt(div_coords[1]), Integer.parseInt(div_coords[2]))+" ";
		}
		res = res.strip();
		res+=";";
		Server.logs.log("Got destroyed players coordinate");
		//2 пункт, вывод координат доступных игроков
		String coordinates = Server.db.get_coords(Server.connect, pl_id);
		String[] separ = coordinates.split(";");
		String pl_coords = separ[0];
		String[] pl_coord = pl_coords.split(",");
		int pl_x = Integer.parseInt(pl_coord[0]);
		int pl_y = Integer.parseInt(pl_coord[1]); 
		String opp_coords = separ[1];
		int radar = Server.db.get_radar(Server.connect, pl_id);
		String[] opps = opp_coords.split(" ");
		for (int i = 0; i < opps.length; i++) {
			String[] opp_coord = opps[i].split(",");
			int opp_x = Integer.parseInt(opp_coord[0]);
			int opp_y = Integer.parseInt(opp_coord[1]);
			double l = distance(opp_x, opp_y, pl_x, pl_y);
			if (l <= radar) {
				String coords = String.valueOf(opp_x)+","+String.valueOf(opp_y);
				if (res.contains(coords)==false) {
					res += coords + " ";
				}				
			}
		}
		res = res.strip();
		res+=";";
		Server.logs.log("Got available players coordinate");
		//3 пункт, вывод координат триггернутых
		String[] pl_ids = Server.db.get_actual_opps(Server.connect, map_id).split(",");
		for (int i = 0 ; i < pl_ids.length ; i++) {
			int opp_id = Integer.parseInt(pl_ids[i]);
			if (opp_id != pl_id) {
				if (Server.db.get_trigger(Server.connect, opp_id) == 1) {
					String[] div_coords = Server.db.get_coords_from_player(Server.connect, opp_id).split(",");
					res += make_local_coords(pl_id, Integer.parseInt(div_coords[0]), Integer.parseInt(div_coords[1]), Integer.parseInt(div_coords[2]))+" ";
				}
			}
		}
		res = res.strip();
		Server.logs.log("Got triggered players coordinate");
	}
	
	public String getRes() {
		return res;
	}
}
