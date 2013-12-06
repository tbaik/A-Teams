import java.util.Random;

public class Main {
	public static void main(String[] args) throws Exception {
		// l represents how many shared memory objects we have
		int l = 10;
		// j represents how many solutions each memory can have
		int j = 100;
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
				
				//then we write it into the memory index
				sharedMem.getMemoryArray()[i] = null;
			}
		}
		
		Solution bestSol = new Solution(null);
		bestSol.setTotalCost(Integer.MAX_VALUE);
		long timeToRunUntil = System.currentTimeMillis() + secondsToRun * 1000;
		while(System.currentTimeMillis() < timeToRunUntil){
			//get a random improvement heuristic
			
			
			int memoryIndex = new Random(l).nextInt();
			
			Solution solToImprove = sharedMem.getMemoryArray()[memoryIndex].getRandomSolution();
			
			//run our heuristic and get a new solution
			Solution improvedSol = null;
			//destroy a random by replacing our new solution in here.
			sharedMem.getMemoryArray()[memoryIndex].getSolutions().set(0, null);
			
			if(improvedSol != null && improvedSol.getTotalCost() < bestSol.getTotalCost()){
				bestSol = improvedSol;
			}
		}
	}
}
