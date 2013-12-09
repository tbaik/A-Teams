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

	public void addEdges(Collection<Edge> edges) {
		for (Edge e : edges) {
			// only adds if it's not present, so we can just add wihtout
			// checking here.
			this.edges.add(e);
		}
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
}
