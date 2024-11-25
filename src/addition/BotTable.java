package addition;

public class BotTable {
	
	public void createBotTable() {
		for (int i = 1; i<100; i++) {
			PlayerPosition pl_pos = new PlayerPosition(0, 0, 0);
			LocalStart.db.create_player_table(LocalStart.connect, pl_pos, 0, i);
		}
	}

}
