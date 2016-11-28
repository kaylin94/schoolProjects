import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.PriorityQueue;

public class DijkstraAlgorithm {
	GraphMaker gm;
	protected String source;
	protected String destination;
	protected DijkstraVertex sourceVertex;
	protected DijkstraVertex destinationVertex;
	protected DAGui gui;
	protected ArrayList<Vertex> vertices;
	
	public DijkstraAlgorithm(){
		gm = new GraphMaker();
		gm.readFile("JapanCities.txt");
		gui = new DAGui();
		vertices = gm.graph.getVertices();
	}
	
	public void getSource(){
		source = gui.promptForSourceVertex();
		Iterator<Vertex> iterator = vertices.iterator();
		while (iterator.hasNext()){
			DijkstraVertex thisVertex = (DijkstraVertex) iterator.next();
		     if(thisVertex.getName().equals(source)) {
		          sourceVertex = thisVertex;
		     }
		}
	}
	
	public void getDestination(){
		destination = gui.promptForDestinationVertex();
		Iterator<Vertex> iterator = vertices.iterator();
		while (iterator.hasNext()){
			DijkstraVertex thisVertex = (DijkstraVertex) iterator.next();
		     if(thisVertex.getName().equals(destination)) {
		          destinationVertex = thisVertex;
		     }
		}
	}
	
	public void dijkstras(AdjListGraph graph){
		/*
		 * find its adjacent vertices YES
		 * determine distances between each
		 * record the parent of the current best distance to those child nodes
		 * pick shortest distance
		 * get vertex that shortest edge led to
		 * perform again
		 */
		PriorityQueue<DijkstraVertex> adjacents = new PriorityQueue<DijkstraVertex>(sourceVertex.getAdjacentVertices().size());
		Iterator<Vertex> iterator = sourceVertex.getAdjacentVertices().iterator();
		while (iterator.hasNext()){
			DijkstraVertex thisVertex = (DijkstraVertex) iterator.next();
			DijkstraVertex thisDV = new DijkstraVertex(thisVertex.getName());
			thisDV.setParent(sourceVertex);
			if(thisDV.compareTo((DijkstraVertex) sourceVertex) == -1){
				thisDV.setDistance(graph.getWeight(thisDV,sourceVertex));
			}
			adjacents.add(thisDV);
		}
		System.out.print(adjacents.toString());
		
	}
	
	public void run(){
		getSource();
		getDestination();
		dijkstras(gm.graph);
	}

}
