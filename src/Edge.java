public class Edge {
	private Node from;
	private Node to;
	private int cost;

	public Edge(Node from, Node to, int cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}

	public int getCost() {
		return this.cost;
	}

	public Node getFrom() {
		return this.from;
	}

	public Node getTo() {
		return this.to;
	}

	public boolean equals(Object e) {
		if (e == null)
			return false;
		if (e == this)
			return true;
		if (!(e instanceof Edge))
			return false;

		Edge edge = (Edge) e;
		//since our graph is undirected, we don't particularly care how it matches up
		return (edge.getFrom().equals(this.from) && edge.getTo().equals(this.to))
 				|| (edge.getTo().equals(this.from) && edge.getFrom().equals(this.to));
	}

	public int hashCode() {
		return this.getFrom().getName().hashCode() + this.getTo().getName().hashCode();
	}
}
