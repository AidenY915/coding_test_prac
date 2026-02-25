import java.util.*;
import java.util.stream.*;
import java.io.*;



public class Main {

	private static int n;
	private static int l;
	private static int map[][];
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = inputs[0];
		l = inputs[1];
		map = new int[n][n];
		for(int i = 0 ; i < n; i++) {
			map[i] = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}
		int cnt = 0;
		for(int i = 0 ; i < n; i++)
			cnt += checkWay(rowToList(i));
		for(int i = 0 ; i < n; i++)
			cnt += checkWay(colToList(i));
		
		System.out.println(cnt);
	}
	
	private static int[] rowToList(int row) {
		int[] way = new int[n];
		for(int i = 0 ; i < n; i++) {
			way[i] = map[row][i];
		}
		return way;
	}
	
	private static int[] colToList(int col) {
		int[] way = new int[n];
		for(int i = 0 ; i < n; i++) {
			way[i] = map[i][col];
		}
		return way;
	}
	
	private static int checkWay(int[] way) {
		int windowSize = 1;
		boolean isGoingDown = false;
		for(int i = 1 ; i < n; i++) {
			if(Math.abs(way[i] - way[i-1]) > 1) return 0;
			if(way[i-1] == way[i]) {
				windowSize++;
				if(isGoingDown && windowSize == l) {
					isGoingDown = false;
					windowSize = 0;
				}
			}
			else if(way[i-1] < way[i]) { //올라가는 경우
				if(isGoingDown) return 0;
				if(windowSize < l)
					return 0;
				windowSize = 1;
			}
			else if(way[i-1] > way[i]) { //내려가는 경우
				if(isGoingDown) return 0;
				windowSize = 1;
				isGoingDown = true;
				if(isGoingDown && windowSize == l) {
					isGoingDown = false;
					windowSize = 0;
				}
			}
		}
		if(isGoingDown)
			return 0;
		return 1;
	}
}
