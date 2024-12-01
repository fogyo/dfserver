package db;

import java.util.HashMap;

import addition.Maps;
import addition.PlayerPosition;
import commands.GlobalMap;
import commands.LocalMap;
import server.Server;


public class MapControl {
	
	private LocalMap lm = new LocalMap();
	private GlobalMap gm = new GlobalMap();
	private int pl_id;
	
	public MapControl(int pl_id) {
		this.pl_id = pl_id;
	}
	
	public void run(){
		if (!Maps.active_maps) {
			gm.createGlobalMap(pl_id);
			Server.logs.log("Global map created by " + pl_id);
			lm.createLocalMap(pl_id);
			Server.logs.log("Local map created for " + pl_id);
		}
		else {
			gm.add(pl_id);
			Server.logs.log(pl_id+" added to global map");
			lm.createLocalMap(pl_id);
			Server.logs.log("Local map created for " + pl_id);
		}
	
	}
	
	public String getRes() {
		return null;
	}

}
