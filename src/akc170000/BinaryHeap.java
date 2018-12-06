/**
 * @author Axat Chaudhari (akc170000)
 * @author Jaiminee Kataria (jxk172330)
 * @author Param Parikh (psp170230)
 * @author Tej Patel (txp172630)
 */
package akc170000;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class BinaryHeap<T extends Comparable<? super T>> {
    Comparable[] pq;
    int size;
    int maxCapacity;
    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(int maxCapacity) {
        this.pq = new Comparable[maxCapacity];
        this.size = 0;
        this.maxCapacity = maxCapacity;
    }

    /**
     * add x to PriorityQueue
     * @param x element to add
     * @return true if queue reached max capacity else false
     */
    public boolean add(T x) {
        if (size() == maxCapacity){
            return false;
        }
        pq[size++] = x;
        percolateUp(size-1);
        return true;
    }

    /**
     * same as add(x)
     * @param x
     * @return true if reached to max capacity else false
     */
    public boolean offer(T x) {
        return add(x);
    }

    /**
     * extract min from queue
     * @return min element from queue
     * @throws NoSuchElementException when queue is empty
     */
    public T remove() throws NoSuchElementException {
        T result = poll();
        if(result == null) {
            throw new NoSuchElementException("Priority queue is empty");
        } else {
            return result;
        }
    }

    /**
     * same as remove()
     * @return null if queue is empty
     */
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T temp = (T) pq[0];
        pq[0] = pq[--size];
        percolateDown(0);
        return temp;
    }

    /**
     * get min element
     * @return element with max priority
     */
    public T min() {
        return peek();
    }

    /**
     * same as min()
     * @return element with max priority
     */
    public T peek() {
        return isEmpty() ? null : (T) pq[0];
    }

    /**
     * parent of i
     * @param i index
     * @return (i-1)*2
     */
    int parent(int i) {
        return (i-1)>>1;
    }

    /**
     * left child of i
     * @param i index
     * @return i*2 + 1
     */
    int leftChild(int i) {
        return (i<<1) + 1;
    }

    /** pq[index] may violate heap order with parent */
    void percolateUp(int index) {
        Comparable temp = pq[index]; // store value of ith element

        // go up and bring down elements while they are greater than 'temp'
        while (index > 0 && compare(temp, pq[parent(index)]) < 0){
            move(index, pq[parent(index)]); // bringing down parent to one level below
            index = parent(index);
        }
        move(index, temp); // move saved element to empty position of heap
    }

    /** pq[index] may violate heap order with children */
    void percolateDown(int index) {
        Comparable temp = pq[index]; // save current element
        int c = leftChild(index); // left child
        // go down while current element is greater than its children
        while (c < size){ // if left child exists
            if (c < size - 1 && compare(pq[c + 1], pq[c]) < 0){ // if right child exists and it is less than left one
                c++; // change child to right child
            }
            // if current element is less than child then we have found the place for current element
            if (compare(temp, pq[c]) <= 0) break;
            move(index, pq[c]);
            index = c;
            c = leftChild(index); // update to next left child
        }
        move(index, temp); // move current element to its place
    }

    /**
     * move x to dest
     * @param dest position to store x
     * @param x element to store at position dest
     */
    void move(int dest, Comparable x) {
        pq[dest] = x;
    }

    int compare(Comparable a, Comparable b) {
        return ((T) a).compareTo((T) b);
    }

    /** Create a heap.  Precondition: none. */
    void buildHeap() {
        for(int i=parent(size-1); i>=0; i--) {
            percolateDown(i);
        }
    }

    /**
     * is queue empty or not
     * @return true if empty otherwise false
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * size of queue
     * @return #elements in queue
     */
    public int size() {
        return size;
    }

    /**
     * resize q to double the size
     */
    void resize() {
        pq = Arrays.copyOf(pq, pq.length<<1);
    }

    /**
     * Interface for IndexedPriorityQueue
     */
    public interface Index {
        public void putIndex(int index);
        public int getIndex();
    }

    public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {
        /** Build a priority queue with a given array */
        public IndexedHeap(int capacity) {
            super(capacity);
        }

        /** restore heap order property after the priority of x has decreased */
        public void decreaseKey(T x) {
            pq[x.getIndex()] = x;
            percolateUp(x.getIndex());
        }

        @Override
        void move(int i, Comparable x) {
            super.move(i, x);
            ((T) x).putIndex(i);
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {0,9,7,5,3,1,8,6,4,2};
        BinaryHeap<Integer> h = new BinaryHeap(arr.length);

        System.out.print("Before:");
        for(Integer x: arr) {
            h.offer(x);
            System.out.print(" " + x);
        }
        System.out.println();

        for(int i=0; i<arr.length; i++) {
            arr[i] = h.poll();
        }

        System.out.print("After :");
        for(Integer x: arr) {
            System.out.print(" " + x);
        }
        System.out.println();
    }
}