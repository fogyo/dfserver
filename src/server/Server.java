package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import db.DBCommands;
import db.DBCreatingUser;



public class Server {
	
	
	
	public static DBCommands db = new DBCommands();
	public static DBCreatingUser dcu = new DBCreatingUser();
	public static Connection connect = db.connection_to_db("dfgame", "postgres", "1234");
	
	public static void main(String[] args) {
		
		
		
		
		
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
