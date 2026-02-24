import java.util.*;
import java.io.*;

//중앙은 항상 w
//각 면의 중앙은 고정

public class Main{
	private static int n;
	private static Map<Character, char[][]> cube = new HashMap<>();
	
	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder(12000);
		n = Integer.parseInt(br.readLine());
		
		for(int i = 0 ; i < n; i++) {
			initCube();
			int m = Integer.parseInt(br.readLine());
			String[] inputs = br.readLine().split(" ");
//			sb.append("--------------기록----------------\n");
			for(String input : inputs) {
				rotateCube(input.charAt(0), input.charAt(1));
//				printFace(sb, 'U');
//				sb.append("\n");
			}
			printFace(sb, 'U');
		}
		System.out.print(sb.toString());
	}
	
	private static void printFace(StringBuilder sb, char faceName) {
		char[][] face = cube.get(faceName);
		
		for(int i = 0 ; i < 3; i++) {
			for(int j = 0 ; j< 3 ;j++) {
				sb.append(face[i][j]);
			}
			sb.append("\n");
		}
	}
	
	private static void initCube() {
		cube.put('U', new char[][] {{'w','w','w'},{'w','w','w'},{'w','w','w'}});
		cube.put('D', new char[][] {{'y','y','y'},{'y','y','y'},{'y','y','y'}});
		cube.put('F', new char[][] {{'r','r','r'},{'r','r','r'},{'r','r','r'}});
		cube.put('B', new char[][] {{'o','o','o'},{'o','o','o'},{'o','o','o'}});
		cube.put('L', new char[][] {{'g','g','g'},{'g','g','g'},{'g','g','g'}});
		cube.put('R', new char[][] {{'b','b','b'},{'b','b','b'},{'b','b','b'}});
	}
	
	private static void rotateCube(char face, char direction) {
		switch(face) {
		case 'U' : rotateUp(direction); break;
		case 'D' : rotateDown(direction); break;
		case 'R' : rotateRight(direction); break;
		case 'L' : rotateLeft(direction); break;
		case 'F' : rotateFront(direction); break;
		case 'B' : rotateBack(direction); break;
		
		}
	}
	
	private static void rotateUp(char direction) {
		//+ F L B R 하나씩 뒤로 밀어야 함
		//- R B L F
		List<Character> order = new ArrayList(List.of('F', 'L', 'B', 'R'));
		if(direction == '-') {
			Collections.reverse(order);
		}
		
		char[] lastLine = cube.get(order.get(3))[0];
		for(int i = 3 ; i >= 1; i--) {
			char[][] prevFace = cube.get(order.get(i-1));
			char[][] curFace = cube.get(order.get(i));
			curFace[0] = prevFace[0]; //윗줄 교체
		}
		cube.get(order.get(0))[0] = lastLine;
		
		//이제 윗면 돌리기
		rotateFace('U', direction);
	}
	
	private static void rotateDown(char direction) {
		//+ R B L F
		List<Character> order = new ArrayList(List.of('R', 'B', 'L', 'F'));
		if(direction == '-') {
			Collections.reverse(order);
		}
		
		char[] lastLine = cube.get(order.get(3))[2];
		for(int i = 3 ; i >= 1; i--) {
			char[][] prevFace = cube.get(order.get(i-1));
			char[][] curFace = cube.get(order.get(i));
			curFace[2] = prevFace[2]; //아랫줄 교체
		}
		cube.get(order.get(0))[2] = lastLine;
		
		rotateFace('D', direction);
	}
	
	private static void rotateRight(char direction) {
		char[][] up = cube.get('U');
		char[][] back = cube.get('B');
		char[][] down = cube.get('D');
		char[][] front = cube.get('F');
		
		char[][] lines = new char[][] {
			{up[2][2],up[1][2],up[0][2]}, 
			{back[0][0], back[1][0], back[2][0]}, 
			{down[2][2], down[1][2], down[0][2]}, 
			{front[2][2], front[1][2], front[0][2]}
			};
			
		
			
		if(direction == '+') {
			char[] tmpLine = lines[3];
			lines[3] = lines[2];
			lines[2] = lines[1];
			lines[1] = lines[0];
			lines[0] = tmpLine;
		}
		else {
			char[] tmpLine = lines[0];
			lines[0] = lines[1];
			lines[1] = lines[2];
			lines[2] = lines[3];
			lines[3] = tmpLine;
		}
		
		up[2][2] = lines[0][0];
		up[1][2] = lines[0][1];
		up[0][2] = lines[0][2];
		
		back[0][0] = lines[1][0];
		back[1][0] = lines[1][1];
		back[2][0] = lines[1][2];
		
		down[2][2] = lines[2][0];
		down[1][2] = lines[2][1];
		down[0][2] = lines[2][2];
		
		front[2][2] = lines[3][0];
		front[1][2] = lines[3][1];
		front[0][2] = lines[3][2];
		
		rotateFace('R', direction);
	}
	
	
	
	private static void rotateLeft(char direction) {
		char[][] up = cube.get('U');
		char[][] front = cube.get('F');
		char[][] down = cube.get('D');
		char[][] back = cube.get('B');
		
	
		
		char[][] lines = new char[][] {
			{up[0][0],up[1][0],up[2][0]}, 
			{front[0][0], front[1][0], front[2][0]}, 
			{down[0][0], down[1][0], down[2][0]}, 
			{back[2][2], back[1][2], back[0][2]}
			};
			
		
			
		if(direction == '+') {
			char[] tmpLine = lines[3];
			lines[3] = lines[2];
			lines[2] = lines[1];
			lines[1] = lines[0];
			lines[0] = tmpLine;
		}
		else {
			char[] tmpLine = lines[0];
			lines[0] = lines[1];
			lines[1] = lines[2];
			lines[2] = lines[3];
			lines[3] = tmpLine;
		}
		
		up[0][0] = lines[0][0];
		up[1][0] = lines[0][1];
		up[2][0] = lines[0][2];
		
		front[0][0] = lines[1][0];
		front[1][0] = lines[1][1];
		front[2][0] = lines[1][2];
		
		down[0][0] = lines[2][0];
		down[1][0] = lines[2][1];
		down[2][0] = lines[2][2];
		
		back[2][2] = lines[3][0];
		back[1][2] = lines[3][1];
		back[0][2] = lines[3][2];
		
		rotateFace('L', direction);
	}
	
	private static void rotateFront(char direction) {
		
		char[][] up = cube.get('U');
		char[][] right = cube.get('R');
		char[][] down = cube.get('D');
		char[][] left = cube.get('L');
		
	
		
		char[][] lines = new char[][] {
			{up[2][0],up[2][1],up[2][2]}, 
			{right[0][0], right[1][0], right[2][0]}, 
			{down[0][2], down[0][1], down[0][0]}, 
			{left[2][2], left[1][2], left[0][2]}
			};
			
		
			
		if(direction == '+') {
			char[] tmpLine = lines[3];
			lines[3] = lines[2];
			lines[2] = lines[1];
			lines[1] = lines[0];
			lines[0] = tmpLine;
		}
		else {
			char[] tmpLine = lines[0];
			lines[0] = lines[1];
			lines[1] = lines[2];
			lines[2] = lines[3];
			lines[3] = tmpLine;
		}
		
		up[2][0] = lines[0][0];
		up[2][1] = lines[0][1];
		up[2][2] = lines[0][2];
		
		right[0][0] = lines[1][0];
		right[1][0] = lines[1][1];
		right[2][0] = lines[1][2];
		
		down[0][2] = lines[2][0];
		down[0][1] = lines[2][1];
		down[0][0] = lines[2][2];
		
		left[2][2] = lines[3][0];
		left[1][2] = lines[3][1];
		left[0][2] = lines[3][2];
		
		rotateFace('F', direction);
		
	}
	
private static void rotateBack(char direction) {
		
		char[][] up = cube.get('U');
		char[][] left = cube.get('L');
		char[][] down = cube.get('D');
		char[][] right = cube.get('R');
		
	
		
		char[][] lines = new char[][] {
			{up[0][2],up[0][1],up[0][0]}, 
			{left[0][0], left[1][0], left[2][0]}, 
			{down[2][0], down[2][1], down[2][2]}, 
			{right[2][2], right[1][2], right[0][2]}
			};
			
		
			
		if(direction == '+') {
			char[] tmpLine = lines[3];
			lines[3] = lines[2];
			lines[2] = lines[1];
			lines[1] = lines[0];
			lines[0] = tmpLine;
		}
		else {
			char[] tmpLine = lines[0];
			lines[0] = lines[1];
			lines[1] = lines[2];
			lines[2] = lines[3];
			lines[3] = tmpLine;
		}
		
		up[0][2] = lines[0][0];
		up[0][1] = lines[0][1];
		up[0][0] = lines[0][2];
		
		left[0][0] = lines[1][0];
		left[1][0] = lines[1][1];
		left[2][0] = lines[1][2];
		
		down[2][0] = lines[2][0];
		down[2][1] = lines[2][1];
		down[2][2] = lines[2][2];
		
		right[2][2] = lines[3][0];
		right[1][2] = lines[3][1];
		right[0][2] = lines[3][2];
		
		rotateFace('B', direction);
		
	}
	
	
	
	private static void rotateFace(char faceName, char direction) {
		char[][] face = cube.get(faceName);
		//0행 2열 2행 0열
		//시계방향
		if(direction == '+') {
			char[] tmp = new char[] {face[1][0], face[0][0]};
			
			face[0][0] = face[2][0];
			face[1][0] = face[2][1];
			
			face[2][0] = face[2][2];
			face[2][1] = face[1][2];
			
			face[2][2] = face[0][2];
			face[1][2] = face[0][1];
			
			face[0][2] = tmp[1];
			face[0][1] = tmp[0];
		}
		else {
			
			char[] tmp = new char[] {face[0][1], face[0][2]};

			face[0][2] = face[2][2];
			face[0][1] = face[1][2];
			
			face[2][2] = face[2][0];
			face[1][2] = face[2][1];
			
			face[2][0] = face[0][0];
			face[2][1] = face[1][0];
			
			face[0][0] = tmp[1];
			face[1][0] = tmp[0];
			
		}
		
	}
	
}