package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBCreatingUser {
	
	public boolean isUser (Connection conn, String telegram_id) {
		boolean res = false;
		Statement statement_to_check;
		try {
			String sql_to_check = String.format("SELECT * FROM tg_user WHERE telegram_id = '%s'", telegram_id);
			statement_to_check = conn.createStatement();
			ResultSet rs = statement_to_check.executeQuery(sql_to_check);
			while (rs.next()) {
				res = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public int num_of_users(Connection conn) {
		Statement statement_to_get;
		try {
			String sql_to_get = String.format("SELECT COUNT(*) FROM tg_user WHERE pl_id > 0");
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
	
	public void createUser (Connection conn, String telegram_id, int users) {
		Statement ins_tg_user;
		Statement ins_rating;
		Statement ins_plicker;
		Statement ins_ptable;
		int pl_id = users+1;
		try {
			String sql_tg_user = String.format("INSERT INTO tg_user VALUES('%s', '%s', '%s', '%s')", telegram_id, pl_id, "rating_default", 0);
			String sql_rating = String.format("INSERT INTO rating_default VALUES('%s', '%s')", pl_id, 0);
			String sql_plicker = String.format("INSERT INTO plicker VALUES('%s', '%s', '%s', '%s', '%s')", pl_id, 0, 0, 0, 0);
			String sql_ptable = String.format("INSERT INTO player_table VALUES('%s', '%s', '%s', '%s', '%s')", pl_id, 0, 0, 0, 0);
			ins_tg_user = conn.createStatement();
			ins_rating = conn.createStatement();
			ins_plicker = conn.createStatement();
			ins_ptable = conn.createStatement();
			ins_tg_user.executeUpdate(sql_tg_user);
			ins_rating.executeUpdate(sql_rating);
			ins_plicker.executeLargeUpdate(sql_plicker);
			ins_ptable.executeUpdate(sql_ptable);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayerID (Connection conn, String telegram_id) {
		Statement get_pl_id;
		try{
			String sql_pl_id = String.format("SELECT pl_id FROM tg_user WHERE telgram_id = '%s", telegram_id);
			get_pl_id = conn.createStatement();
			ResultSet rs = get_pl_id.executeQuery(sql_pl_id);
			while(rs.next()) {
				return rs.getInt(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
