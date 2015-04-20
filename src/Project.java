import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;


public class Project {

	private SimpleGraph<Integer, DefaultEdge> G;
	private HashMap<Integer, Double> Centrality;

	Project() {
		G = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
		Centrality = new HashMap<Integer, Double>();
	}


	public void FullCentrality() {
		int numVert = G.vertexSet().size();
		for (int v1 = 0; v1 < numVert; v1++) {
			System.out.println(v1);
			double averagePath = 0;
			for (int v2 = 0; v2 < numVert; v2++) {
				//System.out.println(v1 + " : " + v2);
				if (v1 != v2) {
					DijkstraShortestPath<Integer,DefaultEdge> dijkstra = 
							new DijkstraShortestPath<Integer,DefaultEdge>(G, v1, v2);
					averagePath += dijkstra.getPathLength();
				}
			}
			Centrality.put(v1,  averagePath / (numVert - 1));
			System.out.println(v1 + " : " + Centrality.get(v1));

		}	
	}

	public void Sampling() {

	}

	public void ModifiedSampling() {

	}

	/***
	 * Gets the vertices of an edge
	 */
	private int[] getVertices(DefaultEdge edge) {
		int[] vertices = new int[2];
		String e = edge.toString();
		int v1 = Integer.parseInt(e.substring(1, e.indexOf(":") - 1));
		int v2 = Integer.parseInt(e.substring(e.indexOf(":") + 2, e.length() - 1));
		vertices[0] = v1;
		vertices[1] = v2;
		return vertices;
	}

	/***
	 * Method that reads a file and puts it into a graph
	 */
	public void readGraph(String filename) {
		BufferedReader br = null; 
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filename));
			String[] s = new String[2];
			while ((sCurrentLine = br.readLine()) != null) {
				s = sCurrentLine.split("\\s+");
				try {
					//cut size of facebook data down to 2000
					//run time goes from 50 hours to 5 hours
					if (Integer.parseInt(s[0])<2000 && Integer.parseInt(s[1])<2000) {
						G.addVertex(Integer.parseInt(s[0]));
						G.addVertex(Integer.parseInt(s[1]));
						G.addEdge(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
					}
				} catch (Exception e) {}
			}
		} catch (Exception e) {}
		finally {
			try {
				if (br != null)br.close();
			} catch (Exception e) {}
		}
	}

	public int getMedian() {
		Set<Integer> vert = G.vertexSet();
		LinkedList <Integer> list = new LinkedList<Integer>();
		for (Integer v : vert) {
			list.add(G.edgesOf(v).size());
		}
		return list.get(list.size()/2);
	}
	
	/***
	 * Writes the answers on a file
	 */
	public void writeFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
			for (Integer key:Centrality.keySet()) {
				out.write(key + " " + Centrality.get(key) + "\n");
				}
			out.close();
		} catch (IOException e) {}
	}

	public static void main(String[] args) {
		Project p = new Project();
		p.readGraph("facebook_combined.txt");
		ConnectivityInspector<Integer, DefaultEdge> c = 
				new ConnectivityInspector<Integer, DefaultEdge>(p.G);
		System.out.println(c.isGraphConnected());
		System.out.println(p.G.vertexSet().size());
		p.FullCentrality();
		p.writeFile();
	}


}
