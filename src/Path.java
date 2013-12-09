import java.util.ArrayList;


public class Path {
	//order represents path taken
	private ArrayList<Edge> pathEdges = new ArrayList<Edge>();
	
	public Path(ArrayList<Edge> pathEdges){
		this.pathEdges = pathEdges;
	}
	
	public Path() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Edge> getPaths(){
		return this.pathEdges;
	}
	
	public void addEdge(Edge e){
		this.pathEdges.add(e);
	}
	
	public String toString(){
		String edges = "";
		for(Edge e : getPaths()){
			edges += e + " ** ";
		}
		return edges.trim().substring(0, edges.trim().length() - 2);
	}
}
