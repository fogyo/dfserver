package addition;

import java.util.HashMap;
import java.util.Random;

import server.Server;

public class UserInfo {

	private int id;
	private int request;
	private String code;
	private HashMap<Integer, String> id_pass = new HashMap<>(); 
	private String alphabet = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
	
	public UserInfo(int id, int request, String code) {
		this.id = id;
		this.request = request;
		this.code = code;
	}
	
	public boolean checkUser() {
		Random random = new Random();
		String pass = new String(); 
		for (int i = 0; i<16; i++) {
			int r = random.nextInt(alphabet.length());
			pass += alphabet.charAt(r);
		}
		try {
			if (code.equals(id_pass.get(id))){
				code = pass;
				id_pass.put(id, pass);
				return true;
			}
			else {
				return false;
			}
		}catch (Exception e){
			if (code.equals(Server.UnityCode)) {
				id_pass.put(id, code);
				return true;
			}
			return false;
		}			
	}
		
}
