import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class Main {

	private static int r, c;
	private static char[][] map;
	private static Queue<Point> jihunQue = new LinkedList<>();
	private static Queue<Point> fireQue = new LinkedList<>();
	private static Point[] ways = new Point[4];
	private static int  t = 0;
	private static Queue<Point> nextFireQue = new LinkedList<>();
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		r = input[0];
		c = input[1];
		ways[0] = new Point(1,0);
		ways[1] = new Point(-1,0);
		ways[2] = new Point(0,1);
		ways[3] = new Point(0,-1);
		map = new char[r][c];
		
		for(int i = 0 ; i < r ; i++) {
			String[] row = br.readLine().split("");
			for(int  j = 0; j < c; j ++ ) {
				map[i][j] = row[j].charAt(0);
				if(map[i][j] == 'F') fireQue.add(new Point(i, j));
				else if(map[i][j] == 'J') jihunQue.add(new Point(i, j));
			}
		}
		int rslt = bfs();
		System.out.println(rslt == -1 ? "IMPOSSIBLE" : rslt);
	}
	
	private static int bfs() {
		Queue<Point> nextJihunQue = new LinkedList<>();
		nextJihunQue.addAll(jihunQue);
		jihunQue.clear();
		while(!nextJihunQue.isEmpty()) {
			t++;
			jihunQue.addAll(nextJihunQue);
			nextJihunQue.clear();
			while(!jihunQue.isEmpty()) {
				Point jihun = jihunQue.remove();
				if(map[jihun.r][jihun.c] == 'F') continue;
				for(Point way : ways) {
					Point nextJihun = new Point(jihun.r + way.r, jihun.c + way.c);
					if(!checkBound(nextJihun))
						return t;
					if(map[nextJihun.r][nextJihun.c] == '.') {
						nextJihunQue.add(nextJihun);
						map[nextJihun.r][nextJihun.c] = 'J';
					}
				}
			}
			moveFire();
		}
		return -1;
		
	}
	
	private static void moveFire() {
		nextFireQue.clear();
		while(!fireQue.isEmpty()) {
			Point fire = fireQue.remove();
			for(Point way : ways){
				Point nextFire = new Point(fire.r + way.r, fire.c + way.c);
				if(checkBound(nextFire) && map[nextFire.r][nextFire.c] != 'F' && map[nextFire.r][nextFire.c] != '#') {
					map[nextFire.r][nextFire.c] = 'F';
					nextFireQue.add(nextFire);
				}
			}
		}
		fireQue.addAll(nextFireQue);
	}
	
	private static boolean checkBound(Point point) {
		if( 0 > point.r || r <= point.r )
			return false;
		if( 0 > point.c || c <= point.c )
			return false;
		return true;
		
	}
	

	private static class Point{
		int r;
		int c;
		
		Point(int r, int c){
			this.r = r;
			this.c = c;
		}
	}

}
