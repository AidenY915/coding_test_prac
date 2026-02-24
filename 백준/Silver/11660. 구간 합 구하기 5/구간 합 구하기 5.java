import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		StringTokenizer st = new StringTokenizer(line, " ");
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[][] arr = new int[n][];
		for(int i = 0; i < n; i++) {
			arr[i] = new int[n];
		}
		
		for(int i = 0; i < n; i++) {
			line = br.readLine();
			st = new StringTokenizer(line, " ");
			for(int j = 0; j < n; j++) { 
				arr[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		int[][] partSum = new int[n][n]; 		//자동 0초기화
		partSum[0][0] = arr[0][0];
		for(int i = 1; i < n; i++) {
			partSum[0][i] = partSum[0][i-1] + arr[0][i];
			partSum[i][0] = partSum[i-1][0] + arr[i][0];
		}
			
		for(int i = 1; i < n; i++) {
			for(int j = 1; j < n; j++) {
				partSum[i][j] = partSum[i-1][j] + partSum[i][j-1] - partSum[i-1][j-1] + arr[i][j];
			}
		}
		
		for(int i = 0; i < m; i++) {
			line = br.readLine();
			st = new StringTokenizer(line, " ");
			int x1 = Integer.parseInt(st.nextToken())-1;
			int y1 = Integer.parseInt(st.nextToken())-1;
			int x2 = Integer.parseInt(st.nextToken())-1;
			int y2 = Integer.parseInt(st.nextToken())-1;
			
			int rslt = partSum[x2][y2];
			if(y1 > 0 && x1 > 0) {
				rslt = rslt - partSum[x2][y1 - 1] - partSum[x1 - 1][y2] + partSum[x1 - 1][y1 - 1];
			}
			else if (y1 > 0)
			{
				rslt -= partSum[x2][y1 - 1];
			}
			else if (x1 > 0)
			{
				rslt -= partSum[x1 -1][y2];
			}
			
			System.out.println(rslt);
		}
				
	}

}
