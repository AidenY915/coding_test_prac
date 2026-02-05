import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static int n; //최고층
	private static int k; //자리수
	private static int p; //끌 수 있는 자리 수
	private static int x; //현재층
	private static int[][] led = new int[10][7];
 
	
	//dp로 최적화 가능
	public static void main(String[] args) throws Exception{
		int[] input = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = input[0];
		k = input[1];
		p = input[2];
		x = input[3];
		int rslt = 0;
		int q = x;
		int[] digitsOfx = new int[k];
		
		for(int i = k-1; i >= 0; i--) {
			digitsOfx[i] = q%10;
			q/=10;
		}
		
		led[0] = new int[] {1,1,1,1,1,1,0};
		led[1] = new int[] {0,1,1,0,0,0,0};
		led[2] = new int[] {1,1,0,1,1,0,1};
		led[3] = new int[] {1,1,1,1,0,0,1};
		led[4] = new int[] {0,1,1,0,0,1,1};
		led[5] = new int[] {1,0,1,1,0,1,1};
		led[6] = new int[] {1,0,1,1,1,1,1};
		led[7] = new int[] {1,1,1,0,0,0,0};
		led[8] = new int[] {1,1,1,1,1,1,1};
		led[9] = new int[] {1,1,1,1,0,1,1};
		
		for(int i = 1 ; i <= n; i++) {
			int num = i;
			int sum = 0;
			if(x == i) continue;
			for(int j = k-1; j >= 0 ;j--) {
				int digit = num % 10;
				sum+=get1NumOfXor(led[digitsOfx[j]], led[digit]);
				num/=10;
				if(sum > p) {
					break;
				}
			}
			if(sum <= p) rslt++;
		}
		
		System.out.println(rslt);
		
		
	}
	
	private static int get1NumOfXor(int[] binary1, int[] binary2) {
		int sum = 0 ;
		for(int i = 0; i < 7; i++) {
			if(binary1[i] != binary2[i]) sum++;
		}
		return sum;
	}

}
