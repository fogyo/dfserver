package addition;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class LocalStart {
	
	public static double[] prize_raspr(int players_num, double prize_sum) {
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
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int players_num = sc.nextInt();
		double prize = sc.nextInt();
		
		double[] pl_arr = prize_raspr(players_num, prize);
		for (int i = 0; i<players_num/5; i++) {
			System.out.println((i+1)+" "+pl_arr[i]);
		}
	}
}
 