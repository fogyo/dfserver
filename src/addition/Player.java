package addition;

public class Player {

	private PlayerPosition pl_pos;
	private int map_id;
	private int user_id;
	
	public Player (PlayerPosition pl_pos, int map_id, int user_id) {
		this.map_id = map_id;
		this.pl_pos = pl_pos;
		this.user_id = user_id;
	}
}
