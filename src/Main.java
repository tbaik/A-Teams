import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("Tests" + File.separator + "tcase2.txt"));

		// get inputs from input file
		HashSet<Node> nodes = new HashSet<Node>();
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if (nextLine.trim().equals("*"))
				break;
			nodes.add(new Node(nextLine.trim()));
		}

		HashSet<Node> startNodes = new HashSet<Node>();
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if (nextLine.trim().equals("*"))
				break;
			startNodes.add(new Node(nextLine.trim()));
		}

		HashSet<Node> endNodes = new HashSet<Node>();
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if (nextLine.trim().equals("*"))
				break;
			endNodes.add(new Node(nextLine.trim()));
		}

		HashMap<Edge, Edge> edges = new HashMap<Edge, Edge>();
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			Scanner lineScanner = new Scanner(nextLine);
			Edge e = new Edge(new Node(lineScanner.next().trim()), new Node(lineScanner.next().trim()), lineScanner.nextInt());
			edges.put(e, e);
		}

		// Hashmap that holds the results of Floyd-Warshall's shortest paths
		// algorithm
		HashMap<Edge, Edge> sValuesMap = new HashMap<Edge, Edge>();
		// this Hashmap keeps all the shortest paths actually used not just the
		// values
		HashMap<Edge, HashSet<Edge>> sPathMap = new HashMap<Edge, HashSet<Edge>>();
		for (Node e1 : nodes)
			for (Node e2 : nodes) {
				sValuesMap.put(new Edge(e1, e2, Integer.MAX_VALUE), new Edge(e1, e2, Integer.MAX_VALUE));
				HashSet<Edge> hs = new HashSet<Edge>();
				hs.add(new Edge(e1, e2, -1));
				sPathMap.put(new Edge(e1, e2, 0), hs);
			}
		for (Node e1 : nodes) {
			sValuesMap.put(new Edge(e1, e1, 0), new Edge(e1, e1, 0));
		}
		for (Edge e : edges.keySet()) {
			sValuesMap.put(e, e);
		}

		for (Node e1 : nodes)
			for (Node e2 : nodes)
				for (Node e3 : nodes) {
					int directCost = sValuesMap.get(new Edge(e2, e3, -1)).getCost();
					int firstCost = sValuesMap.get(new Edge(e2, e1, -1)).getCost();
					int secCost = sValuesMap.get(new Edge(e1, e3, -1)).getCost();
					// adding 2 ints might give overflow, just skip over it for
					// now by doing this check...
					if (firstCost == Integer.MAX_VALUE || secCost == Integer.MAX_VALUE)
						continue;
					if (directCost > (firstCost + secCost)) {
						sValuesMap.put(new Edge(e2, e3, (firstCost + secCost)), new Edge(e2, e3, (firstCost + secCost)));
						for (HashSet<Edge> e : sPathMap.values()) {
							if (e.contains(new Edge(e2, e3, -1))) {
								e.remove(new Edge(e2, e3, -1));
								if (sPathMap.containsKey(new Edge(e2, e1, -1)))
									e.addAll(sPathMap.get(new Edge(e2, e1, -1)));
								else
									e.add(new Edge(e2, e1, -1));
								if (sPathMap.containsKey(new Edge(e1, e3, -1)))
									e.addAll(sPathMap.get(new Edge(e1, e3, -1)));
								else
									e.add(new Edge(e1, e3, -1));
							}
						}
					}
				}

		// for (Edge e : sValuesMap.values()) {
		// System.out.println(e.getFrom().getName() + " " + e.getTo().getName()
		// + " " + e.getCost());
		// for (Edge e1 : sPathMap.get(e)) {
		// System.out.println(e1);
		// }
		// }

		// l represents how many shared memory objects we have
		int l = 5;
		// j represents how many solutions each memory can have
		int j = 10;

		// however long we should run the program
		int secondsToRun = 2000;
		if (args.length >= 2) {
			l = Integer.parseInt(args[0]);
			j = Integer.parseInt(args[1]);
		}
		// we can optionally accept a third param to run it for longer
		if (args.length >= 3) {
			secondsToRun = Integer.parseInt(args[2]);
		}
		SharedMemory sharedMem = new SharedMemory(l, j);
		// fill each Memory with j solutions, also called [ki] in the paper
		for (int i = 0; i < l; i++) {
			for (int k = 0; k < j; k++) {
				if (k > sharedMem.getMemoryArray()[i].getMemCapacity())
					throw new Exception("Created too many solutions...");
				// here we want to call one of the constructors to create a
				// solution and add it
				Solution solution = createRandomSolution(startNodes, endNodes, edges);
				// then we write it into the memory index
				sharedMem.getMemoryArray()[i].getSolutions().add(solution);
			}
		}

		calcAndPrintSharedMemoryCost(sharedMem);

		Solution bestSol = null;
		long timeToRunUntil = System.currentTimeMillis() + secondsToRun;
		while (System.currentTimeMillis() < timeToRunUntil) {
			// get a random improvement heuristic

			int memoryIndex = new Random().nextInt(l);

			Solution solToImprove = sharedMem.getMemoryArray()[memoryIndex].getRandomSolution();

			// run our heuristic and get a new solution
			Solution improvedSol = null;
//			 improvedSol = createRandomSolution(startNodes, endNodes, edges);
//			if (Math.random() < .5)
//				improvedSol = Heuristics.improveHeuristicOne(solToImprove, sValuesMap, sPathMap);
//			else
//				improvedSol = Heuristics.improveHeuristicFour(solToImprove, sValuesMap, sPathMap);
			 
			improvedSol = Heuristics.CB1andC2(solToImprove, sharedMem.getMemoryArray()[new Random().nextInt(l)].getRandomSolution(), sValuesMap, sPathMap, startNodes, endNodes, edges);
			// destroy a random by replacing our new solution in here.
			sharedMem.getMemoryArray()[memoryIndex].write(improvedSol);

			if (bestSol == null || improvedSol != null && improvedSol.getTotalCost() < bestSol.getTotalCost()) {
				bestSol = improvedSol;
			}
		}

		System.out.println(bestSol);
		calcAndPrintSharedMemoryCost(sharedMem);
	}

	// creates a random solution from given edges, sources, and destination
	// (making sure no loops exist)
	private static Solution createRandomSolution(HashSet<Node> startNodes, HashSet<Node> endNodes, HashMap<Edge, Edge> edges) {
		Solution sol = new Solution();

		// put end nodes in an array so we can remove each randomly chosen one.
		ArrayList<Node> endNodesArr = new ArrayList<Node>();
		for (Node e : endNodes) {
			endNodesArr.add(e);
		}

		// for each source node, choose a random end node and find a path to it.
		for (Node e : startNodes) {
			int index = new Random().nextInt(endNodesArr.size());
			Node dest = endNodesArr.get(index);
			endNodesArr.remove(index);

			Path path = generateRandomPath(e, dest, edges);
			sol.addPath(path);
		}
		return sol;
	}

	public static Path generateRandomPath(Node start, Node dest, HashMap<Edge, Edge> edges) {
		HashSet<Edge> usedEdges = new HashSet<Edge>();

		Node currentNode = start;
		Path path = new Path(start, dest);

		// we want to find the set of edges that go to node we have no yet
		// traverse..
		while (!currentNode.equals(dest)) {
			// find all the edges we can go to
			ArrayList<Edge> possibleEdges = new ArrayList<Edge>();
			for (Edge e : edges.keySet())
				if (e.getFrom().equals(currentNode) || e.getTo().equals(currentNode))
					possibleEdges.add(e);

			// remove edges we already we used, since we don't want cycles
			for (int i = possibleEdges.size() - 1; i >= 0; i--) {
				if (usedEdges.contains(possibleEdges.get(i)))
					possibleEdges.remove(i);
			}

			// if we have no more moves left, just go back to the start
			if (possibleEdges.size() == 0) {
				currentNode = start;
				path = new Path(start, dest);
				usedEdges = new HashSet<Edge>();
			} else {
				Edge edgeToUse = possibleEdges.get(new Random().nextInt(possibleEdges.size()));
				path.addEdge(edgeToUse);
				if (edgeToUse.getFrom().equals(currentNode))
					currentNode = edgeToUse.getTo();
				else
					currentNode = edgeToUse.getFrom();
				usedEdges.add(edgeToUse);
			}
		}
		return path;
	}

	private static void calcAndPrintSharedMemoryCost(SharedMemory sharedMem) {
		int cost = 0;
		int bestSolCost = Integer.MAX_VALUE;
		for (Memory mem : sharedMem.getMemoryArray()) {
			for (Solution sol : mem.getSolutions()) {
				cost += sol.getTotalCost();
				if (sol.getTotalCost() < bestSolCost){
					bestSolCost = sol.getTotalCost();
//					System.out.println("*");
//					for(Edge e : sol.getEdges())
//						System.out.println(e);
				}
				
			}
		}
		System.out.println("Best solution: " + bestSolCost);
		System.out.println("Total cost of all solutions	: " + cost + "\n");
	}
}
