
public class Node {
	private String name;
	
	public Node(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean equals(Object e){
        if (e == null)
            return false;
        if (e == this)
            return true;
        if (!(e instanceof Node))
            return false;

        Node node = (Node) e;
        return node.getName().equals(this.name);
	}
}
