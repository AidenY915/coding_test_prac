import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for(int i = 0 ; i < n ; i++)
			arr[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(arr);
		int rslt = 0;
		for(int i = 0 ; i < n ; i++) {					//0 고려
			int p = 0; int q = n - 1;					//음수 고려
			while(p < q) {
				int sum = arr[p] + arr[q];
				if(p == i) 
				{
					p++;
					continue;
				}
				if(q == i) 
				{
					q--;
					continue;
				}
				else if(sum == arr[i]) {
					rslt++;
					break;
				}
				else if(sum < arr[i])
					p++;
				else if(sum > arr[i])
					q--;
			}
		}
		System.out.println(rslt);
	}
}
