import java.util.*;
import java.io.*;
import java.util.stream.*;

public class Main {

	private static Byte[][] gear = new Byte[5][8];
	private static int[] topPoint = new int[] {-1, 0, 0, 0, 0}; //12시가 0, 2, 4, 6 즉 12시 + 2가 오른쪽 12시 + 6이 왼쪽
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int i = 1; i<=4; i++) {
			gear[i] = Stream.of(br.readLine().split("")).map(Byte::parseByte).toArray(Byte[]::new);
		}
		
		
		int n = Integer.parseInt(br.readLine());
		for(int i = 0; i < n ;i++) {
			int[] inputs = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			int gearIdx = inputs[0];
			int direction = inputs[1];
			rotateAllGear(checkNextMoveOfGears(gearIdx, direction));
		}
		
		int rslt = 0;
		for(int i = 1; i <= 4; i++) {
			if(getTopPolar(i) == 1) rslt += Math.pow(2, i-1);
		}
		System.out.println(rslt);
	}
	
	private static void rotateAllGear(int[] moveGear) {
		for(int i = 1; i <= 4; i++) {
			topPoint[i] = (topPoint[i] -  moveGear[i] + 8) % 8;
		}
	}
	
	private static int[] checkNextMoveOfGears(int gearIdx, int direction) {
		int leftGearIdx = gearIdx - 1;
		int rightGearIdx = gearIdx + 1;
		int[] moveGear = new int[5];
		
		moveGear[gearIdx] = direction;
		
		spreadRotationToLeftByDfs(leftGearIdx, direction * -1, moveGear);
		spreadRotationToRightByDfs(rightGearIdx, direction * -1, moveGear);
		
		return moveGear;
	}
	

	private static void spreadRotationToLeftByDfs(int gearIdx, int direction, int[] moveGear) {
		if(gearIdx < 1) return;
		if(getRightPolar(gearIdx) == getLeftPolar(gearIdx + 1))
			return;
		moveGear[gearIdx] = direction;
		spreadRotationToLeftByDfs(gearIdx - 1 , direction * -1, moveGear);
	}
	
	private static void spreadRotationToRightByDfs(int gearIdx, int direction, int[] moveGear) {
		if(gearIdx > 4) return;
		if(getLeftPolar(gearIdx) == getRightPolar(gearIdx - 1))
			return;
		moveGear[gearIdx] = direction;
		spreadRotationToRightByDfs(gearIdx + 1 , direction * -1, moveGear);
	}
	
	private static Byte getRightPolar(int gearIdx) {
		return gear[gearIdx][(topPoint[gearIdx] + 2) % 8];
	}
	
	private static Byte getLeftPolar(int gearIdx) {
		return gear[gearIdx][(topPoint[gearIdx] + 6) % 8];
	}
	private static Byte getTopPolar(int gearIdx) {
		return gear[gearIdx][(topPoint[gearIdx])];
	}
	
}
