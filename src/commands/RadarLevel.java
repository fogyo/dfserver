package commands;

import server.Server;

public class RadarLevel extends Levels{
	
	@Override
	public void increase_lvl(int pl_id, int points) {
		Server.db.increase_bd_lvl(Server.connect, pl_id, "radar", points);
	}

}
