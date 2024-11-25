package db;

import java.util.HashMap;

import addition.Maps;
import addition.PlayerPosition;
import commands.GlobalMap;
import commands.LocalMap;


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
			lm.createLocalMap(pl_id);
		}
		else {
			gm.add(pl_id);
			lm.createLocalMap(pl_id);
		}
	
	}
	
	public String getRes() {
		return null;
	}

}
