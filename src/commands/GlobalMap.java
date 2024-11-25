package commands;

import java.util.HashMap;

import addition.Maps;
import addition.Player;
import addition.PlayerPosition;
import server.Server;

public class GlobalMap {
	
	public void createGlobalMap(int p_id){
		Maps.active_maps = true; 
		
		String str_with_ids = String.valueOf(p_id);
		//генерируем айдишники ботов
		for (int i = -1; i>=-24; i--) {
			str_with_ids += " " + String.valueOf(i);
		}
		int map_id = Server.db.add_new_map(Server.connect, str_with_ids);	
		int player_square = (int) (Math.random()*26);
		int x = (int) (Math.random()*6+2);
		int y = (int) (Math.random()*6+2);
		PlayerPosition pl_pos = new PlayerPosition(player_square, x, y);
		Server.db.update_player_table(Server.connect, pl_pos, map_id, p_id);
		int k = -1;
		for (int bot_square = 1; bot_square < 26; bot_square++) {
			if (bot_square == player_square) {
			}
			else {
				int x_bot = (int) (Math.random()*6+2);
				int y_bot = (int) (Math.random()*6+2);
				PlayerPosition bot_pos = new PlayerPosition(bot_square, x_bot, y_bot);
				Server.db.update_bot_table(Server.connect, bot_pos, map_id, k);
				k -= 1;
			}					
		}
	}
	
	public void add(int p_id) {
		Server.db.change_bot_to_player(Server.connect, p_id);
		int bot_num = Server.db.num_of_bots(Server.connect);
		if (bot_num == 0) {
			Maps.active_maps = false;
		}
	}

}

