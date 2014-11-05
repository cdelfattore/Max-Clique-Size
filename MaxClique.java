/*	Project 6 - Genetic Alogorithm and Wisdom of the crowd's approach for solving maximum clique size
	Name: Chris Del Fattore
	Email: crdelf01@louisville.edu
	Description:

*/
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class MaxClique {
	public static Map<Integer,Point> points; //map of points
	public static Map<Integer,List<Integer>> edgeMap;

	public static void main(String[] args) throws IOException {
		//edges in the undirected graph
		List<String> graphEdgesList = Arrays.asList("1-2","1-3","1-5","2-3","2-4","2-6","3-4","3-5","3-6","5-6","6-1");

		//Store the edges in a map to represent all of the edges of a node
		edgeMap = new HashMap<Integer,List<Integer>>();
		for(String s : graphEdgesList){
			//System.out.println(s);
			String[] edge = s.split("-");
			Integer pointA = Integer.valueOf(edge[0]);
			Integer pointB = Integer.valueOf(edge[1]);
			// System.out.println("PointA: " + pointA);
			// System.out.println("PointB: " + pointB);
			if(edgeMap.keySet().contains(pointA)){
				edgeMap.get(pointA).add(pointB);
			}
			else {
				List<Integer> tmp = new ArrayList<Integer>();
				tmp.add(pointB);
				edgeMap.put(pointA,tmp);
			}
			if(edgeMap.keySet().contains(pointB)){
				edgeMap.get(pointB).add(pointA);
			}
			else {
				List<Integer> tmp = new ArrayList<Integer>();
				tmp.add(pointA);
				edgeMap.put(pointB,tmp);
			}
		}

		for(Integer i : edgeMap.keySet()){
			System.out.println(i + " " + edgeMap.get(i));
		}

		/*for(String s : graphEdgesList){
			System.out.println(s);
		}*/

		//Get the points from the input file
		//The Below list is used to store the point information from the input file
		points = new HashMap<Integer,Point>();

		//Takes the filename as a parameter. File contains points and the x and y cooridnates.
		String filename = args[0];

		//BufferedReader used to read input from a file
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		//pattern is the regular expression used to parse throught the input file and find the point number and the point's x and y value.
		//The pattern will find all of the points in the file
		String pattern = "(?m)^\\d+\\s\\d+\\.\\d+\\s\\d+\\.\\d+";
		Pattern r = Pattern.compile(pattern);

		String value = null;

		//the below while loop with go through the file line by line and see if a match has been made with the regular expression.
		//If a match is made, the line is parsed, retrieving the piont name, x and y coordinate values
		//the points are saved in the points list.
		//We will need the points later on to display the graph in the jframe
		while((value = reader.readLine()) != null){
			Matcher m = r.matcher(value);
			if(m.find()) {
				//add the point to the List of points
				Point p = new Point(Integer.parseInt(value.split(" ")[0]), Double.parseDouble(value.split(" ")[1]), Double.parseDouble(value.split(" ")[2]));
				points.put(p.name,p);
			}
		}

		/*for(Integer p : points.keySet()){
			System.out.println(points.get(p).name + " " + points.get(p).x + " " + points.get(p).y);	
		}*/

		//More concerned with the edges
		//Create a gentic algoritm to generate random sets of edges, which is basically a subgraph
		
		//need to retrieve a random amount of edges
		//int max = points.size(); //normally will be this 
		int max = 6; //use this for initally testing
		int min = 2;
		int numPoints = randomNum(min,max);
		System.out.println("Number of points: " + numPoints);
		Integer[] randPoints = new Integer[numPoints];
		//grab numEdges amount of points from points
		for(int i = 0; i < numPoints; i++){
			randPoints[i] = randomNum(min,max);
			System.out.println(randPoints[i]);
		}
		System.out.println();
		System.out.println("Set of points");
		//convert the array to a set to remove duplicat values
		Set<Integer> mySet = new HashSet<Integer>(Arrays.asList(randPoints));
		for(Integer i : mySet){
			System.out.println(i);
		}

		//create the random subgraph

	}

	static int randomNum(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
}

//Object used to represent a single point
//Point Stores the Name, X and Y Value
//with methods to retrieve the name, x and y value
//and a method to set the name.
//Turns out there is a java class called point
class Point {
	int name;
	double x, y;
	//constructor
	Point(int name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	//needed when converting a number to a letter and vise versa
	void setName(int a) {
		this.name = a;
	}
}

//Create a object that will store the random edges
//This object will calculate the clique of the subGraph
class SubGraph {
	ArrayList<String> edges;

	SubGraph(ArrayList<String> ranEdges){
		edges = new ArrayList<String>(ranEdges);
	}

	//function to calculate the maximum clique of a subgraph
	void CalcMaxClique(){

	}

	/*void addEdge(String edge){
		edges.add(edge);
	}
	void removeEdge(String edge){
		edges.remove(edge);
	}*/
}