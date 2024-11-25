package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import addition.PlayerPosition;



public class DBCommands {

	public Connection connection_to_db(String dbname, String user, String pass) {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, user, pass);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}	
	
	public void add_map_template(Connection conn, int id, String squares) {
		Statement statement_collection;

		try {
			String sql = String.format("INSERT INTO map_templates VALUES ('%s', '%s')", id, squares);
			statement_collection = conn.createStatement();
			statement_collection.executeUpdate(sql);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void create_bot_table(Connection conn, PlayerPosition pl_pos, int map_id, int bot_id) {
		Statement statement;
		try {
			String sql_to_ins = String.format("INSERT INTO bot_table VALUES ('%s', '%s', '%s', '%s', '%s')", pl_pos.getSquare(), pl_pos.getX(),
					pl_pos.getY(), bot_id, map_id);
			statement = conn.createStatement();
			statement.executeUpdate(sql_to_ins);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTextureTemplates(Connection conn, int id, int att, int def, int rad) {
		Statement ins_to_templates;
		try {
			String sql_ins = String.format("INSERT INTO texture_templates VALUES ('%s', '%s', '%s', '%s')", id, att, def, rad);
			ins_to_templates = conn.createStatement();
			ins_to_templates.executeUpdate(sql_ins);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int add_new_map(Connection conn, String str_with_ids) {
		Statement statement_to_ins;
		Statement statement_to_get;
		try {
			String sql_to_ins = String.format("INSERT INTO global_maps (pl_ids) VALUES ('%s')", str_with_ids);
			statement_to_ins = conn.createStatement();
			statement_to_ins.executeUpdate(sql_to_ins);
			String sql_to_get = String.format("SELECT map_id FROM global_maps WHERE pl_ids = '%s'", str_with_ids);
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			while (rs.next()){
				return rs.getInt("map_id");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void update_bot_table(Connection conn, PlayerPosition pl_pos, int map_id, int bot_id) {
		Statement statement;
		try {
			String sql_to_ins = String.format("UPDATE bot_table SET square = '%s', x = '%s', y = '%s', map_id = '%s' WHERE bot_id = '%s'", pl_pos.getSquare(), pl_pos.getX(),
					pl_pos.getY(), map_id, bot_id);
			statement = conn.createStatement();
			statement.executeUpdate(sql_to_ins);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update_player_table(Connection conn, PlayerPosition pl_pos, int map_id, int user_id) {
		Statement statement;
		try {
			String sql_to_ins = String.format("UPDATE player_table SET square = '%s', x = '%s', y = '%s', map_id = '%s' WHERE pl_id = '%s'", pl_pos.getSquare(), pl_pos.getX(),
					pl_pos.getY(), map_id, user_id);
			statement = conn.createStatement();
			statement.executeUpdate(sql_to_ins);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void change_bot_to_player(Connection conn, int pl_id) {
		Statement statement_to_get_map_id;
		Statement statement_to_get_ids_on_map;
		Statement statement_to_get_bot_info;
		Statement statement_to_update_bot_info;
		Statement statement_to_update_player_info;
		Statement statement_to_update_map_info;
		try {
			String sql_to_get_map_id = "SELECT map_id FROM bot_table WHERE map_id > 0";
			statement_to_get_map_id = conn.createStatement();
			ResultSet rs1 = statement_to_get_map_id.executeQuery(sql_to_get_map_id);
			int map_id = 0;
			while (rs1.next()) {
				map_id = rs1.getInt("map_id");
			}
			String sql_to_get_ids_on_map = String.format("SELECT pl_ids FROM global_maps WHERE map_id = '%s'", map_id);
			statement_to_get_ids_on_map = conn.createStatement();
			ResultSet rs2 = statement_to_get_ids_on_map.executeQuery(sql_to_get_ids_on_map);
			String str_with_ids_old = "";
			while (rs2.next()) {
				str_with_ids_old = rs2.getString("pl_ids");
			}
			String[] pl_ids = str_with_ids_old.split(" ");
			int bot_id = 0;
			boolean change = false;
			String str_with_ids_new = "";
			for (int i = 0; i<25; i++) {
				bot_id = Integer.parseInt(pl_ids[i]);
				if (bot_id<0 && change == false) {
					change = true;
					str_with_ids_new += String.valueOf(pl_id) + " ";
					String sql_to_get_bot_info = String.format("SELECT square, x, y FROM bot_table WHERE bot_id = '%s'", bot_id);
					statement_to_get_bot_info = conn.createStatement();
					ResultSet rs3 = statement_to_get_bot_info.executeQuery(sql_to_get_bot_info);
					while(rs3.next()) {
						PlayerPosition pl_pos = new PlayerPosition(rs3.getInt("square"), rs3.getInt("x"), rs3.getInt("y"));
						String sql_to_update_player_info = String.format("UPDATE player_table SET square = '%s', x = '%s', y = '%s', map_id = '%s' WHERE pl_id = '%s'", pl_pos.getSquare(), pl_pos.getX(), pl_pos.getY(), map_id, pl_id);
						statement_to_update_player_info = conn.createStatement();
						statement_to_update_player_info.executeUpdate(sql_to_update_player_info);
						String sql_to_update_bot_info = String.format("UPDATE bot_table SET square = '%s', x = '%s', y = '%s', map_id = '%s' WHERE bot_id = '%s'", 0, 0, 0, 0, bot_id);
						statement_to_update_bot_info = conn.createStatement();
						statement_to_update_bot_info.executeUpdate(sql_to_update_bot_info);
					}
				}
				else {
					str_with_ids_new += pl_ids[i] + " "; 
				}
			}
			String sql_to_update_map_info = String.format("UPDATE global_maps SET pl_ids = '%s' WHERE map_id = '%s'", str_with_ids_new, map_id);
			statement_to_update_map_info = conn.createStatement();
			statement_to_update_map_info.executeUpdate(sql_to_update_map_info);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int num_of_bots(Connection conn) {
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT COUNT(*) FROM bot_table WHERE square > 0");
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			while (rs.next()) {
				return rs.getInt("count");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void create_player_table(Connection conn, PlayerPosition pl_pos, int map_id, int bot_id) {
		Statement statement;
		try {
			String sql_to_ins = String.format("INSERT INTO player_table VALUES ('%s', '%s', '%s', '%s', '%s')", bot_id, map_id, pl_pos.getSquare(), pl_pos.getX(),
					pl_pos.getY());
			statement = conn.createStatement();
			statement.executeUpdate(sql_to_ins);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String get_template_by_pl_id(Connection conn, int pl_id) {
		Statement statement_to_get_templates;
		try {
			String sql_to_get = String.format("SELECT squares FROM map_templates WHERE map_template IN (SELECT square FROM player_table WHERE pl_id = '%s')", pl_id);
			statement_to_get_templates = conn.createStatement();
			ResultSet rs = statement_to_get_templates.executeQuery(sql_to_get);
			while (rs.next()) {
				return rs.getString("squares");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String get_coords_from_player(Connection conn, int pl_id) {
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT x, y, square FROM player_table WHERE pl_id = '%s'", pl_id);
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			while (rs.next()) {
				String x = rs.getString("x");
				String y = rs.getString("y");
				String square = rs.getString("square");
				return square+","+x+","+y;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String get_coords_from_bots(Connection conn, int bot_id) {
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT x, y, square FROM bot_table WHERE bot_id = '%s'", bot_id);
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			while (rs.next()) {
				String x = rs.getString("x");
				String y = rs.getString("y");
				String square = rs.getString("square");
				return square+","+x+","+y;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String get_pl_ids(Connection conn, int pl_id) {
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT pl_ids FROM global_maps WHERE map_id IN (SELECT map_id FROM player_table WHERE pl_id = '%s')", pl_id);
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			while (rs.next()) {
				return rs.getString("pl_ids");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void add_local_map(Connection conn, int pl_id, String coords_pl, String coords_opp) {
		Statement statement_to_ins;
		try {
			String sql_to_ins = String.format("INSERT INTO local_maps VALUES('%s', '%s', '%s')", pl_id, coords_pl, coords_opp);
			statement_to_ins = conn.createStatement();
			statement_to_ins.executeUpdate(sql_to_ins);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void increase_bd_lvl(Connection conn, int pl_id, String lvl_name, int points) {
		Statement statement_to_ins;
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT '%s' FROM plicker WHERE pl_id = '%s'", lvl_name, pl_id);
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			while(rs.next()) {
				int skill = rs.getInt(lvl_name); 
				skill += points;
				String sql_to_ins = String.format("UPDATE plicker SET '%s' = '%s' WHERE pl_id = '%s'", lvl_name, skill, pl_id);
				statement_to_ins = conn.createStatement();
				statement_to_ins.executeUpdate(sql_to_ins);
			}					
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String get_local_map(Connection conn, int pl_id) {
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT opp_coords FROM local_maps WHERE pl_id = '%s'", pl_id);
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			while(rs.next()) {
				return rs.getString("opp_coords"); 
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int get_map_id(Connection conn, int pl_id) {
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT map_id FROM local_maps WHERE pl_id = '%s'", pl_id);
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			while(rs.next()) {
				return rs.getInt("map_id"); 
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int bot_or_player(Connection conn, int map_id, int square) {
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT bot_id, pl_id FROM bot_table, player_table WHERE (bot_table.square = '%s' AND bot_table.map_id = '%s') OR (player_table.map_id = '%s' AND player_table.square = '%s')", square, map_id, map_id, square);
			statement_to_get = conn.createStatement();
			ResultSet rs = statement_to_get.executeQuery(sql_to_get);
			int bot_old = 0;
			int pl_old = 0;
			while(rs.next()) {
				int bot_new = rs.getInt("bot_id"); 
				int pl_new = rs.getInt("pl_id"); 
				if (bot_new == bot_old) {
					return bot_new;
				}
				if (pl_new == pl_old) {
					return pl_new;
				}
				bot_old = bot_new;
				pl_old = pl_new;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void bot_destruction(Connection conn, int bot_id, int pl_id) {
		Statement statement_to_update_bot_info;
		Statement statement_to_update_player_rating;
		Statement statement_to_add_destruction;
		try {	
			String sql_to_update_player_rating = String.format("UPDATE rating SET points = points + 1500 WHERE pl_id = '%s'", pl_id);
			statement_to_update_player_rating = conn.createStatement();
			statement_to_update_player_rating.executeUpdate(sql_to_update_player_rating);
			
			String sql_to_add_destruction = String.format("INSERT INTO destructions VALUES((SELECT map_id FROM bot_table WHERE bot_id = '%s'), (SELECT square FROM bot_table WHERE bot_id = '%s'), '%s', (SELECT x FROM bot_table WHERE bot_id = '%s'), (SELECT y FROM bot_table WHERE bot_id = '%s'))", bot_id, bot_id, pl_id, bot_id, bot_id);
			statement_to_add_destruction = conn.createStatement();
			statement_to_add_destruction.executeUpdate(sql_to_add_destruction);
			
			String sql_to_update_bot_info = String.format("UPDATE bot_table SET square = '%s', x = '%s', y = '%s', map_id = '%s' WHERE bot_id = '%s'", 0, 0, 0, 0, bot_id);
			statement_to_update_bot_info = conn.createStatement();
			statement_to_update_bot_info.executeUpdate(sql_to_update_bot_info);
									
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean check_success_of_attack(Connection conn, int opp_id, int pl_id) {
		Statement statement_to_get_pl_att;
		Statement statement_to_get_opp_def;
		try {	
			String sql_to_get_pl_att = String.format("SELECT attack FROM plicker WHERE pl_id = '%s'", pl_id);
			statement_to_get_pl_att = conn.createStatement();
			ResultSet rsAtt = statement_to_get_pl_att.executeQuery(sql_to_get_pl_att);
			String sql_to_get_opp_def = String.format("SELECT defense FROM plicker WHERE pl_id = '%s'", opp_id);
			statement_to_get_opp_def = conn.createStatement();
			ResultSet rsDef = statement_to_get_opp_def.executeQuery(sql_to_get_opp_def);
			int def = 0;
			int att = 0;
			while (rsAtt.next()) {
				att = rsAtt.getInt("attack");
			}
			while (rsDef.next()) {
				def = rsDef.getInt("defense");
			}
			if (att>def) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void pl_destruction(Connection conn, int opp_id, int pl_id) {
		Statement statement_to_update_opp_info_map;
		Statement statement_to_update_player_rating;
		Statement statement_to_add_destruction;
		Statement statement_to_update_opp_rating;
		Statement statement_to_update_opp_info_plicker;
		Statement statement_to_get_opp_stats;
		try {	
			String sql_to_update_player_rating = String.format("UPDATE rating SET points = points + 1500 WHERE pl_id = '%s'", pl_id);
			statement_to_update_player_rating = conn.createStatement();
			statement_to_update_player_rating.executeUpdate(sql_to_update_player_rating);
			
			String sql_to_get_opp_stats = String.format("SELECT attack, defense, radar FROM plicker WHERE pl_id = '%s'", opp_id);
			statement_to_get_opp_stats = conn.createStatement();
			ResultSet rs = statement_to_get_opp_stats.executeQuery(sql_to_get_opp_stats);
			int def = 0;
			int att = 0;
			int rad = 0;
			int res = 0;
			while (rs.next()) {
				def = rs.getInt("defense")*1000;
				att = rs.getInt("attack")*1000;
				rad = rs.getInt("radar")*1000;
				res = def + att + rad;
			}
			
			String sql_to_update_opp_rating = String.format("UPDATE rating SET points = points - 1500 + '%s' WHERE pl_id = '%s'", res, pl_id);
			statement_to_update_opp_rating = conn.createStatement();
			statement_to_update_opp_rating.executeUpdate(sql_to_update_opp_rating);
			
			String sql_to_add_destruction = String.format("INSERT INTO destructions VALUES((SELECT map_id FROM player_table WHERE pl_id = '%s'), (SELECT square FROM player_table WHERE pl_id = '%s'), '%s', (SELECT x FROM player_table WHERE pl_id = '%s'), (SELECT y FROM player_table WHERE pl_id = '%s'))", opp_id, opp_id, pl_id, opp_id, opp_id);
			statement_to_add_destruction = conn.createStatement();
			statement_to_add_destruction.executeUpdate(sql_to_add_destruction);
			
			String sql_to_update_opp_info_map = String.format("UPDATE player_table SET square = '%s', x = '%s', y = '%s', map_id = '%s' WHERE pl_id = '%s'", 0, 0, 0, 0, opp_id);
			statement_to_update_opp_info_map = conn.createStatement();
			statement_to_update_opp_info_map.executeUpdate(sql_to_update_opp_info_map);
			
			String sql_to_update_opp_info_plicker = String.format("UPDATE plicker SET attack = '%s', defense = '%s', radar = '%s', trigger = '%s' WHERE pl_id = '%s'", 0, 0, 0, 0, opp_id);
			statement_to_update_opp_info_plicker = conn.createStatement();
			statement_to_update_opp_info_plicker.executeUpdate(sql_to_update_opp_info_plicker);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void unsuccessful_attack(Connection conn, int pl_id) {
		Statement statement_to_update_pl_trigger;
		try {
			String sql_to_update_pl_trigger = String.format("UPDATE plicker SET trigger = 1 WHERE pl_id = '%s'", pl_id);
			statement_to_update_pl_trigger = conn.createStatement();
			statement_to_update_pl_trigger.executeUpdate(sql_to_update_pl_trigger);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String pl_rating(Connection conn, int pl_id) {
		Statement statement_to_get_pl_tournament;
		Statement statement_to_get_first_hundred;
		Statement statement_to_get_telegram_ids;
		Statement statement_to_get_kolvo;
		try {
			String sql_to_get_pl_tournament = String.format("SELECT tournament FROM user WHERE pl_id = '%s'", pl_id);
			statement_to_get_pl_tournament = conn.createStatement();
			ResultSet rs_tour = statement_to_get_pl_tournament.executeQuery(sql_to_get_pl_tournament);
			String tournament = "";
			while (rs_tour.next()) {
				tournament = rs_tour.getString("tournament");
			}
			String sql_to_get_kolvo = String.format("SELECT COUNT(*) FROM %s", tournament);
			statement_to_get_kolvo = conn.createStatement();
			ResultSet rs_kol = statement_to_get_kolvo.executeQuery(sql_to_get_kolvo);
			int kol = 0;
			while (rs_kol.next()) {
				kol = rs_kol.getInt(1);
			}
			kol = kol/5;
			String sql_to_get_first_hundred = String.format("SELECT * FROM '%s' ORDER BY points DESC LIMIT %s", tournament, kol);
			statement_to_get_first_hundred = conn.createStatement();
			ResultSet rs_fh = statement_to_get_first_hundred.executeQuery(sql_to_get_first_hundred);
			String res = "";
			while (rs_fh.next()) {
				String sql_to_get_telegram_ids = String.format("SELECT telegram_id FROM tg_user WHERE pl_id = '%s'", rs_fh.getInt("pl_id"));
				statement_to_get_telegram_ids = conn.createStatement();
				ResultSet rs_tg = statement_to_get_telegram_ids.executeQuery(sql_to_get_telegram_ids);
				String tg_id = "";
				while(rs_tg.next()) {
					tg_id = rs_tg.getString("telegram_id");
				}
				res += tg_id+" "+String.valueOf(rs_fh.getInt("points"))+",";
			}
			return res;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int tournament_kol(Connection conn, int pl_id) {
		Statement statement_to_get_kolvo;
		Statement statement_to_get_pl_tournament;
		try {
			String sql_to_get_pl_tournament = String.format("SELECT tournament FROM user WHERE pl_id = '%s'", pl_id);
			statement_to_get_pl_tournament = conn.createStatement();
			ResultSet rs_tour = statement_to_get_pl_tournament.executeQuery(sql_to_get_pl_tournament);
			String tournament = "";
			while (rs_tour.next()) {
				tournament = rs_tour.getString("tournament");
			}
			String sql_to_get_kolvo = String.format("SELECT COUNT(*) FROM %s", tournament);
			statement_to_get_kolvo = conn.createStatement();
			ResultSet rs_kol = statement_to_get_kolvo.executeQuery(sql_to_get_kolvo);
			int kol = 0;
			while (rs_kol.next()) {
				kol = rs_kol.getInt(1);
			}
			return kol;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public double entry_cost(Connection conn, int pl_id) {
		Statement statement_to_get_entry_cost;
		Statement statement_to_get_pl_tournament;
		try {
			String sql_to_get_pl_tournament = String.format("SELECT tournament FROM user WHERE pl_id = '%s'", pl_id);
			statement_to_get_pl_tournament = conn.createStatement();
			ResultSet rs_tour = statement_to_get_pl_tournament.executeQuery(sql_to_get_pl_tournament);
			String tournament = "";
			while (rs_tour.next()) {
				tournament = rs_tour.getString("tournament");
			}
			String sql_to_get_entry_cost = String.format("SELECT entry_cost FROM tournament_table WHERE tournament_name = %s", tournament);
			statement_to_get_entry_cost = conn.createStatement();
			ResultSet rs_entry_cost = statement_to_get_entry_cost.executeQuery(sql_to_get_entry_cost);
			double entry_cost = 0;
			while (rs_entry_cost.next()) {
				entry_cost = rs_entry_cost.getDouble(1);
			}
			return entry_cost;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String get_coords(Connection conn, int pl_id) {
		Statement get_coords;
		try {
			String sql_coords = String.format("SELECT * FROM local_maps WHERE pl_id = '%s'", pl_id);
			get_coords = conn.createStatement();
			ResultSet rs = get_coords.executeQuery(sql_coords);
			while (rs.next()) {
				String pl_coords = rs.getString("pl_coords");
				String opp_coords = rs.getString("opp_coords");
				return pl_coords+";"+opp_coords;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int get_radar(Connection conn, int pl_id) {
		Statement get_r;
		try {
			String sql_radar = String.format("SELECT radar FROM plicker WHERE pl_id = '%s'", pl_id);
			get_r = conn.createStatement();
			ResultSet rs = get_r.executeQuery(sql_radar);
			while(rs.next()) {
				return rs.getInt("radar");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String get_destroyed_coords(Connection conn, int map_id) {
		Statement get_dc;
		try {
			String sql_dc = String.format("SELECT square, x, y FROM destructions WHERE map_id = '%s'", map_id);
			get_dc = conn.createStatement();
			ResultSet rs = get_dc.executeQuery(sql_dc);
			String res = "";
			while(rs.next()) {
				res += rs.getString("square")+" "+rs.getString("x")+" "+rs.getString("y")+",";
			}
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String get_actual_opps(Connection conn, int map_id) {
		Statement get_ao;
		try {
			String sql_ao = String.format("SELECT pl_id FROM player_table WHERE map_id = '%s'", map_id);
			get_ao = conn.createStatement();
			ResultSet rs = get_ao.executeQuery(sql_ao);
			String res = "";
			while(rs.next()) {
				res += rs.getString("pl_id")+",";
			}
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int get_trigger(Connection conn, int pl_id) {
		Statement get_t;
		try {
			String sql_t = String.format("SELECT trigger FROM plicker WHERE pl_id = '%s'", pl_id);
			get_t = conn.createStatement();
			ResultSet rs = get_t.executeQuery(sql_t);
			while(rs.next()) {
				return rs.getInt("trigger");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String get_skills(Connection conn, int pl_id) {
		Statement get_s;
		try {
			String sql_s = String.format("SELECT attack, defense, radar FROM plicker WHERE pl_id = '%s'", pl_id);
			get_s = conn.createStatement();
			ResultSet rs = get_s.executeQuery(sql_s);
			while(rs.next()) {
				return rs.getString("attack")+" "+rs.getString("defense")+" "+rs.getString("radar");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String get_current_place(Connection conn, int pl_id) {
		Statement get_cp;
		Statement statement_to_get_pl_tournament;
		try {
			String sql_to_get_pl_tournament = String.format("SELECT tournament FROM user WHERE pl_id = '%s'", pl_id);
			statement_to_get_pl_tournament = conn.createStatement();
			ResultSet rs_tour = statement_to_get_pl_tournament.executeQuery(sql_to_get_pl_tournament);
			String tournament = "";
			while (rs_tour.next()) {
				tournament = rs_tour.getString("tournament");
			}
			String sql_cp = String.format("SELECT pl_id FROM %s ORDER BY points DESC", tournament, pl_id);
			get_cp = conn.createStatement();
			ResultSet rs = get_cp.executeQuery(sql_cp);
			int k=1;
			while(rs.next()) {
				if (rs.getInt("pl_id") == pl_id) {
					return String.valueOf(k);
				}
				k+=1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String update_player_texture(Connection conn, int pl_id, int texture) {
		Statement player_texture_in_user;
		Statement player_texture_in_plicker;
		Statement get_template;
		try {
			String sql_player_texture_in_user = String.format("UPDATE tg_user SET texture = '%s' WHERE pl_id = pl_id", texture);
			String sql_player_texture_in_plicker = String.format("UPDATE plicker SET attack = (SELECT attack FROM texture_templates WHERE texture = '%s'), defense = (SELECT defense FROM texture_templates WHERE texture = '%s'), radar = (SELECT radar FROM texture_templates WHERE texture = '%s') WHERE pl_id = '%s'", texture, texture, texture, pl_id);
			player_texture_in_user = conn.createStatement();
			player_texture_in_plicker = conn.createStatement();
			player_texture_in_user.executeUpdate(sql_player_texture_in_user);
			player_texture_in_plicker.executeUpdate(sql_player_texture_in_plicker);
			String sql_get_template = String.format("SELECT attack, defense, radar FROM texture_template WHERE texture = '%s'", texture);
			get_template = conn.createStatement();
			ResultSet rs = get_template.executeQuery(sql_get_template);
			while (rs.next()) {
				return rs.getString("attack")+","+ rs.getString("defense")+","+rs.getString("radar");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
