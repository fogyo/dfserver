package addition;

public class PrizeControl {
	
	private int players_num;
	private double entry_cost;
	
	public PrizeControl(int players_num, double entry_cost) {
		players_num = this.players_num;
		entry_cost = this.entry_cost;
	}
	
	public static double[] prize_raspr(int players_num, double entry_cost) {
		double prize_sum = entry_cost*players_num;
		prize_sum = prize_sum*9/10;
		double[] pl_arr = new double[players_num];
		int win_num = players_num/10;
		int sum = 0;
		double[] pr_arr = new double[win_num];
		for (int i = 0; i<win_num; i++) {
			double i_do = i;
			double win_do = win_num;
			double x = 1+(i_do/win_do)*4;
			pr_arr[i] = Math.pow(Math.E, (-x+2));
			sum+=pr_arr[i];
		}
		for (int i = 0; i<win_num; i++) {
			pl_arr[i] = pr_arr[i]*prize_sum/sum;			
		}
		return pl_arr;
	}
}
