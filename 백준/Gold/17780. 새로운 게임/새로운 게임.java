import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	//말은 순서대로 십자 이동
	//4개 이상 쌓이면 종료
	//쌓여있으면 동시 이동 맨 아래에 있는 말만 이동 가능
	//빨간색 칸 이동한 말이 뒤집어서 쌓임
	//도착 칸이 파란색이면 반대로 이동 -> 반대도 파란색이면 방향만 바꾸고 대기 
	//조건은 나갔을 때도 똑같음

	
	private static int k; //말 개수
	private static int n; //판 크기
	private static Horse[] horses;
	private static int map[][];
	
	private static List<Horse>[][] horseMap;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = inputs[0];
		k = inputs[1];
		map = new int[n+1][n+1];
		horseMap = new List[n+1][n+1];
		for(int i = 1 ; i <= n; i++) {
			inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			for(int j = 1; j <= n ; j++) {
				map[i][j] = inputs[j-1];
			}
		}
		horses = new Horse[k+1];
		for(int i = 1 ; i <= k; i++) {
			inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			horses[i] = new Horse(i, inputs[0], inputs[1], inputs[2]);
			List<Horse> horseTower = new LinkedList<>();
			horseTower.add(horses[i]);
			horseMap[horses[i].row][horses[i].col] = horseTower;
		}
		int cnt = 1;
		while(cnt <= 1000) {
			for(int i = 1 ; i <= k; i++) {
				int sizeOfTower = move(i);
				if(sizeOfTower >= 4) {
//					System.out.println(horseMap[horses[i].row][horses[i].col].stream().map(h -> h.idx).collect(Collectors.toList())); //stream.toList() 자바 16이상
					System.out.println(cnt);
					return;
				}
			}
			cnt++;
		}
		System.out.println(-1);
		return;
	}
	
	private static int move(int horseIdx) throws Exception{
		Horse horse = horses[horseIdx];
		List<Horse> horseTower = horseMap[horse.row][horse.col];
		if(horse.idx != horseTower.get(0).idx)
			return -1;
		int nextRow = horse.row + horse.rowMove;
		int nextCol = horse.col + horse.colMove;
		
		if(isBlue(nextRow, nextCol)) {
			horse.rowMove *= -1;
			horse.colMove *= -1;
			nextRow = horse.row + horse.rowMove;
			nextCol = horse.col + horse.colMove;
			if(isBlue(nextRow, nextCol)) {
//				nextRow = horse.row;
//				nextCol = horse.col;
				return -1; //실제 이동 안하고 리턴
			}
		}
		switch (map[nextRow][nextCol]){
			case 0: break;
			case 1: Collections.reverse(horseTower); // 빨간 줄 뒤집기
				horse = horseTower.get(0);
				horseIdx = horse.idx;
				break;
		}
		//이제 이동
		if(horseMap[nextRow][nextCol] == null) {
			horseMap[nextRow][nextCol] = horseTower; //이거 때문이라도 모든 말 다 row col 갱신
		}
		else {
			horseMap[nextRow][nextCol].addAll(horseTower);
		}
		horseMap[horse.row][horse.col] = null;
		renewPositionOfHorses(horseTower, nextRow, nextCol);
		
		
		return horseMap[nextRow][nextCol].size();
	}
	
	private static Boolean isBlue(int nextRow, int nextCol) {
		return (nextRow < 1 || n < nextRow || nextCol < 1 || n < nextCol || map[nextRow][nextCol] == 2);
	}
	
	private static void renewPositionOfHorses(List<Horse> horses, int nextRow, int nextCol) {
		for(Horse horse : horses) {
			horse.row = nextRow;
			horse.col = nextCol;
		}
	}
	
	static class Horse {
		int idx;
		int row;
		int col;
		int rowMove;
		int colMove;
		Horse(int idx, int row, int col, int direction){
			this.idx = idx;
			this.row = row;
			this.col = col;
			switch(direction) {
			case 1 : 
				rowMove = 0;
				colMove = 1;
				break;
			case 2 :
				rowMove = 0;
				colMove = -1;
				break;
			case 3 : 
				rowMove = -1;
				colMove = 0;
				break;
			case 4 :
				rowMove = 1;
				colMove = 0;
				break;
			}
		}
	}

}


