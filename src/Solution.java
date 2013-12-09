import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Solution {
	private HashSet<Edge> edges;

	public Solution(HashSet<Edge> edges) {
		this.edges = edges;
		if(edges == null){
			edges = new HashSet<Edge>();
		}
	}
	
	public int getTotalCost() {
		int totalCost = 0;
		if (edges != null){
			for (Edge e : edges)
				totalCost += e.getCost();
		}else
			totalCost = Integer.MAX_VALUE;
		return totalCost;
	}
	
	public void addEdges(Collection<Edge> edges){
		for(Edge e : edges){
			//only adds if it's not present, so we can just add wihtout checking here.
			this.edges.add(e);
		}
	}

	public HashSet<Edge> getEdges() {
		return edges;
	}

}
