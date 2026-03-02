import java.util.*;
import java.io.*;

public class Main {
	/*
	 * 사이클 찾기 문제
	 * 각 원소는 하나의 사이클에만 포함되거나 아예 사이클에 포함되지 않음. -> 단방향이기 때문
	 * 즉, 모든 사이클을 구하고 사이클에 속하는 모든 원소를 넣으면 됨.
	 */
	
	
	private static int[] next = new int[101];
	private static boolean[] isInCycle = new boolean[101];
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		for(int i = 1; i <= n; i++) {
			next[i] = Integer.parseInt(br.readLine());
		}
		
		for(int i = 1; i <= n; i++) {
			if(isInCycle[i]) continue;
			boolean[] visited = new boolean[n+1];
			findCycleByDfs(i, visited);
		}
		List<Integer> rslt = new LinkedList<>();
		for(int i = 1; i <= n; i++) {
			if(isInCycle[i])
				rslt.add(i);
		}
		
		rslt.sort(null);
		StringBuilder sb = new StringBuilder((n + 1) * 4);
		sb.append(rslt.size()).append('\n');
		for(int k : rslt) {
			sb.append(k).append('\n');
		}
		System.out.println(sb.toString());
	}
	
	private static void findCycleByDfs(int curr, boolean[] visited){
		if(isInCycle[curr])
			return;
		if(visited[curr]) {
			markCycleByDfs(curr);
			return;
		}
		visited[curr] = true;
		findCycleByDfs(next[curr], visited);
	}
	
	private static void markCycleByDfs(int curr) {
		if(isInCycle[curr])
			return;
		isInCycle[curr] = true;
		markCycleByDfs(next[curr]);
	}
	
}
