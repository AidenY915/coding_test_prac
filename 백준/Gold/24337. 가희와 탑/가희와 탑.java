import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class Main {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static int n; //건물 개수
	private static int a; //왼쪽에서 보이는 건물 수
	private static int b; //오른쪽에서 보이는 건물 수
	
	//a 가 큰 경우 , b 가 큰 경우
	//남는 경우
	//중간을 채워야 하는 경우 a가 1이고, n이 남는 경우
	

	public static void main(String[] args) throws Exception{
		int[] input = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = input[0];
		a = input[1];
		b = input[2];
		int maxH = Math.max(a, b);
		StringBuilder sb = new StringBuilder((int)Math.pow(10,5) * 2);
		//불가능
		if(a+b > n+1) {
			System.out.println(-1);
			return;
		}
		
		if(a == 1) {
			sb.append(b).append(" ");
			for(int i = 0 ; i < n-b ; i++) {
				sb.append(1).append(" ");
			}
			for(int i = b-1; i>=1;i--) {
				sb.append(i).append(" ");
			}
			System.out.println(sb.toString());
			return;
			
		}
		
		
		for(int i = 0 ; i < n - b - a + 1; i++) {
			sb.append(1).append(" ");
		}
		for(int i = 1 ; i <= a - 1; i++) {
			sb.append(i).append(" ");
		}
		sb.append(maxH).append(" ");
		
		for(int i = b-1 ; i >= 1; i--) {
			sb.append(i).append(" ");
		}
		
		System.out.println(sb);
	}
	// 10 1 2
}
