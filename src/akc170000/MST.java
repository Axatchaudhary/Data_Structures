
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

public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm;
    public long wmst;
    List<Edge> mst;

    MST(Graph g) {
        super(g, new MSTVertex((Vertex) null));
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
        boolean seen; // vertex is visited or not?
        Vertex parent; // parent of vertex in MST
        int index; // index of vertex in priority queue
        int d; // weight of the smallest edge that connects v to some u in G
        Vertex vertex; // corresponding vertex
        int rank; // rank of node in UNION/FIND data structure
        MSTVertex root; // root of vertex in UNION/FIND
        MSTVertex(Vertex u) {
            seen = false;
            parent = null;
            d = Integer.MAX_VALUE;
            vertex = u;
            root = this;
            rank = 0;
        }

        MSTVertex(MSTVertex u) {  // for prim2
            vertex = u.vertex;
        }

        public MSTVertex make(Vertex u) { return new MSTVertex(u); }

        public void putIndex(int index) { this.index = index; }

        public int getIndex() { return index; }

        public int compareTo(MSTVertex other) {
            if(other == null || this.d > other.d) return 1;
            else if(this.d < other.d) return -1;
            else return 0;
        }

        public MSTVertex find(){
            if(!vertex.equals(root.vertex)){
                root = root.find();
            }
            return root;
        }

        public void union(MSTVertex rv){
            if (rank > rv.rank){
                rv.root = this;
            }else if(rank < rv.rank){
                root = rv;
            }else{
                rank++;
                rv.root = this;
            }
        }
    }

    public long kruskal() {
        algorithm = "Kruskal";
        Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        Arrays.sort(edgeArray);
        for (Edge e : edgeArray){
            MSTVertex ru = get(e.fromVertex()).find();
            MSTVertex rv = get(e.toVertex()).find();
            if(!ru.vertex.equals(rv.vertex)){
                mst.add(e);
                ru.union(rv);
                wmst += e.getWeight();
            }
        }
        return wmst;
    }

    public long prim3(Vertex s) {
        algorithm = "indexed heaps";
        initialize();
        get(s).d = 0;
        mst = new LinkedList<>();
        IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
        for (Vertex u : g){
            q.add(get(u));
        }
        while (!q.isEmpty()){
            MSTVertex u = q.poll();
            u.seen = true;
            wmst += u.d;
            for (Edge e : g.incident(u.vertex)){
                Vertex v = e.otherEnd(u.vertex);
                if(!get(v).seen && e.getWeight() < get(v).d){
                    get(v).d = e.getWeight();
                    get(v).parent = u.vertex;
                    q.decreaseKey(get(v));
                }
            }
        }
        return wmst;
    }

    public long prim2(Vertex s) {
        algorithm = "PriorityQueue<Vertex>";
        initialize();
        get(s).d = 0;
        mst = new LinkedList<>();
        PriorityQueue<MSTVertex> q = new PriorityQueue<>();
        q.add(get(s));

        while (!q.isEmpty()){
            MSTVertex u = q.remove();
            if(!u.seen){
                u.seen = true;
                wmst+=u.d;

                for (Edge e : g.incident(u.vertex)){
                    Vertex v = e.otherEnd(u.vertex);
                    if(!get(v).seen && e.getWeight() < get(v).d){
                        MSTVertex dupV = new MSTVertex(get(v));
                        dupV.d = e.getWeight();
                        dupV.parent = u.vertex;
                        put(dupV.vertex, dupV).seen = true;
                        q.add(dupV);
                    }
                }

            }
        }

        return wmst;
    }

    public long prim1(Vertex s) {
        algorithm = "PriorityQueue<Edge>";
        initialize();
        get(s).seen = true;
        mst = new LinkedList<>();
        PriorityQueue<Edge> q = new PriorityQueue<>();

        for (Edge e: g.incident(s)){
            q.add(e);
        }

        while (!q.isEmpty()){
            Edge e = q.remove();
            Vertex v = get(e.fromVertex()).seen ? e.toVertex() : e.fromVertex();
            if(get(v).seen) continue;

            get(v).seen = true;
            get(v).parent = e.otherEnd(v);
            wmst += e.getWeight();

            for (Edge e2 : g.incident(v)){
                if (!get(e2.otherEnd(v)).seen){
                    q.add(e2);
                }
            }
        }
        return wmst;
    }

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