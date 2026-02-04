import java.io.*;

public class Main {
	/*
	 점화식 -> DP
	 An = (An-1) + (An-2) + (An-3) -> 틀림 순서 차이에 의한 중복을 제거하지 못함
	 */
	private static int[][] dpArr = new int[10001][4];
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws Exception{
		dpArr[1][1] = 1;
		dpArr[1][2] = 1;
		dpArr[1][3] = 1;
		dpArr[2][1] = 1;
		dpArr[2][2] = 2;
		dpArr[2][3] = 2;
		dpArr[3][1] = 1;
		dpArr[3][2] = 2;
		dpArr[3][3] = 3;
		int n = Integer.parseInt(br.readLine());
		for(int i = 0 ; i < n ; i++) {
			int m = Integer.parseInt(br.readLine());
			System.out.println(getNumOfWay(m));
		}
	}
	
	private static int getNumOfWay(int target) {
		if(dpArr[target][3] != 0) return dpArr[target][3];
		if(dpArr[target-1][3] == 0)
			getNumOfWay(target-1);
		dpArr[target][1] = 1;
		dpArr[target][2] = dpArr[target - 2][2] + 1;
		dpArr[target][3] = dpArr[target - 1][1] + dpArr[target - 2][2] + dpArr[target - 3][3];
				
		return dpArr[target][3];
	}
	
	
}
