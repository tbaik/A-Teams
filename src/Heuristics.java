import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Heuristics {

	public static Solution improveHeuristicOne(Solution s, HashMap<Edge, Edge> sValuesMap, HashMap<Edge, HashSet<Edge>> sPathMap) {
		ArrayList<Edge> improvedPath = new ArrayList<Edge>();
		HashSet<Edge> firstSeg;
		HashSet<Edge> secondSeg;
		Random r = new Random();

		// Get a random path from the solution and improve on it.
		int randomPath = r.nextInt(s.getPaths().size());
		Path p = s.getPaths().get(randomPath);

		Edge lowestCost = p.getEdges().get(0);
		for (Edge e : sPathMap.get(new Edge(p.getStart(), p.getEnd(), -1))) {
			if (lowestCost.getCost() > e.getCost()) {
				lowestCost = sValuesMap.get(e);
			}
		}

		// From the start of the path, find the shortest path to the start of
		// the minimum weight edge.s
		firstSeg = sPathMap.get(new Edge(p.getStart(), lowestCost.getTo(), -1));
		// From the end of the minimum weight edge, find the shortest path to
		// the end of the path.
		secondSeg = sPathMap.get(new Edge(lowestCost.getTo(), p.getEnd(), -1));

		for (Edge e : firstSeg) {
			improvedPath.add(new Edge(e.getFrom(), e.getTo(), sValuesMap.get(e).getCost()));
		}
		improvedPath.add(lowestCost);
		for (Edge e : secondSeg) {
			improvedPath.add(new Edge(e.getFrom(), e.getTo(), sValuesMap.get(e).getCost()));
		}

		// update path and return
		s.replaceAndRecomputeEdges(randomPath, new Path(improvedPath, p.getStart(), p.getEnd()));

		return s;
	}

	// Creates a new solution from an existing solution, using only shortest
	// paths. O(pm) but it may be faster in our implementation
	// since we run the shortest path algorithm once and just do O(1) searches
	// instead of running the shortest path algorithm
	// for every time this method is called.
	public static Solution improveHeuristicFour(Solution sol, HashMap<Edge, Edge> sValuesMap, HashMap<Edge, HashSet<Edge>> sPathMap) {
		// for each source-destination pair (a,b) in s
		for (int i = 0; i < sol.getPaths().size(); i++) {
			Node n1 = sol.getPaths().get(i).getStart();
			Node n2 = sol.getPaths().get(i).getEnd();
			ArrayList<Edge> tempEdges = new ArrayList<Edge>();

			for (Edge e1 : sPathMap.get(new Edge(n1, n2, -1))) {
				tempEdges.add(new Edge(e1.getFrom(), e1.getTo(), sValuesMap.get(e1).getCost()));
			}

			Path tempPath = new Path(tempEdges, n1, n2);
			sol.replaceAndRecomputeEdges(i, tempPath);

		}
		return sol;
	}

	public static Solution CB1andC2(Solution solToImprove, Solution randomSolution, HashMap<Edge, Edge> sValuesMap,
			HashMap<Edge, HashSet<Edge>> sPathMap, HashSet<Node> startNodes, HashSet<Node> endNodes, HashMap<Edge, Edge> edges) {
		// find similar edges
		HashSet<Edge> similar = new HashSet<Edge>();
		for (Edge e : solToImprove.getEdges())
			if (randomSolution.getEdges().contains(e))
				similar.add(e);

		// put end nodes in an array so we can remove each randomly chosen one.
		ArrayList<Node> endNodesArr = new ArrayList<Node>();
		for (Node e : endNodes) {
			endNodesArr.add(e);
		}
		// put end nodes in an array so we can remove each randomly chosen one.
		ArrayList<Node> startNodesArr = new ArrayList<Node>();
		for (Node e : startNodes) {
			startNodesArr.add(e);
		}
		Solution sol = new Solution();
		for (Edge e : similar) {
			if (endNodesArr.size() == 0 || startNodesArr.size() == 0)
				return sol;
			// get random start and end nodes
			int sIndex = new Random().nextInt(startNodesArr.size());
			Node start = startNodesArr.get(sIndex);
			startNodesArr.remove(sIndex);

			int eIndex = new Random().nextInt(endNodesArr.size());
			Node end = endNodesArr.get(eIndex);
			endNodesArr.remove(eIndex);

			int costFromFirst = sValuesMap.get(new Edge(start, e.getFrom(), -1)).getCost()
					+ sValuesMap.get(new Edge(end, e.getTo(), -1)).getCost();

			int costToFirst = sValuesMap.get(new Edge(start, e.getTo(), -1)).getCost()
					+ sValuesMap.get(new Edge(end, e.getFrom(), -1)).getCost();

			HashSet<Edge> edgesToTake = new HashSet<Edge>();
			if (costFromFirst < costToFirst) {
				for (Edge e1 : sPathMap.get(new Edge(start, e.getFrom(), -1))) {
					edgesToTake.add(new Edge(e1.getFrom(), e1.getTo(), sValuesMap.get(e1).getCost()));
				}
				edgesToTake.add(e);
				for (Edge e1 : sPathMap.get(new Edge(end, e.getTo(), -1))) {
					edgesToTake.add(new Edge(e1.getFrom(), e1.getTo(), sValuesMap.get(e1).getCost()));
				}
			} else {
				for (Edge e1 : sPathMap.get(new Edge(start, e.getTo(), -1))) {
					edgesToTake.add(new Edge(e1.getFrom(), e1.getTo(), sValuesMap.get(e1).getCost()));
				}
				edgesToTake.add(e);
				for (Edge e1 : sPathMap.get(new Edge(end, e.getFrom(), -1))) {
					edgesToTake.add(new Edge(e1.getFrom(), e1.getTo(), sValuesMap.get(e1).getCost()));
				}
			}
			
			ArrayList<Edge> edgesArray = new ArrayList<Edge>();
			for(Edge e1 : edgesToTake)
				edgesArray.add(e1);
			
			Path p = new Path(edgesArray ,start, end);
			sol.replaceIfBetter(p);
		}
		
		for(int i = 0; i < startNodesArr.size(); i++){

			// get random start and end nodes
			int sIndex = new Random().nextInt(startNodesArr.size());
			Node start = startNodesArr.get(sIndex);
			startNodesArr.remove(sIndex);

			int eIndex = new Random().nextInt(endNodesArr.size());
			Node end = endNodesArr.get(eIndex);
			endNodesArr.remove(eIndex);
			
			Path path = Main.generateRandomPath(start, end, edges);
			sol.addPath(path);
		}

		return sol;
	}

}
