import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import javax.sound.sampled.ReverbType;


public class Main {
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static int n;
	private static int m;
	private static Map<Integer, Vertex> vertexes; //incidence.get(정점) -> 인접리스트
	
	public static void main(String[] args) throws Exception { //5만 인접 행렬 불가 -> 인접 리스트
		String[] input = br.readLine().split(" ");
		n = Integer.parseInt(input[0]); 
		m = Integer.parseInt(input[1]);
		vertexes = new HashMap<>();
		for(int i = 0 ; i<n+1 ; i++)
			vertexes.put(i, new Vertex(Integer.MAX_VALUE));
		for(int i = 0 ; i < m; i++) {
			input = br.readLine().split(" ");
			Edge edge = new Edge(Integer.parseInt(input[0]),Integer.parseInt(input[1]),Integer.parseInt(input[2]));
			Edge reverseEdge =  new Edge(Integer.parseInt(input[1]),Integer.parseInt(input[0]),Integer.parseInt(input[2]));
			vertexes.get(edge.s).edges.add(edge);
			vertexes.get(edge.e).edges.add(reverseEdge);
		}
		vertexes.get(1).distance = 0;
		dijkstra(1);
		System.out.println(vertexes.get(n).distance);
	}
	
	private static void dijkstra(int s) {
		PriorityQueue<Vertex> pq = new PriorityQueue<>(
				(v1, v2)-> v1.distance - v2.distance
		);
		pq.add(vertexes.get(1));
		while(!pq.isEmpty()) {
			Vertex c = pq.poll();
			for(Edge edge : c.edges) {
				if(c.distance + edge.w < vertexes.get(edge.e).distance) {
					vertexes.get(edge.e).distance = c.distance + edge.w; 
					pq.add(new Vertex(vertexes.get(edge.e).distance, vertexes.get(edge.e).edges));
				}
			}
		}
	}
	
	private static class Edge{
		int s;
		int e;
		int w;
		Edge(int s, int e, int w){
			this.s = s;
			this.e = e;
			this.w = w;
		}
	}
	
	private static class Vertex{
		int distance;
		List<Edge> edges;
		
		Vertex(int distance){
			this.edges  = new LinkedList<Edge>();
			this.distance = distance;
		}
		
		Vertex(int distance, List<Edge> edges){
			this.edges  = edges;
			this.distance = distance;
		}
	}

}
