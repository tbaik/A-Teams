import java.util.Random;

//holds multiple "memory" objects that represnt solutions
public class SharedMemory {
	private Memory[] memoryArr;
	public SharedMemory(int size, int memoryCapacity){
		memoryArr = new Memory[size];
		for(int i = 0; i < size; i++)
			memoryArr[i] = new Memory(memoryCapacity);
	}
	//gets us a random Memory object to work in
	public Memory getRandomMemory(){
		return memoryArr[new Random(memoryArr.length).nextInt()];
	}
	
	public Memory[] getMemoryArray(){
		return memoryArr;
	}
}
