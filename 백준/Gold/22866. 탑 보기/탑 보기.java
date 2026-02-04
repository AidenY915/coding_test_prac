import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.*;

public class Main {
	
	private static Deque<Building> rStack = new ArrayDeque<>(); // 스택에는 피크만 저장하면 됨. 자기보다 더 큰 피크를 만나면 다 지움
	private static Deque<Building> lStack = new ArrayDeque<>(); // 스택에는 피크만 저장하면 됨.

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int[] building = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int[] cnt = new int[n];
		int[] closest = new int[n];
		Arrays.fill(closest, -1);
		
		
		//반등할 때, 자기보다 낮은 거 다 버려
		
		lStack.add(new Building(0, 0, building[0]));
		for(int i = 1; i < n ; i++) {
			while(!lStack.isEmpty() && lStack.peekLast().h <= building[i]) {
				lStack.pollLast();
			}
			if(lStack.isEmpty()) {
				lStack.add(new Building(0, i, building[i]));
			}
			else {
				closest[i] = lStack.peekLast().index;
				lStack.add(new Building(lStack.peekLast().before+1, i, building[i]));
			}
			cnt[i] += lStack.peekLast().before;
		}
		
		rStack.add(new Building(0, n-1, building[n-1]));
		for(int i = n-2; i >= 0 ; i--) {
			while(!rStack.isEmpty() && rStack.peekLast().h <= building[i]) {
				rStack.pollLast();
			}
			if(rStack.isEmpty()) {
				rStack.add(new Building(0, i, building[i]));
			}
			else {
				int rCloest = rStack.peekLast().index;
				if(closest[i] != -1)
					closest[i] = Math.abs(i - closest[i]) <= Math.abs(i-rCloest)  ? closest[i] : rCloest;
				else
					closest[i] = rCloest;
				rStack.add(new Building(rStack.peekLast().before+1, i, building[i]));
				
			}
			cnt[i] += rStack.peekLast().before;
		}
		for(int i = 0; i < n; i++) {
			System.out.println(cnt[i] + " " + (closest[i] != -1 ? (closest[i]+1) : ""));
		}
		
	}
	
	private static class Building{
		int before = 0; //나보다 앞에 있는 건물 중 나보다 높은 거 개수
		int index;
		int h = 0;
		
		Building(int before, int index, int h){
			this.before = before;
			this.index = index;
			this.h=h;
		}
	}
	
}
