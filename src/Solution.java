import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Solution {
	private HashSet<Edge> edges = new HashSet<Edge>();
	private ArrayList<Path> paths = new ArrayList<Path>();

	public Solution() {
	}

	public int getTotalCost() {
		int totalCost = 0;
		if (edges != null) {
			for (Edge e : edges)
				totalCost += e.getCost();
		} else
			totalCost = Integer.MAX_VALUE;
		return totalCost;
	}

	public HashSet<Edge> getEdges() {
		return edges;
	}

	public void addPath(Path e) {
		this.paths.add(e);
		for (Edge pEdge : e.getPaths()) {
			edges.add(pEdge);
		}
	}
	
	public String toString(){
		String pathStr = "";
		for(Path e : paths){
			pathStr += e.toString() + "\n";
		}
		return pathStr;
	}
}
