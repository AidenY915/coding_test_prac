import java.util.*;
import java.util.stream.*;
import java.io.*;

public class Main {
	private static int n;
	private static int k;
	private static int r;
	private static List<Road> roads;
	
	private static int[][] groupNums;  
	private static int cnt = 1;
	private static List<Cow> cows;
	private static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//입력 받기
		int[] inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = inputs[0]; k = inputs[1]; r = inputs[2];
		
		groupNums = new int[n + 1][n + 1];
		
		roads = new ArrayList<>(r);
		for(int i = 0; i < r; i++) {
			inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			roads.add(new Road(inputs[0], inputs[1], inputs[2], inputs[3]));
		}
		
		cows = new ArrayList<>(k);
		for(int i = 0; i < k; i++) {
			inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			cows.add(new Cow(inputs[0], inputs[1]));
		}
		
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= n; j++) {
				if(groupNums[i][j] == 0)
					setGroupNumByDfs(i, j, cnt++);
			}
		}
		
		int[] cowNumsOfGroup = new int[cnt]; //cnt는 현재 그룹 수 + 1
		
		for(Cow cow : cows) {
			cowNumsOfGroup[groupNums[cow.r][cow.c]]++;
		}
		//조합 곱
		int rslt = 0;
		for(int i = 1; i < cowNumsOfGroup.length; i++) {
			for(int j = i+1; j < cowNumsOfGroup.length; j++) {
				rslt += cowNumsOfGroup[i] * cowNumsOfGroup[j];
			}
		}
		System.out.println(rslt);
//		for(int i = 0; i <= n; i++) {
//			System.out.println(Arrays.toString(groupNums[i]));
//		}
	}
	
	//소세기
	
	private static void setGroupNumByDfs(int r, int c, int groupNum) {
		if(r < 1 || n < r || c < 1 || n < c)
			return;
		if(groupNums[r][c] != 0) return;
		groupNums[r][c] = groupNum;
		for(int[] direction : directions) {
			int[] next = {r + direction[0], c + direction[1]};
			Road nextRoad = new Road(r, c, next[0], next[1]);
			if(roads.contains(nextRoad)) continue;
			setGroupNumByDfs(next[0], next[1], groupNum);
		}
	}
	
	static class Road{
		int r1, c1, r2, c2;
		
		Road(int r1, int c1 ,int r2, int c2){
			this.r1 = r1;
			this.c1 = c1;
			this.r2 = r2;
			this.c2 = c2;
		}
		
		@Override
		public boolean equals(Object obj) {
			Road road = (Road)obj;
			return (r1 == road.r1 && c1 == road.c1 && r2 == road.r2 && c2 == road.c2) || (r1 == road.r2 && c1 == road.c2 && r2 == road.r1 && c2 == road.c1);
		}
	}
	
	static class Cow{
		int r,c;
		
		Cow(int r, int c){
			this.r = r;
			this.c = c;
		}
		
	}

}
