import java.util.List;


public class Solution {
	private List<Edge> edges;
	private int totalCost;
	
	public Solution(List<Edge> edges){
		this.edges = edges;
		for(Edge e : edges)
			totalCost += e.getCost();
	}
	
	public int getTotalCost(){
		return totalCost;
	}
	
	public List<Edge> getEdges(){
		return edges;
	}

	public void setTotalCost(int tCost){
		this.totalCost = tCost;
	}
}
