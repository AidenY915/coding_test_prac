import java.util.*;
import java.util.stream.*;
import java.io.*;

public class Main {
	
	private static int n, m, l;
	private static List<Integer> ageForEmploy;
	private static List<Node> nodes;
	

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] intInput = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = intInput[0]; m = intInput[1]; l = intInput[2];
		
		StringBuilder sb = new StringBuilder(l * 4);
		
		ageForEmploy = new ArrayList<>(n + 1);
		ageForEmploy.add(null);
		ageForEmploy.addAll(Stream.of(br.readLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
		
		nodes = new ArrayList<>(n + 1);
		nodes.add(null);
		for(int i = 1 ; i<=n; i++) {
			nodes.add(new Node(i));
		}
		
		
		for(int i  = 0; i < m; i++) {
			intInput = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			Node higher = nodes.get(intInput[0]);
			Node lower = nodes.get(intInput[1]);
			higher.children.add(lower);
			lower.parents.add(higher);
		}
		
		
		for(int i = 0; i < l; i++) {
			String[] strInput = br.readLine().split(" ");
			String cmd = strInput[0]; 
			switch (cmd) {
			case "P":
				Node target = null;
				for(Node node : nodes) {
					if(node == null) continue;
					if(node.employId == Integer.parseInt(strInput[1]))
						target = node;
				}
				sb.append(getMin(target)).append("\n");
				break;
			case "T":
				Node target1 = null, target2 = null;
				for(Node node : nodes) {
					if(node == null) continue;
					if(node.employId == Integer.parseInt(strInput[1]))
						target1 = node;
					else if(node.employId == Integer.parseInt(strInput[2]))
						target2 = node;
				}
				
				swap(target1, target2);
				break;
			}
		}
		
		System.out.println(sb.toString());
	}
	
	private static String getMin(Node node) {
		if(node.parents.isEmpty())
			return "*";
		nodes.subList(1, nodes.size()).stream().forEach(n -> {n.isVisited = false;});
		int min = Integer.MAX_VALUE;
		for(Node parent : node.parents) {
			min = Math.min(dfs(parent), min);
		}
		
		return String.valueOf(min);
	}
	
	private static int dfs(Node node) {
		if(node.isVisited) return Integer.MAX_VALUE;
		node.isVisited = true;
		int min = ageForEmploy.get(node.employId);
		for(Node parent : node.parents) {
			min = Math.min(dfs(parent), min);
		}
		return min;
	}
	
	private static void swap(Node n1, Node n2) {
		int tmpId = n1.employId;
		n1.employId = n2.employId;
		n2.employId = tmpId;
	}
	

	private static class Node{
		int employId;
		List<Node> children = new LinkedList<>();
		List<Node> parents = new LinkedList<>();
		boolean isVisited = false;
		Node(int employId){
			this.employId = employId;
		}
	}
	
	
	
}

