import java.util.*;

public class AdjListGraph implements Graph<Vertex>{
	private boolean isDirected;
	private ArrayList<Vertex> myVertices;
	
	public AdjListGraph(boolean directed){
		isDirected = directed;
		myVertices = new ArrayList<Vertex>();
	}
	
	public void addEdge(Vertex i, Vertex j) throws IllegalVertexException{
		if (myVertices.contains(i) && myVertices.contains(j)){
			i.addEdge(j);
			if (!isDirected){
				j.addEdge(i);
			}
		}
		else{
			throw new IllegalVertexException("Add edge failed.");
		}
	}
	
	public void addEdge(Vertex i, Vertex j, double length) throws IllegalVertexException{
		if (myVertices.contains(i) && myVertices.contains(j)){
			i.addEdge(j,length);
			if (!isDirected){
				j.addEdge(i,length);
			}
		}
		else{
			throw new IllegalVertexException("Add edge failed.");
		}
	}
	
	public Vertex addVertex(Vertex v){
		myVertices.add(v);
		return v;
	}
	
	public boolean edgeExists(Vertex i, Vertex j)throws IllegalVertexException{
		if (myVertices.contains(i)&& myVertices.contains(j)){
			return  i.hasEdge(j);
		}
		else{
			throw new IllegalVertexException("Edge Exists failed.");
		}
	}

	public ArrayList<Vertex> getVertices(){
		return myVertices;
	}
	
	public Collection<Vertex> getAdjacentVertices(Vertex v) throws IllegalVertexException{
		if (myVertices.contains(v)){
			return v.getAdjacentVertices();
		}
		else{
			throw new IllegalVertexException("get Iterator failed");
		}
	}
	
	public int inDegree(Vertex v) throws IllegalVertexException{
		if (!myVertices.contains(v)){
			throw new IllegalVertexException("in Degree failed.");
		}
		else{
			int inDegree = 0;
			for (Vertex i: myVertices){
				for(Vertex j: i.getAdjacentVertices()){
					if (j == v){
						inDegree++;
					}
				}
			}
			return inDegree;
		}
	}
	
	public int outDegree(Vertex v) throws IllegalVertexException{
		if (!myVertices.contains(v)){
			throw new IllegalVertexException("out degree failed");
		}
		else{
			return v.getAdjacentVertices().size();
		}
	}
	
	public boolean isDirected(){
		return isDirected;
	}
	
	public int numEdges(){
		int edges =0;
		for(Vertex v: myVertices){
			edges += outDegree(v);
		}
		if (!this.isDirected()){
			edges /= 2;
		}
		return edges;
	}
	
	public int numVertices(){
		return myVertices.size();
	}
	
	public void removeEdge(Vertex i, Vertex j) throws IllegalVertexException{
		if (myVertices.contains(i) && myVertices.contains(j) && i.hasEdge(j)){
			i.removeEdge(j);
			if (isDirected){
				j.removeEdge(i);
			}
		}
		else{
			throw new IllegalVertexException("Remove Edge Failed.");
		}
	}
	
	public void removeVertex(Vertex v) throws IllegalVertexException{
		if (myVertices.contains(v)){
			myVertices.remove(v);
			for (Vertex i: myVertices){
				if (i.hasEdge(v)) i.removeEdge(v);
			}
		}
		else{
			throw new IllegalVertexException("removeVertex failed.");
		}
	}
	
	public double getWeight(Vertex i, Vertex j) throws IllegalVertexException {
		if(myVertices.contains(i)){
			return i.getEdgeWeight(j);
		}
		else{
			throw new IllegalVertexException("getWeight failed.");
		}
	}
	
	public void print(){
		String direction = isDirected ? "directed" : "undirected";
		System.out.println("This is a "+direction+ " graph.");
		System.out.println("This graph has "+ numEdges()+" edges and "+numVertices()+ " vertices:");
		for(Vertex i: myVertices){
			System.out.println(i.getName()+" in degree: "+this.inDegree(i)+ " out degree: "+this.outDegree(i));
			System.out.print("\tEdges to: ");
			for (Vertex j: i.getAdjacentVertices()){
				System.out.print(j.getName()+":"+i.getEdgeWeight(j)+" ");
			}
			System.out.println();
		}
	}
}
