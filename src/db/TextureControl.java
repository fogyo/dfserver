package db;

import server.Server;

public class TextureControl {
	
	private int pl_id;
	private int texture;
	private String res;
	
	public TextureControl (int pl_id, int texture) {
		this.pl_id = pl_id;
		this.texture = texture;
	}
	
	public void run() {
		res = Server.db.update_player_texture(Server.connect, pl_id, texture);
	}
	
	public String getRes() {
		return res;
	}
}
