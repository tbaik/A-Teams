import java.util.ArrayList;


public class Path {
	private Node start;
	private Node end;
	
	//order represents path taken
	private ArrayList<Edge> pathEdges = new ArrayList<Edge>();
	
	public Path(ArrayList<Edge> pathEdges, Node start, Node end){
		this.start = start;
		this.end = end;
		this.pathEdges = pathEdges;
	}
	
	public Path(Node start, Node end) {
		this.start = start;
		this.end = end;
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
	
	public Node getStart(){
		return this.start;
	}
	
	public Node getEnd(){
		return this.end;
	}
}
