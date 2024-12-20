package db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;

import server.Server;


public class CommandManager {
	
	private HashMap<String, Class> commands = new HashMap<>() {{
		put("add_pl_to_map", MapControl.class);
		put("attack", PlayerInteraction.class);
		put("get_local_map", LocalMapControl.class);
		put("increase", LevelControl.class);
		put("rating", RatingControl.class);
		put("texture", TextureControl.class);
	}};
	
	private String strCommand;
	private int numargs;
	private String reply = "";
	private int pl_id;
	private String args;
	
	public CommandManager(String strCommand, int numargs, String args, int pl_id) {
		this.strCommand = strCommand;
		this.numargs = numargs;
		this.pl_id = pl_id;
		this.args = args;
	}

	public void startCommand() {
		if (commands.containsKey(strCommand)==true) {
			reply="";
			String[] adress = commands.get(strCommand).toString().split(" ");
			Server.logs.log(strCommand + "arrived to CM");
			//1 аргумент, pl_id		
			if(numargs == 1) {
				try {
					Class clazz = Class.forName(adress[1]);
					
					Class[] oneArg = {Integer.class};
					Method run = null;
					Method getString = null;
					try {
						run = clazz.getDeclaredMethod("run");
						getString = clazz.getDeclaredMethod("getString");
						Object command = clazz.getConstructor(oneArg).newInstance(pl_id);
						run.invoke(command);
						reply = reply + getString.invoke(command) + " ";
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
						Server.logs.log("Error while running command with 1 arg, catch with methods");
					}
					
					
				} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException  e) {
					e.printStackTrace();
					Server.logs.log("Error while running command with 1 arg, multicatch");
				}
			}
			//2 арга, pl_id, args
			else if(numargs == 2) {
				try {
					Class clazz = Class.forName(adress[1]);
					
					Class[] oneArg = {Integer.class, String.class};
					Method run = null;
					Method getString = null;
					try {
						run = clazz.getDeclaredMethod("run");
						getString = clazz.getDeclaredMethod("getString");
						Object command = clazz.getConstructor(oneArg).newInstance(pl_id, args);
						run.invoke(command);
						reply = reply + getString.invoke(command) + " ";
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
						Server.logs.log("Error while running command with 2 args, catch with methods");
					}
					
					
				} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException  e) {
					e.printStackTrace();
					Server.logs.log("Error while running command with 2 args, multicatch");
				}
			}
		}
	}
}
