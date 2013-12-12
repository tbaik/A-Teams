import java.util.ArrayList;
import java.util.Random;


public class Memory {
	private ArrayList<Solution> solutions;
	private int memCapacity;
	
	public Memory(int memSize){
		this.solutions = new ArrayList<Solution>();
		this.memCapacity = memSize;
	}
	
	public ArrayList<Solution> getSolutions(){
		return this.solutions;
	}
	
	public int getMemCapacity(){
		return this.memCapacity;
	}
	
	public Solution getRandomSolution(){
		return solutions.get(new Random().nextInt(memCapacity));
	}

	public void write(Solution improvedSol) {
		int nSolCost = improvedSol.getTotalCost();
		int maxSolCost = Integer.MIN_VALUE;
		for(Solution e : solutions){
			if(e.getTotalCost() > maxSolCost)
				maxSolCost = e.getTotalCost();
		}
		
		// test to see if this solution is worse than worst solution,
		// if it is, just return without writing
		if(nSolCost >= maxSolCost){
			return;
		}
		
		// choose a random one to replace using roulette rule (higher score => more likely to be removed)
		ArrayList<Short> indexes = new ArrayList<Short>();
		for(int i = 0; i < solutions.size(); i++){
			for(int j = 0; j < solutions.get(i).getTotalCost(); j++)
				indexes.add((short)i);
		}
		if(indexes.size() == 0)
			System.out.println("fuck");
		// replace now
		solutions.set(indexes.get(new Random().nextInt(indexes.size())), improvedSol);
	}
}
