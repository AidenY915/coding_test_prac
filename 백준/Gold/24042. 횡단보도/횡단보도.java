import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Main {
	//Vertex와 edge 관계를 별도의 Map으로 분리
	//distance 당연히 Vertex 안에 있으며 안됨 -> 객체 지향적이지 않음
	private static PriorityQueue<Pair> pq = new PriorityQueue<Pair>(Comparator.comparingLong(p -> p.d));
	private static int n, m;
	private static List<Vertex> vertexes = new ArrayList<Vertex>(100001);
	private static long[] distance = new long[100001];
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = input[0];
		m = input[1];
		vertexes.add(null);
		for(int i = 1 ; i <= n; i++) {
			Vertex v = new Vertex(i);
			vertexes.add(v);
			distance[i] = Long.MAX_VALUE;
		}
		
		for(int i = 0; i < m; i++) {
			input = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			Vertex s = vertexes.get(input[0]), e = vertexes.get(input[1]);
			int order = i;
			Edge edge = new Edge(s, e, order);
			Edge reverseEdge = new Edge(e,s,order);
			s.edges.add(edge);
			e.edges.add(reverseEdge);
		}
		
		dijkstra();
		System.out.println(distance[n]);
	}
	
	private static void dijkstra() {
		pq.add(new Pair(vertexes.get(1), 0));
		while(!pq.isEmpty()) {
			Pair p = pq.remove();
			Vertex c = p.v;
			if(distance[c.index] != Long.MAX_VALUE)
				continue;
			long d = p.d;
			distance[c.index] = p.d;
			for(Edge edge : c.edges) {
				long nextTime = findNextPeriod(d, edge.order) + 1;
				if(nextTime < distance[edge.e.index]) {
					pq.add(new Pair(edge.e, nextTime));
				}
			}
		}
	}
	private static long findNextPeriod(long now, int order) {
		if (order >= now) return order;
		long diff = now - order;
		long k = (diff + m - 1) / m; // 올림 나눗셈
	    return order + k * m;
	}
	
	private static class Edge{
		public Vertex s;
		public Vertex e;
		public int order;
		
		Edge(Vertex s, Vertex e, int order){
			this.s = s;
			this.e = e;
			this.order = order;
		}
		
	}
	
	private static class Vertex {
		public List<Edge> edges = new LinkedList<>();
		int index;
		
		Vertex(int index){
			this.index=index;
		}
	}
	
	private static class Pair {
		public Vertex v;
		public long d;
		
		Pair(Vertex v, long d){
			this.v = v;
			this.d = d;
		}
	}

}
