/*
Class used to genertate random numbers and points.
Takes one command line paramter, which represents the number of nodes in the graph.
The amount of edges will be random
To compile: javac GenerateGraphs.java
to run 		java GenerateGraphs [Number of nodes in graph] > fileToSaveGraph.txt
be sure to specify a file for the output to print to other wise the output will print to the console.
*/
import java.io.*;
import java.util.*;
public class GenerateGraphs {
	public static void main(String[] args){
		HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		Integer input = Integer.valueOf(args[0]);
		for(int i = 1; i <= input; i++ ){
			int max = input; 
			int min = 1;
			int numPoints = randomNum(min,max);

			for(int j = 0; j < numPoints; j++){
				int k = randomNum(min,max);
				if(i==k)continue;
				if(!map.keySet().contains(i)){
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(k);
					map.put(i,tmp);
				}
				else {
					if(!map.get(i).contains(k)){
						map.get(i).add(k);	
					}					
				}
				if(!map.keySet().contains(k)){
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(i);
					map.put(k,tmp);
				}
				else {
					if(!map.get(k).contains(i)){
						map.get(k).add(i);	
					}					
				}
			}
		}

		//string buffer for the final output of the file
		StringBuffer sBuffer = new StringBuffer(); 
		for(Integer i : map.keySet()){
			Collections.sort(map.get(i));
			//System.out.print(i+"-");
			sBuffer.append(i+"-");
			for(Integer j = 0; j < map.get(i).size(); j++) {
				if(j == map.get(i).size() - 1 ){
					sBuffer.append(map.get(i).get(j));
				}
				else {
					sBuffer.append(map.get(i).get(j)+",");
				}
			}
			sBuffer.append("\n");
		}
		System.out.print(sBuffer);

	}
	static int randomNum(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
}