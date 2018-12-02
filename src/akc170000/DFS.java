/** Short Project 8: DFS and Topological Order
 *  @author Axat Chaudhari (akc170000)
 *  @author Shreeya Girish Degaonkar (sxd174830)
 */

package akc170000;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import rbk.Graph.Timer;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    LinkedList<Vertex> finishList; // list contains vertices in topological order
    int topNum;
    boolean isDAG; // is graph directed acyclic graph?
    int cno; // #conncted components in the graph
    int scc; // # strongly connected components
    public static class DFSVertex implements Factory {
        int cno; // connected component number
        int top; // number of vertex in topological order
        boolean seen;
        Vertex parent;

        int in; // inDegree of vertex
        public DFSVertex(Vertex u) {
            seen = false;
            parent = null;
            top = 0;
            cno = -1;
        }
        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        finishList = new LinkedList<>();
        topNum = g.size();
        isDAG = true;
        cno = 0;
        scc = 0;
    }

    /**
     * depth first search algorithm on graph g provided in constructor.
     * Is also assigns topological order to every vertex
     */
    public void dfs(){
        dfs(g);
    }

    /**
     * run depth first search in order of vertices given by order
     * @param order order of vertices in which dfs should run
     */
    private void dfs(Iterable<Vertex> order){
        isDAG = true; // initialization in case of independent call to dfs() on updated graph
        topNum = g.size();
        finishList = new LinkedList<>(); // Assign new Linkedlist so that old one can be used in Strongly connected components
        cno = 0;

        // Initialize
        for (Vertex u: order){
            get(u).seen = false;
            get(u).parent = null;
            get(u).top = 0;
        }

        for (Vertex u: order){
            if(!get(u).seen){
                cno++;
                dfsVisit(u);
            }
        }

    }

    /**
     * Travel vertex u in depth first order.
     * Pre-condition: u is visited by dfs and u is not seen
     * @param u vertex to start depth first travel
     */
    public void dfsVisit(Vertex u){
        get(u).seen = true;
        get(u).cno = cno;

        for (Edge e: g.incident(u)){
            Vertex v = e.otherEnd(u);
            if(!get(v).seen){
                get(v).parent = u;
                dfsVisit(v); // every other incedent has same connected component number
            }else if (get(v).top == 0){ // back edge detected (only for directed graph algorithms!)
                isDAG = false;
            }
        }
        get(u).top = topNum--;
        finishList.addFirst(u);
    }

    /**
     * return DFS instance on given graph
     * @param g graph to run depthfirst search or other algorithms like topological order / connected components
     * @return instance of DFS on graph g
     */
    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        d.dfs();
        return d;
    }

    /**
     * Find strongly connected components in graph g
     * @param g the graph whose strongly connectec component is to be found
     * @return DFS object containing information about strongly connected components
     */
    public static DFS stronglyConnectedComponents(Graph g) {
        DFS d = new DFS(g);
        d.stronglyConnectedComponents();
        return d;
    }

    /**
     * Find strongly connected components in graph
     * It is called from static method
     */
    private void stronglyConnectedComponents(){
        dfs(g);
        LinkedList<Vertex> topologicalOrder = finishList;
        g.reverseGraph();
        dfs(topologicalOrder);
        g.reverseGraph();
    }

    /**
     * Get number of strongly connected components
     * @return #Strongly connected components
     */
    public int scc(){
        return cno;
    }
    /**
     * Member function to find topological order
     * Pre-condition: graph is directed
     * @return List of vertices in topological order
     */
    public List<Vertex> topologicalOrder1() {
        return isDAG ? finishList : null;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.

    /**
     * find the number of connected components in graph
     * @return number of connected components
     */
    public int connectedComponents() {
        // after making the instance of DFS, call dfs(). That will assign component number to each vertex
        return cno;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    /**
     * @param u
     * @return component number of vertex u
     */
    public int cno(Vertex u) {
        return get(u).cno;
    }

    /** Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
     * @return list of vertices in topological order
     */
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = depthFirstSearch(g);
        return d.topologicalOrder1();
    }

    public List<Vertex> topologicalOrder2(){
        List<Vertex> list = new LinkedList<>(); // list containing elements in topological order
        LinkedList<Vertex> zeroq = new LinkedList<>(); // queue containing vertices with zero incoming edges
        // Initialize
        for (Vertex v: g){
            get(v).in = v.inDegree();
            if (get(v).in == 0) {
                zeroq.add(v);
            }
        }
        int count = 0;// topological order
        // keep removing vertices with zero incoming edges until none left
        while (!zeroq.isEmpty()){
            Vertex u = zeroq.removeFirst();
            get(u).top = ++count;
            list.add(u);
            // removing vertex and edges associated with it, virtually, with zero in degree
            for (Edge e: g.incident(u)){
                Vertex v = e.otherEnd(u);
                get(v).in-- ;
                if(get(v).in == 0){
                    zeroq.add(v);
                }
            }
        }
        // if count has not reached zero that means graph has cycles
       return g.size() == count ? list : null;
    }
    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder2();
    }

    public static void main(String[] args) throws Exception {
//        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
        String string = "11 17   1 11 1   2 3 1   2 7 1   3 10 1   4 1 1   4 9 1   5 4 1   5 8 1   5 7 1   6 3 1   7 8 1   8 2 1   9 11 1   10 6 1   11 6 1   11 3 1   11 4 1";
        String st = "8 12   1 5 1   2 1 1   5 2 1   3 2 1   4 3 1   3 4 1   6 5 1   7 6 1   6 7 1   7 3 1   8 7 1   8 8 1   8 4 1";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(st);

        // Read graph from input
        Graph g = Graph.readGraph(in,true);
        g.printGraph(false);

        DFS d = stronglyConnectedComponents(g);
        System.out.println("Number of Strongly connected components: " + d.scc() + "\nu\tcno");
        for(Vertex u: g) {
            System.out.println(u + "\t" + d.cno(u));
        }
        System.out.println("-----");
        for (Vertex u: d.finishList){
            System.out.println(u + "\t" + d.cno(u));
        }
    }
}