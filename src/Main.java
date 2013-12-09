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
		Scanner sc = new Scanner(new File("input.txt"));
		
		//get inputs from input file
		HashSet<Node> nodes = new HashSet<Node>();
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			if(nextLine.trim().equals("*"))
				break;
			nodes.add(new Node(nextLine.trim()));
		}

		HashSet<Node> startNodes = new HashSet<Node>();
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			if(nextLine.trim().equals("*"))
				break;
			startNodes.add(new Node(nextLine.trim()));
		}

		HashSet<Node> endNodes = new HashSet<Node>();
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			if(nextLine.trim().equals("*"))
				break;
			endNodes.add(new Node(nextLine.trim()));
		}
		
		HashMap<Edge, Edge> edges = new HashMap<Edge, Edge>();
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			Scanner lineScanner = new Scanner(nextLine);
			Edge e = new Edge(new Node(lineScanner.next().trim()), new Node(lineScanner.next().trim()), lineScanner.nextInt());
			edges.put(e, e);
		}
		
		//Hashmap that holds the results of Floyd-Warshall's shortest paths algorithm
		HashMap<Edge, Edge> sPathMap = new HashMap<Edge, Edge>();
		for(Node e1 : nodes)
			for(Node e2 : nodes)
				sPathMap.put(new Edge(e1, e2, Integer.MAX_VALUE), new Edge(e1, e2, Integer.MAX_VALUE));
		for(Node e1 : nodes){
			sPathMap.put(new Edge(e1, e1, 0), new Edge(e1, e1, 0));
		}
		for(Edge e : edges.keySet()){
			sPathMap.put(e, e);
		}

		for(Node e1 : nodes)
			for(Node e2 : nodes)
				for(Node e3 : nodes)
				{
					int directCost = sPathMap.get(new Edge(e2, e3, -1)).getCost();
					int firstCost = sPathMap.get(new Edge(e2, e1, -1)).getCost();
					int secCost = sPathMap.get(new Edge(e1, e3, -1)).getCost();
					//adding 2 ints might give overflow, just skip over it for now by doing this check...
					if(firstCost == Integer.MAX_VALUE || secCost == Integer.MAX_VALUE)
						continue;
					if(directCost > (firstCost + secCost)){
						sPathMap.put(new Edge(e2, e3, (firstCost + secCost)), new Edge(e2, e3, (firstCost + secCost)));
					}
				}

//		for(Edge e : sPathMap.values()){
//			System.out.println(e.getFrom().getName() + " " + e.getTo().getName() + " " + e.getCost());
//		}
		
		
		
		// l represents how many shared memory objects we have
		int l = 10;
		// j represents how many solutions each memory can have
		int j = 20;
		
		//however long we should run the program
		int secondsToRun = 15;
		if (args.length >= 2) {
			l = Integer.parseInt(args[0]);
			j = Integer.parseInt(args[1]);
		}
		//we can optionally accept a third param to run it for longer
		if(args.length >= 3){
			secondsToRun = Integer.parseInt(args[2]);
		}
		SharedMemory sharedMem = new SharedMemory(l, j);
		// fill each Memory with j solutions, also called [ki] in the paper
		for (int i = 0; i < l; i++) {
			for (int k = 0; k < j; k++) {
				if(k > sharedMem.getMemoryArray()[i].getMemCapacity())
					throw new Exception("Created too many solutions...");
				//here we want to call one of the constructors to create a solution and add it
				Solution solution = createRandomSolution(startNodes, endNodes, edges);
				//then we write it into the memory index
				sharedMem.getMemoryArray()[i].getSolutions().add(solution);
			}
		}
		
		Solution bestSol = new Solution(null);
		long timeToRunUntil = System.currentTimeMillis() + secondsToRun * 1000;
		while(System.currentTimeMillis() < timeToRunUntil){
			//get a random improvement heuristic
			
			
			int memoryIndex = new Random().nextInt(l);
			
			Solution solToImprove = sharedMem.getMemoryArray()[memoryIndex].getRandomSolution();
			
			//run our heuristic and get a new solution
			Solution improvedSol = null;
			//destroy a random by replacing our new solution in here.
			sharedMem.getMemoryArray()[memoryIndex].write(improvedSol);
			
			if(improvedSol != null && improvedSol.getTotalCost() < bestSol.getTotalCost()){
				bestSol = improvedSol;
			}
		}
	}

	//creates a random solution from given edges, sources, and destination (making sure no loops exist)
	private static Solution createRandomSolution(HashSet<Node> startNodes, HashSet<Node> endNodes, HashMap<Edge, Edge> edges) {
		Solution sol = new Solution(null);
		
		//put end nodes in an array so we can remove each randomly chosen one.
		ArrayList<Node> endNodesArr = new ArrayList<Node>();
		for(Node e : endNodes){
			endNodesArr.add(e);
		}
		
		//for each source node, choose a random end node and find a path to it.
		for(Node e : startNodes){
			int index = new Random().nextInt(endNodesArr.size());
			Node dest = endNodesArr.get(index);
			endNodesArr.remove(index);
			
			
		}
		
		return sol;
	}
}
