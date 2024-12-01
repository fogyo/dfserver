package server;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

import addition.Logger;
import db.DBCommands;
import db.DBCreatingUser;



public class Server {
	
	
	public static String UnityCode;
	public static DBCommands db = new DBCommands();
	public static DBCreatingUser dcu = new DBCreatingUser();
	public static Connection connect = db.connection_to_db("dfgame", "postgres", "1234");
	public static Logger logs;

	public static void main(String[] args) throws IOException {
		logs = new Logger("logs.log");
		try {
            BufferedReader reader = new BufferedReader(new FileReader("path"));
            String line = reader.readLine();
            UnityCode = line;
            while (line != null) {
                line = reader.readLine();
            }
            reader.close();
            logs.log("Entry file was read");
        } catch (IOException e) {
            e.printStackTrace();
            logs.log("Error with entry file");
        }
		
		
		
		
		/*Scanner sc = new Scanner(System.in);
		boolean flag = true;
		MapControl mc = new MapControl();	
		int bot_num = db.num_of_bots(connect);
		if (bot_num == 0) {
			Maps.active_maps = false;
		}
		else {
			Maps.active_maps = true;
		}
		while(flag) {
			String telegram_id = sc.nextLine();
			if (dcu.isUser(connect, telegram_id) == false) {
				dcu.createUser(connect, telegram_id, dcu.num_of_users(connect));
			}
			else {
				
			}
		}*/
	}

}
