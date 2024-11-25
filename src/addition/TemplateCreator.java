package addition;

public class TemplateCreator {

	private int [][] players_pos = new int [15][15];
	
	public String createMapTemplates(int player_pos) {
		int [][] global_map = new int [5][5];
		int k=1;
		for (int i = 0; i<5; i++) {
			for (int j = 0; j<5; j++) {
				global_map[i][j] = k;
				k++;
			}
		}
		for (int i = 0; i<15; i++) {
			for (int j = 0; j<15; j++) {
				players_pos[i][j] = global_map[i%5][j%5];	
			}
		}
		String map_template = "";
		int pl_x = 0;
		int pl_y = 0;
		for (int i = 5; i<10; i++) {
			for (int j = 5; j<10; j++) {
				if (players_pos[i][j] == player_pos) {
					pl_x = i;
					pl_y = j;
				}
			}
		}
		for (int i = (pl_x-2); i<=(pl_x+2); i++) {
			for (int j = (pl_y-2); j<=(pl_y+2); j++) {
				map_template += String.valueOf(players_pos[i][j])+",";
			}
		}
		return map_template;
	}
	
}
