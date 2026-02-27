import java.util.*;
import java.util.stream.*;
import java.io.*;

public class Main {
	
	private static int n;
	private static int m;
	private static int[][] map;
	private static List<Camera> cameras = new ArrayList<>(8);
	private static final int[] NUM_OF_DIRECTION = new int[]{0,4,2,4,4,1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = inputs[0];
		m = inputs[1];
		map = new int[n][];
		for(int i = 0 ; i < n; i++) {
			map[i] = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			for(int j = 0 ; j < m; j++) {
				if(1 <= map[i][j] && map[i][j] <= 5)
					cameras.add(new Camera(i, j, map[i][j]));
			}
		}
		if(cameras.isEmpty()) {
			System.out.println(countBlindSpot(map));
			return;
		}
		System.out.println(findMin0Dfs(0, map));
	}
	
	private static int findMin0Dfs(int cameraIdx, int[][] markedMap) {
		int[][][] markedMaps = markAllWay(markedMap, cameras.get(cameraIdx));
		if(cameraIdx == cameras.size()-1) {
			int minBlind = Integer.MAX_VALUE;
			for(int[][] map : markedMaps) {
				minBlind = Math.min(minBlind, countBlindSpot(map));
//				printMap(map);
			}
			return minBlind;
		}
		int minBlind = Integer.MAX_VALUE;
		for(int[][] map : markedMaps) {
			minBlind = Math.min(minBlind, findMin0Dfs(cameraIdx+1, map));
		}
		return minBlind;
	}
	
	private static int countBlindSpot(int[][] markedMap) {
		int cnt = 0;
		for(int[] row : markedMap) {
			for(int spot: row) {
				cnt += spot == 0 ? 1 : 0;
			}
		}
		return cnt;
	}
	
	private static int[][][] markAllWay(int[][] map, Camera camera) {
		//1은 4방향
		//2는 2방향
		//3은 4방향
		//4는 4방향
		//5는 1방향
		int numOfDirection = NUM_OF_DIRECTION[camera.type];
		int[][][] maps = new int[numOfDirection][][];
		for(int i = 1; i <= numOfDirection; i++) {
			maps[i-1] = markMap(map, camera, i);
		}
		return maps;
	}
	
	private static int[][] markMap(int[][] map, Camera camera, int direction) {
		//방향은 1이상 4이하
		int[][] markedMap = new int[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				markedMap[i][j] = map[i][j];
			}
		}
		switch(camera.type) {
		case (1) :
			switch(direction) {
			case (1):
				markRight(markedMap, camera);
				break;
			case (2):
				markDown(markedMap, camera);
				break;
			case (3):
				markLeft(markedMap, camera);
				break;
			case (4):
				markUp(markedMap, camera);
				break;
			}
			break;
		case (2) :
			switch(direction) {
			case (1):
				markRight(markedMap, camera);
				markLeft(markedMap, camera);
				break;
			case (2):
				markUp(markedMap, camera);
				markDown(markedMap, camera);
				break;
			}
			break;
		case (3) :
			switch(direction) {
			case (1):
				markUp(markedMap, camera);
				markRight(markedMap, camera);
				break;
			case (2):
				markRight(markedMap, camera);
				markDown(markedMap, camera);
				break;
			case (3):
				markDown(markedMap, camera);
				markLeft(markedMap, camera);
				break;
			case (4):
				markLeft(markedMap, camera);
				markUp(markedMap, camera);
				break;
			}
			break;
		case (4) :
			switch(direction) {
			case (1):
				markLeft(markedMap, camera);
				markUp(markedMap, camera);
				markRight(markedMap, camera);
				break;
			case (2):
				markUp(markedMap,camera);
				markRight(markedMap, camera);
				markDown(markedMap, camera);
				break;
			case (3):
				markRight(markedMap, camera);
				markDown(markedMap, camera);
				markLeft(markedMap, camera);
				break;
			case (4):
				markDown(markedMap, camera);
				markLeft(markedMap, camera);
				markUp(markedMap, camera);
				break;
			}
			break;
		case (5) :
			markUp(markedMap,camera);
			markRight(markedMap, camera);
			markDown(markedMap, camera);
			markLeft(markedMap, camera);
			break;
		}
		
		return markedMap;
		
	}
	
	private static void markRight(int[][] map, Camera camera) {
		for(int i = camera.col + 1; i < m ; i++) {
			if(map[camera.row][i] == 6) break;
			map[camera.row][i] = -1;
		}
	}
	private static void markLeft(int[][] map, Camera camera) {
		for(int i = camera.col - 1; i >= 0 ; i--) {
			if(map[camera.row][i] == 6) break;
			map[camera.row][i] = -1;
		}
	}
	
	private static void markUp(int[][] map, Camera camera) {
		for(int i = camera.row - 1; i >= 0 ; i--) {
			if(map[i][camera.col] == 6) break;
			map[i][camera.col] = -1;
		}
	}
	
	private static void markDown(int[][] map, Camera camera) {
		for(int i = camera.row + 1; i < n ; i++) {
			if(map[i][camera.col] == 6) break;
			map[i][camera.col] = -1;
		}
	}
	
	private static void printMap(int[][] map) {
		System.out.println("--------- 지도 출력 ----------");
		for(int i = 0; i < n; i++) {
			System.out.println(Arrays.toString(map[i]));
		}
		
	}
	
	static class Camera{
		int row;
		int col;
		int type;
		Camera(int row, int col, int type){
			this.row = row;
			this.col = col;
			this.type = type;
		}
	}

}
