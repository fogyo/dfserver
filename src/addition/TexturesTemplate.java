/*package addition;

import java.util.HashMap;

public class TexturesTemplate {

	private HashMap<Integer, String> temps = new HashMap<>() {{
		put(1, "5 2 3");
		put(2, "4 2 4");
		put(3, "4 3 3");
		put(4, "4 4 2");
		put(5, "3 4 3");
		put(6, "3 5 2");
		put(7, "2 6 2");
		put(8, "3 6 1");
		put(9, "2 7 1");
		put(10, "3 7 0");
	}};
	
	public void createTemps() {
		for (int i = 1; i < 11; i++) {
			String[] skills = temps.get(i).split(" ");
			int att = Integer.parseInt(skills[0]);
			int daf = Integer.parseInt(skills[1]);
			int rad = Integer.parseInt(skills[2]);
			LocalStart.db.createTextureTemplates(LocalStart.connect, i, att, daf, rad);
		}
	}
	
}
*/