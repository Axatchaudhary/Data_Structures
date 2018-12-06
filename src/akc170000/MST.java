/**
 * Long Project 5: Implementation of Minimum Spanning Tree using Prim1, Prim2, Prim3 and Kruskal's algorithm
 * @author Axat Chaudhari (akc170000)
 * @author Jaiminee Kataria (jxk172330)
 * @author Param Parikh (psp170230)
 * @author Tej Patel (txp172630)
 */
package akc170000;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import rbk.Graph.Timer;

import akc170000.BinaryHeap.Index;
import akc170000.BinaryHeap.IndexedHeap;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * Class to computer MST on graph G.
 * After running MST algorithm, MST is stored in list 'mst' corresponding predecessors are stored in 'parent'
 * Weight is MST is stored in 'wmst'
 */
public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm; // which algorithm used to get MST
    public long wmst; // total weight of MST
    List<Edge> mst; // list containing edges in MST

    MST(Graph g) {
        super(g, new MSTVertex((Vertex) null));
    }

    /**
     * MSTVertex to store additional properties for vertices of G for computing MST
     */
    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
        boolean seen; // when v.seen = true: edge e (u,v), where v.d = w(e) is added in MST
        Vertex parent; // predecessor vertex in MST. When edge e (u, v) is added to MST, v.parent = u and v.d = w(e)
        int index; // index of vertex stored in priority queue
        int d; // weight of edge (u,v) that belongs to MST. v.d = w(e)
        Vertex vertex; // corresponding graph vertex
        int rank; // rank of vertex in UNION/FIND
        MSTVertex root; // root of vertex in UNION/FIND
        MSTVertex(Vertex u) {
            seen = false;
            parent = null;
            d = Integer.MAX_VALUE;
            vertex = u;
            root = this; // vertex itself is its root node in the beginning
            rank = 0;
        }

        MSTVertex(MSTVertex u) {  // for prim2
            vertex = u.vertex;
        }

        /**
         * duplicate the MSTVeretx u
         * @param u vertex to be copied
         * @return new duplicated vertex
         */
        public MSTVertex make(Vertex u) { return new MSTVertex(u); }

        /**
         * set the position of vertex in IndexedPriorityQueue
         * @param index index of vertex in IndexedPriorityQueue
         */
        public void putIndex(int index) { this.index = index; }

        /**
         * get the index where vertex is stored in IndexedPriorityQueue
         * @return index of vertex
         */
        public int getIndex() { return index; }

        /**
         * used in priority queue to compare weight of two edges e(u,v), e'(u', v') where v.d = w(e) and v'.d = w(e')
         * @param other edge e'
         * @return 1 if w(e) > w(e'), 0 if w(e) = w(e'), -1 if w(e) < w(e')
         */
        public int compareTo(MSTVertex other) {
            if(other == null || this.d > other.d) return 1;
            else if(this.d < other.d) return -1;
            else return 0;
        }

        /**
         * find the root of vertex. point current vertex to root for immediate access in future
         * @return root of vertex
         */
        public MSTVertex find(){
            // recursive call while vertex's root is itself
            if(!vertex.equals(root.vertex)){
                root = root.find(); // set direct pointer to its root
            }
            return root;
        }

        /**
         * union vertex 'rv' with current vertex
         * @param rv vertex to union current vertex with
         */
        public void union(MSTVertex rv){
            // set root of smaller tree as a child of root with bigger tree.
            // use rank to determine which tree is bigger
            if (rank > rv.rank){
                rv.root = this;
            }else if(rank < rv.rank){
                root = rv;
            }else{ // if both tree has same rank, any one of two root vertices can become new root
                rank++;
                rv.root = this;
            }
        }
    }

    /**
     * Kruskal's algorithm:
     * - Sort edges is ascending order of weights
     * - Add every edge in MST until all vertices in G are covered
     * @return total weight of MST
     */
    public long kruskal() {
        // initialization done in constructor
        algorithm = "Kruskal";
        Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        Arrays.sort(edgeArray);
        for (Edge e : edgeArray){ // for each edge e (u,v) in G
            MSTVertex ru = get(e.fromVertex()).find(); // root of u
            MSTVertex rv = get(e.toVertex()).find(); // root of v
            if(!ru.vertex.equals(rv.vertex)){ // if both are not in same component
                mst.add(e); // add e to mst
                ru.union(rv); // combine two components
                wmst += e.getWeight();
            }
        }
        // at the end #components in UNION/FIND = 1
        return wmst;
    }

    /**
     * Prim3 (modification of Prim2)
     * using Indexed queue so there are no duplicate vertices in priority queue
     * @param s source vertex
     * @return total weight of MST
     */
    public long prim3(Vertex s) {
        algorithm = "indexed heaps";
        //initialization
        initialize();
        get(s).d = 0;
        mst = new LinkedList<>();
        IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
        for (Vertex u : g){
            q.add(get(u));
        }

        while (!q.isEmpty()){
            MSTVertex u = q.poll(); //get vertex u representing edge e(v,u) with min weight (v.d)
            u.seen = true; // this vertex is processed
            wmst += u.d; // update MST weight

            for (Edge e : g.incident(u.vertex)){ // every edge e(u,v) outgoing of u
                Vertex v = e.otherEnd(u.vertex);
                //discard if v already processed or v already represented edge with less weight than e
                if(!get(v).seen && e.getWeight() < get(v).d){
                    // update weight and predecessor
                    get(v).d = e.getWeight();
                    get(v).parent = u.vertex;
                    //update priority queue
                    q.decreaseKey(get(v));
                }
            }
        }
        return wmst;
    }

    /**
     * Prim2 (modification of Prim1)
     * - maintain priority queue of vertex.
     * - Store only the end vertex v representing edge e(u, v) in priority queue
     *      - v.d = w(e)
     *      - v.d is acts as a key in priority queue (see MSTVertex's compareTo(other MSTVertex))
     *      - There may exist duplicate vertices representing different edges.
     *          - Only the vertex with min weight is considered in future (this issue is solved in Prim3)
     * @param s source vertex
     * @return total weight of MST
     */
    public long prim2(Vertex s) {
        algorithm = "PriorityQueue<Vertex>";
        //initialization
        initialize();
        get(s).d = 0;
        mst = new LinkedList<>();
        PriorityQueue<MSTVertex> q = new PriorityQueue<>(); // priority queue to get remaining min weight edge to add to mst
        q.add(get(s));

        while (!q.isEmpty()){
            MSTVertex u = q.remove(); // get edge with min weight. vertex u represents edge e(v,u)
            if(!u.seen){
                u.seen = true;
                wmst+=u.d; // w(e)

                for (Edge e : g.incident(u.vertex)){ // edges (u,v) outgoing of u
                    Vertex v = e.otherEnd(u.vertex); // other end of edge (u,v)

                    // discard if v is already seen or v already represented edge e' with lesser weight then e
                    if(!get(v).seen && e.getWeight() < get(v).d){
                        MSTVertex dupV = new MSTVertex(get(v)); // duplicate the old vertex
                        dupV.d = e.getWeight(); // set new lesser weight of edge
                        dupV.parent = u.vertex; // now new v represents new edge
                        get(v).seen = true; // make old vertex seen so that it is never used here again
                        put(dupV.vertex, dupV); // replace old vertex with new for future access
                        q.add(dupV); // add to priority queue
                    }
                }

            }
        }

        return wmst;
    }

    /**
     * Prim1:
     * - maintain priority queue of edges of G
     * - remove smallest weight edge from queue and add it to MST if appropriate
     * @param s
     * @return
     */
    public long prim1(Vertex s) {
        algorithm = "PriorityQueue<Edge>";
        //initialization
        initialize();
        get(s).seen = true;
        mst = new LinkedList<>();
        PriorityQueue<Edge> q = new PriorityQueue<>();

        //add every edge incident to source to queue
        for (Edge e: g.incident(s)){
            q.add(e);
        }

        //while every edge is not processed
        while (!q.isEmpty()){
            Edge e = q.remove(); // extract min weight edge e(u,v) with u.seen = true
            Vertex v = get(e.fromVertex()).seen ? e.toVertex() : e.fromVertex(); // get v from e(u,v)
            if(get(v).seen) continue; // discard if other end is also processed

            get(v).seen = true; // this vertex is processed now
            get(v).parent = e.otherEnd(v); // set its predecessor as u
            wmst += e.getWeight(); // update weight

            for (Edge e1 : g.incident(v)){ // for every outgoing edge e1(v, u)
                if (!get(e1.otherEnd(v)).seen){ // if u is not processed, meaning edge (v,u) is not processed, add to queue
                    q.add(e1);
                }
            }
        }
        return wmst;
    }

    /**
     * Initialization process for all prim algorithms
     */
    void initialize(){
        for (Vertex u: g){
            get(u).seen = false;
            get(u).parent = null;
            get(u).d = Integer.MAX_VALUE;
        }
        wmst = 0;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
        MST m = new MST(g);
        switch(choice) {
            case 0:
                m.kruskal();
                break;
            case 1:
                m.prim1(s);
                break;
            case 2:
                m.prim2(s);
                break;
            default:
                m.prim3(s);
                break;
        }
        return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        int choice = 1;  // Kruskal
        String string="9 14	1 2 4	1 3 8	2 3 11	2 7 8	3 6 7	3 4 1	4 6 6	7 6 2	7 8 7	7 5 4	4 5 2	8 5 14	8 9 9	5 9 10";
        if (args.length == 0 || args[0].equals("-")) {
            in = new Scanner(System.in);
        } else {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        }

        if (args.length > 1) { choice = Integer.parseInt(args[1]); }
//        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);

        Timer timer = new Timer();
        MST m = mst(g, s, choice);
        System.out.println(m.algorithm + "\n" + m.wmst);
        System.out.println(timer.end());
    }
}