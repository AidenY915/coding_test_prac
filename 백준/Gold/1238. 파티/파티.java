import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {

	/*
	 최단 거리 다익스트라 -> 우선수위 힙
	 모든 점에서 다익스트라 후 더하기
	 
	 */
	private static List<Edge> edges;
	private static List<Vertex> vertexes;
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws Exception {
		String[] input = br.readLine().split(" ");
		int n = Integer.parseInt(input[0]); // 사람 수
		int m = Integer.parseInt(input[1]); // 엣지 수
		int x = Integer.parseInt(input[2]); //도착지
		
		
		edges = new ArrayList<>(m+1);
		edges.add(null);
		vertexes = new ArrayList<>(n+1);
		vertexes.add(null);
		for(int i = 1 ; i <= n; i++) vertexes.add(new Vertex(i));
		for(int i = 1 ; i <= m; i++) {
			input = br.readLine().split(" ");
			int s = Integer.parseInt(input[0]);
			int e = Integer.parseInt(input[1]);
			int w = Integer.parseInt(input[2]);
			
			Edge newEdge = new Edge(e,s,w);	// 엣지를 거꾸로 생성
			edges.add(newEdge);
			vertexes.get(e).edges.add(newEdge);
		}
		
		int[] toXDistances = dijkstra(x);
		reverseEdges();
		int[] fromXDistances = dijkstra(x);
		int max = Integer.MIN_VALUE;
		for(int i = 1; i <= n; i++) {
			max = Math.max(max, toXDistances[i] + fromXDistances[i]);
		}
		System.out.print(max);
		
	}
	
	private static void reverseEdges() {
		for(Edge edge : edges) {
			if(edge == null) continue;
			vertexes.get(edge.s).edges.remove(edge);
			vertexes.get(edge.e).edges.add(edge);
			int tmp = edge.s;
			edge.s = edge.e;
			edge.e = tmp;
		}
	}
	
	private static int[] dijkstra(int start) {
		PriorityQueue<Pair> pq = new PriorityQueue<>((p1, p2) -> p1.distance - p2.distance);
		int[] distances = vertexes.stream().mapToInt(e -> Integer.MAX_VALUE).toArray();
		distances[0] = -1;
		distances[start] = 0;
		pq.add(new Pair(vertexes.get(start), distances[start]));
		while(!pq.isEmpty()) {
			Pair pair = pq.remove();
			Vertex curV = pair.vertex;
			int curD = pair.distance;
			for(Edge edge : curV.edges) {
				if(curD + edge.w < distances[edge.e]) {
					distances[edge.e] = curD + edge.w;
					pq.add(new Pair(vertexes.get(edge.e), distances[edge.e]));
				}
			}
		}
		return distances;
	}
	
	private static class Edge {
		public int s;
		public int e;
		public int w;
		
		Edge(int s, int e, int w){
			this.s = s;
			this.e = e;
			this.w = w;
		}
	}
	
	private static class Vertex {
		public int index;
		public List<Edge> edges = new LinkedList<>();
		
		Vertex(int index){
			this.index = index;
		}
	}
	
	private static class Pair{
		public Vertex vertex;
		public int distance;
		
		Pair(Vertex v, int d){
			vertex = v;
			distance = d;
		}
	}

}
