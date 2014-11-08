/*	Project 6 - Genetic Alogorithm and Wisdom of the crowd's approach for solving maximum clique size
	Name: Chris Del Fattore
	Email: crdelf01@louisville.edu
	Description:

*/
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class MaxClique {
	//public static Map<Integer,Point> points; //map of points
	public static ArrayList<Integer> points; //map of points
	public static HashMap<Integer,ArrayList<Integer>> edgeMap;

	public static void main(String[] args) throws IOException {
		//Takes the filename as a parameter. File contains points and the x and y cooridnates.
		String filename = args[0];

		//BufferedReader used to read input from a file
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-16") );
		String line = null;
		
		//initalize the points arraylist and the hash map that will contain the edges
		points = new ArrayList<Integer>();
		edgeMap = new HashMap<Integer,ArrayList<Integer>>();
		while((line = reader.readLine()) != null){
			//System.out.println(line);
			String[] pointEdges = line.split("-");
			Integer point = Integer.valueOf(pointEdges[0]);
			points.add(point);

			ArrayList<Integer> edges = new ArrayList<Integer>();
			for(String i : pointEdges[1].split(",")){
				edges.add(Integer.valueOf(i));
			}
			edgeMap.put(point,edges);
		}

		/*for(Integer i : points){
			//System.out.println(i + " " + edgeMap.get(i));
			System.out.println(i);
		}*/

		/*System.out.println("Max clique of all points");
		SubGraph initSubGraph = new SubGraph(points);
		System.out.println(initSubGraph.points);
		System.out.println(initSubGraph.maxCliqueArray);
		System.out.println(initSubGraph.maxCliqueSize);
		System.out.println();*/
		
		//Genetic algorithm
		//first create the inital population
		int popSize = 24;
		ArrayList<SubGraph> popArray = new ArrayList<SubGraph>();
		for(int i = 0; i < popSize; i++){
			SubGraph sub = new SubGraph();
			popArray.add(sub);
		}
		//System.out.println();
		popArray = sortArraySubGraph(popArray,popSize);
		
		System.out.println("Init Pop Array");
		for(SubGraph s : popArray){
			System.out.println(s.maxCliqueSize + " " + s.points);
		}
		System.out.println();
		int generations = 10;
		int innerLoopIterations = popArray.size() / 2;
		for(int i = 0; i < generations; i++){
			for(int j = 0; j < innerLoopIterations; j++){
				//take the first two subGraphs and combine them, then the next two for the first half of popsize
				ArrayList<Integer> child = createChildPath(popArray.get(j).maxCliqueArray, popArray.get(j+1).maxCliqueArray);
				popArray.add(new SubGraph(child));
				popArray = sortArraySubGraph(popArray,popSize);

				

			}
			System.out.println("popArray");
			for(SubGraph s : popArray){
				System.out.println(s.maxCliqueSize + " " + s.points);
			}
			System.out.println();
		}
	}

	//create a child path
	public static ArrayList<Integer> createChildPath(ArrayList<Integer> pOne, ArrayList<Integer> pTwo){
		ArrayList<Integer> childPath = new ArrayList<Integer>();
		int smallestArraySize = pOne.size() > pTwo.size() ? pTwo.size() : pOne.size();
		//System.out.println(smallestArraySize);
		// int ranCrossOver = (int)(smallestArraySize * 0.25);
		// int crossOverIndex = randomNum(ranCrossOver,smallestArraySize);

		/*System.out.println(ranCrossOver);
		System.out.println(crossOverIndex);*/
		for(int i = 0; i < smallestArraySize;i++){
			if(!childPath.contains( pOne.get(i) )){
				childPath.add(pOne.get(i));	
			}
			if(!childPath.contains(pTwo.get(pTwo.size() - 1 - i))){
				childPath.add(pTwo.get(pTwo.size() - 1 - i));	
			}			
		}

		/*System.out.println("pOne " + pOne);
		System.out.println("pTwo " + pTwo);
		System.out.println("Child " + childPath);
		System.out.println();*/

		return childPath;
	}

	//sorting function that will simiulate the fitness function of a genetic algorithm
	//Not any particualer sorting algorithm, its efficency is O(n^2), but stable.
	public static ArrayList<SubGraph> sortArraySubGraph(ArrayList<SubGraph> subGraphArray, int popSize){
		ArrayList<SubGraph> retSubGraphArray = new ArrayList<SubGraph>();
		for(SubGraph s : subGraphArray){
			//System.out.println(s.maxCliqueArray.size());
			if(retSubGraphArray.size() == 0){
				retSubGraphArray.add(s);
			}
			else if(retSubGraphArray.size() == 1){
				if(retSubGraphArray.get(0).maxCliqueSize > s.maxCliqueSize){
					retSubGraphArray.add(s);
				}
				else {
					retSubGraphArray.add(0,s);
				}
			}
			else {
				for(int i = 0; i < retSubGraphArray.size(); i++){
					if( i == 0 && s.maxCliqueSize >= retSubGraphArray.get(i).maxCliqueSize ){
						retSubGraphArray.add(0,s);
						break;
					}
					else if(i == retSubGraphArray.size()-1 && s.maxCliqueSize < retSubGraphArray.get(i).maxCliqueSize){
						retSubGraphArray.add(retSubGraphArray.size(),s);
						break;
					}
					else if(s.maxCliqueSize < retSubGraphArray.get(i).maxCliqueSize && s.maxCliqueSize >= retSubGraphArray.get(i+1).maxCliqueSize){
						retSubGraphArray.add(i+1,s);
						break;
					}
				}
			}
			/*for(SubGraph b : retSubGraphArray){
				System.out.print(b.maxCliqueSize + " ");
			}
			System.out.println();*/
		}
		if(retSubGraphArray.size() >= popSize){
			ArrayList<SubGraph> subList = new ArrayList<SubGraph>();
			subList.addAll(retSubGraphArray.subList(0,23));
			return subList;
		}
		else {
			return retSubGraphArray;	
		}		
	}

	public static HashSet<Integer> randomSet(){
		int max = points.size(); //normally will be this 
		int min = 1;
		int numPoints = randomNum(min,max);
		//System.out.println("Number of points: " + numPoints);
		Integer[] randPoints = new Integer[numPoints];
		//grab numEdges amount of points from points
		for(int i = 0; i < numPoints; i++){
			randPoints[i] = randomNum(min,max);
		}
		//System.out.println();
		//System.out.println("Set of points");
		//convert the array to a set to remove duplicat values
		return new HashSet<Integer>(Arrays.asList(randPoints));
	}

	public static int randomNum(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}

	//This is a correction function for finding the intersection
	public static ArrayList<Integer> intersection(Integer a, Integer b){
		ArrayList<Integer> inter = new ArrayList<Integer>();
		// System.out.println("a " + a);
		// System.out.println("b " + b);
		//inter.add(a);
		//inter.add(b);
		for(Integer j : edgeMap.get(a)){
			if(j != a && j != b){
				for(Integer h : edgeMap.get(b)){
					if(j == h){
						System.out.println("Is " + j + " = " + h);
						inter.add(j);
					}
				}
			}
		}
		return inter;
	}

	//intersection between a node and a list
	public static ArrayList<Integer> intersection(Integer node, ArrayList<Integer> aList){
		ArrayList<Integer> inter = new ArrayList<Integer>();
		// System.out.println("node " + node);
		// System.out.println("aList " + aList);
		for(Integer j : aList){
			if(MaxClique.edgeMap.get(node).contains(j) || j == node){
				inter.add(j);
			}
			else {
				inter.remove(j);
			}
		}
		return inter;
	}

	//intersection between a list and a list
	public static ArrayList<Integer> intersection(ArrayList<Integer> aList, ArrayList<Integer> bList){
		ArrayList<Integer> inter = new ArrayList<Integer>();
		// System.out.println("aList " + aList);
		// System.out.println("bList " + bList);
		for(Integer j : aList){
			for(Integer k : bList){
				//System.out.println(j + " " + k);
				if(j == k){
					inter.add(k);
				}
			}
		}
		//System.out.println("inter " + inter);
		return inter;
	}

	public static ArrayList<Integer> union(ArrayList<Integer> a, Integer b){
		ArrayList<Integer> union = new ArrayList<Integer>();
		union.add(b);
		for(Integer i : a){
			if(b != i){
				union.add(i);
			}
		}
		return union;
	}

	public static ArrayList<Integer> setDifference(ArrayList<Integer> a, Integer b){
		ArrayList<Integer> diffArray = new ArrayList<Integer>();
		for(Integer i : a){
			if(b != i){
				diffArray.add(i);
			}
		}
		return diffArray;
	}

	public static ArrayList<Integer> bronKerbosch(ArrayList<Integer> r, ArrayList<Integer> p, ArrayList<Integer> x, ArrayList<Integer> maxCliqueArray){
		/*System.out.println("r " + r);
		System.out.println("p " + p);
		System.out.println("x " + x);*/
		
		if(p.size() == 0 && x.size() == 0){
			//System.out.println("return " + r);
			if(r.size() >= maxCliqueArray.size()){
				maxCliqueArray.clear();
				maxCliqueArray.addAll(r);
			}
		}
		else {
			for(Integer v : p){
				bronKerbosch(union(r,v), intersection(p,edgeMap.get(v)), intersection(x,edgeMap.get(v)), maxCliqueArray);
				p = setDifference(p,v);
				x = union(x,v);
			}	
		}
		return r;
	}

}
//Create a object that will store the random edges
//This object will calculate the clique of the subGraph
class SubGraph {
	ArrayList<Integer> points;
	int maxCliqueSize;
	ArrayList<Integer> maxCliqueArray;

	SubGraph(ArrayList<Integer> ranEdges){
		points = new ArrayList<Integer>();
		for(Integer i : ranEdges){
			points.add(i);
		}
		CalcMaxClique();
	}

	SubGraph(Set<Integer> ranEdges){
		points = new ArrayList<Integer>();

		for(Integer i : ranEdges){
			points.add(i);
		}
		CalcMaxClique();	
	}

	SubGraph(){
		points = new ArrayList<Integer>(MaxClique.randomSet());
		CalcMaxClique();
	}

	//function to calculate the maximum clique of a subgraph
	void CalcMaxClique(){
		maxCliqueArray = new ArrayList<Integer>();

		if(points.size() == 1){
			maxCliqueArray.addAll(points);
			maxCliqueSize = maxCliqueArray.size();
		}
		else if(points.size() == 2){
			//check if there is a edge between them
			if( MaxClique.edgeMap.get( points.get(0) ).contains( points.get(1) ) ){
				maxCliqueArray.addAll(points);
				maxCliqueSize = maxCliqueArray.size();

			}				
		}
		else {
			ArrayList<Integer> tmpMaxArray = new ArrayList<Integer>();
			ArrayList<Integer> a = new ArrayList<Integer>();
			ArrayList<Integer> b = new ArrayList<Integer>();
			MaxClique.bronKerbosch(a, points, b, maxCliqueArray );
			Collections.sort(maxCliqueArray);
			maxCliqueSize = maxCliqueArray.size();
		}
	}
}