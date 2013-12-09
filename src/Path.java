import java.util.ArrayList;


public class Path {
	//order represents path taken
	private ArrayList<Edge> pathEdges = new ArrayList<Edge>();
	
	public Path(ArrayList<Edge> pathEdges){
		this.pathEdges = pathEdges;
	}
	
	public ArrayList<Edge> getPaths(){
		return this.pathEdges;
	}
}
