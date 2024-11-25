package db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import commands.AttackLevel;
import commands.DefenseLevel;
import commands.Levels;
import commands.RadarLevel;
import server.Server;

public class LevelControl {

	private HashMap<String, Class> lc = new HashMap<>() {{
		put("attack", AttackLevel.class);
		put("defense", DefenseLevel.class);
		put("radar", RadarLevel.class);
	}};
	
	private int pl_id;
	private String args;
	
	public LevelControl(int pl_id, String args) {
		this.pl_id = pl_id;
		this.args = args;
	}
	
	public void run() {
		String skill = args.split(" ")[1];
		Class clazz = lc.get(skill);
		Method increase_lvl = null;		
			try {
				increase_lvl = clazz.getDeclaredMethod("increase_lvl");
				increase_lvl.invoke(clazz.newInstance(), pl_id, 1);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
	}
	
	public String getRes() {
		return Server.db.get_skills(Server.connect, pl_id);
	}
	
}
	

