import java.util.*;
import java.io.*;

public class Main {
	//256 * 500 * 500 = 3*5*5*1000000 -> 10^8
	private static int n, m;
	private static int[][] map = new int[500][];
	private static int[][] directions = new int[][] {{1,0}, {-1,0}, {0,1}, {0,-1}};
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = inputs[0]; m = inputs[1];
		for(int i = 0; i < n; i++) {
			inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			map[i] = inputs; 
		}
		int max = 0;
		boolean[][] visited = new boolean[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				max = Math.max(getMaxTetrominoByDfs(i, j, 1, 0, visited), max);
				max = Math.max(getMaxFuTetromino(i, j), max);
			}
		}
		System.out.println(max);
	}
	
	//백트래킹
	private static int getMaxTetrominoByDfs(int row, int col, int cnt, int sum, boolean[][] visited) { //한붓 그리기 -> ㅗ 가 안됨 
		if(!isInsideOfMap(row, col)) return -1;
		if(visited[row][col]) return -1;
		int newSum = sum + map[row][col];
		if(cnt == 4) return newSum;
		
		visited[row][col] = true;
		int max = 0;
		for(int[] dir : directions) {
			int nextRow = row + dir[0];
			int nextCol = col + dir[1];
			max = Math.max(getMaxTetrominoByDfs(nextRow, nextCol, cnt + 1, newSum, visited), max);
		}
		visited[row][col] = false; //백트래킹에서 필수
		return max;
	}
	
	private static int getMaxFuTetromino(int row, int col) { //ㅗ를 동서남북 체크
		int[][][] fuDirections = {
				{{0,0},{-1,0}, {0,-1}, {1,0}}, //왼쪽
				{{0,0},{0,1}, {0,-1}, {1,0}}, //아래
				{{0,0},{-1,0}, {0,1}, {1,0}}, //오른쪽
				{{0,0},{-1,0}, {0,1}, {0,-1}}, //위
		};
		int max = 0;
		for(int[][] fuDir : fuDirections) {
			int sum = 0;
			for(int[] dir : fuDir) {
				int newRow = row + dir[0];
				int newCol = col + dir[1];
				if(!isInsideOfMap(newRow, newCol)) {
					sum = 0;
					break;
				}
				sum += map[newRow][newCol];
			}
			max = Math.max(sum, max);
		}
		return max;
	}
	
	private static boolean isInsideOfMap(int row, int col) {
		return  0 <= row && row < n && 0 <= col && col < m;
	}

}
