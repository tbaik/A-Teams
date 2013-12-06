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
		return solutions.get(new Random(memCapacity).nextInt());
	}
}
