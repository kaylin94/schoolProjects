import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GraphMaker {
	protected int vertices;
	protected String[][] fileArray;
	protected DijkstraVertex[] vertexArray;
	protected AdjListGraph graph;

	public void readFile(String fileName){
		try{
			File file = new File(fileName);
			Scanner scan = new Scanner(file);
			String line = scan.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			String numVertices = tokenizer.nextToken();
			vertices = Integer.parseInt(numVertices);

			fileArray = new String[vertices+1][vertices+1];
			vertexArray = new DijkstraVertex[vertices];

			int i = 0;
			while(scan.hasNextLine()){
				line = scan.nextLine();
				readAndProcess(line, i);
				i++;
			}
			scan.close();
		}
		catch(IOException exception){
			System.out.println("Error processing file: "+exception);
		}

		//              for(int i = 0; i < fileArray.length; i++){
			//                      for(int j = 0; j < fileArray.length; j++) {
				//                              System.out.print(fileArray[i][j]);
				//                              System.out.print("   ");
				//                      }
			//                      System.out.print("\n");
			//              }

		createGraph();
	}

	public void readAndProcess(String inputLine, int i){
		StringTokenizer tokenizer = new StringTokenizer(inputLine);
		for(int j = 0;j< vertices+1 ;j++){
			if(i == 0){
				if(tokenizer.hasMoreTokens()){
					fileArray[i][j+1] = tokenizer.nextToken();
				}
			} else {
				if(tokenizer.hasMoreTokens()){
					fileArray[i][j] = tokenizer.nextToken();
				}
			}
		}
	}

	public void createGraph(){
		graph = new AdjListGraph(false);

		for(int i = 0; i < 1; i++){
			for(int j = 1; j < fileArray[0].length; j++){
				vertexArray[j-1] = new DijkstraVertex(fileArray[i][j]);
			}
		}

		for(int i = 0; i < vertexArray.length; i++) {
			graph.addVertex(vertexArray[i]);
		}

		for(int i = 1; i < fileArray.length; i++){
			for(int j = 1; j< fileArray[0].length;j++){
				//System.out.print(fileArray[i][j] + " ");
				if(Double.parseDouble(fileArray[i][j]) != 0) {
					//System.out.print("\n" + fileArray[i][0] + " and " + fileArray[0][j] + " ");
					graph.addEdge(vertexArray[i-1], vertexArray[j-1], Double.parseDouble(fileArray[i][j]));
				}
			}
		}
		graph.print();
	}
}