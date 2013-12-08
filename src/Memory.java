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
		// test to see if this solution is worse than worst solution,
		// if it is, just return
		
		// choose a random one to replace using roulette rule (higher score => more likely to be removed)
		
		// replace now
	}
}
