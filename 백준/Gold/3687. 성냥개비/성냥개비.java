import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static int n;
	private static String[] dp = new String[101]; 
	public static void main(String[] args) throws Exception {

		int m = Integer.parseInt(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		dp[2] = "1";
		dp[3] = "7";
		dp[4] = "4";
		dp[5] = "2";
		dp[6] = "0";
		dp[7] = "8";
		dp[8] = "10";
		dp[9] = "18";
		dp[10] = "22";
		dp[11] = "20";
		dp[12] = "28";
		dp[13] = "68";
		
		
		for(int i = 14; i <= 100; i++) {
			long min = Long.MAX_VALUE;
			for(int j = 2; j <= i-2; j++) {
				if(dp[j].charAt(0) == '0') continue;
				min = Math.min(min,Long.parseLong(dp[j] + dp[i-j]));
			}
			dp[i] = String.valueOf(min);
		}
		dp[6] = "6";
		for (int j = 0; j < m; j++) {

			n = Integer.parseInt(br.readLine().trim());
		

			// 가장 큰수는 무조건 71111(n이 홀수) or 11111(n이 짝수)
			// 가장 작은 수는 8000 일단 개수가 작은게 제일 중요
			// n을 2 3 4 5 6 7 의 합으로 나타내야함.
			// 2하고 3의 합으로 2 이상 모든 수를 표현 가능
			// 즉 4 5 6 7은 각각
			// 4 2 2개
			// 5 2,3
			// 6은 3,3
			// 7은 2 2 3
			// 최대한 7을 많이 써야 함.
			// 나머지는 1 ~ 6

			// 1인 경우는 7을 빼고 5, 2로

			/*
			 2개를 써야하는 경우
			 8 이상
			 8은 10 1
			 9는 18 2
			 10은 22 3
			 11은 20 4
			 12는 28 5
			 13은 68 6
			 14는 88 7
			 */
			
			//dp, 그리디
			
			sb.delete(0, sb.length());
			for (int i = 0; i < n / 2; i++)
				sb.append(1);
			if (n % 2 == 1) {
				sb.delete(0, 1);
				sb.insert(0, 7);
			}
			String max = sb.toString();

			System.out.println(dp[n] + " " + max);
		}
		
//		System.out.println(String.join(" ", dp));

	}

}
