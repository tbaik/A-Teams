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

	public ArrayList<Path> getPaths() {
		return paths;
	}

	public String toString() {
		String pathStr = "";
		for (Path e : paths) {
			pathStr += e.toString() + "\n";
		}
		return pathStr;
	}

	public void replaceAndRecomputeEdges(int randomPath, Path improvedPath) {
		for (Edge e : paths.get(randomPath).getEdges())
			edges.remove(e);

		paths.set(randomPath, improvedPath);

		for (Edge e1 : improvedPath.getEdges()) {
			edges.add(e1);
		}
	}

	public void replaceIfBetter(Path p) {
		int indexToRemove = -1;
		for (int i = 0; i < paths.size(); i++) {
			if ((paths.get(i).getStart().equals(p.getStart()) && paths.get(i).getEnd().equals(p.getEnd()) || (paths.get(i).getStart()
					.equals(p.getEnd()) && paths.get(i).getEnd().equals(p.getStart())))) {
				indexToRemove = i;
				break;
			}
		}
		Path oldPath = null;
		if (!(indexToRemove == -1))
			oldPath = paths.remove(indexToRemove);
		// recalculate edges hashset
		for (Path p1 : paths)
			for (Edge pEdge : p1.getPaths()) {
				edges.add(pEdge);
			}

		int nCost = 0;
		for (Edge e1 : p.getEdges()) {
			if (!edges.contains(e1)) {
				nCost += e1.getCost();
			}
		}

		int oCost = 0;
		if (oldPath != null)
			for (Edge e1 : oldPath.getEdges()) {
				if (!edges.contains(e1)) {
					nCost += e1.getCost();
				}
			}

		if (nCost < oCost || oldPath == null) {
			addPath(p);
		} else {
			addPath(oldPath);
		}
	}

}
