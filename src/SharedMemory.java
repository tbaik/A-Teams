import java.util.Random;

//holds multiple "memory" objects that represnt solutions
public class SharedMemory {
	private Memory[] memoryArr;
	public SharedMemory(int size, int memoryCapacity){
		memoryArr = new Memory[size];
		for(int i = 0; i < size; i++)
			memoryArr[i] = new Memory(memoryCapacity);
	}
	
	public Memory[] getMemoryArray(){
		return memoryArr;
	}
}
