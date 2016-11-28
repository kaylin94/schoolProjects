
public class DijkstraVertex extends Vertex implements Comparable<DijkstraVertex>{
	protected double distance;
	protected Vertex parent;
	
	public DijkstraVertex(String name) {
		super(name);
		distance = Integer.MAX_VALUE;
		parent = null;
	}

	@Override
	public int compareTo(DijkstraVertex v) {		
		if(this.distance > v.distance){
			return 1;
		}
		else if(this.distance < v.distance){
			return -1;
		}
		else{
			return 0;
		}
	}

	public void setDistance(double aDistance){
		distance = aDistance;
	}
	
	public void setParent(DijkstraVertex aParent){
		parent = aParent;
	}

}
