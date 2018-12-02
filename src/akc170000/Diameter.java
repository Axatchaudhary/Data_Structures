/**
 * Short Project 12: Find the diameter of tree using BFS
 * @author  Axat Chaudhari akc170000
 * @author  Garima Kukreja gxk170630
 */
package akc170000;

import rbk.BFSOO;
import rbk.Graph;

import java.io.File;
import java.util.Scanner;

public class Diameter {

    /**
     * Find the vertex with max distance from source
     * @param b BFS class object which contains information about distance of every node from the source
     * @return farthest vertex from source
     */
    static Graph.Vertex findMax(BFSOO b){
        Graph.Vertex farthest = null;
        int max = -1; // distance will never be negative
        for (Graph.Vertex v: b.g){
            int dis = b.getDistance(v);
            if(dis != b.INFINITY && dis > max){ // keep track of farthest vertex
                max = b.getDistance(v);
                farthest = v;
            }
        }
        return  farthest;
    }

    /**
     * Find the diameter of tree represented by undirected graph
     * @param g graph representing tree
     * @return diameter of tree
     */
    public static int diameter(Graph g){

        int src = 1; //pick a random source node TODO: this is supposed to be random and has to exist in graph g
        BFSOO b = BFSOO.breadthFirstSearch(g, src); // run BFS on that source node
        Graph.Vertex farthest = findMax(b); // find the farthest node from the source node. Call this max
        b.bfs(farthest); // run BFS on max
        farthest = findMax(b); // find farthest node from max
        return b.getDistance(farthest); // distance of farthest node from max
    }

    public static void main(String[] args) throws Exception{
        String string = "10 9   1 2 1   1 3 1   2 4 1   2 5 1   3 6 1   3 7 1   4 8 1   7 9 1   7 10 1 0";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        // Read graph from input
        Graph g = Graph.readGraph(in);
        int s = in.nextInt();

        int d = diameter(g);
        System.out.println("Diameter: "+d);
    }
}
